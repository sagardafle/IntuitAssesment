package com.rest;

import com.rest.CustomHelper.MatchTemplateHelper;
import com.rest.CustomHelper.UserInput;
import org.opencv.core.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;



@Path("/intuit")
public class MatchTemplateService {
    private static final String TEMPLATE_FILE = "C:\\Users\\I863509\\IdeaProjects\\IntuitAssesment\\src\\sample_files\\perfect_cat_image.txt";
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{param}")
    public String helloWorld(@PathParam("param") String msg) {
        return "Hello " + msg;
    }
    @GET
    @Path("/loadlibrary")
    public String loadlibrary() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        return "Library " + Core.NATIVE_LIBRARY_NAME + " has been successfully loaded.";
    }

    @POST @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/matchtemplate")
    public Response matchpatterntemplate(UserInput input) {
        MatchTemplateHelper helper = new MatchTemplateHelper();
        Map<Integer,String> catMap = helper.buildCatMap(input.getInputfile());
        Map<List<Integer>, List<Integer>> matchpoints = helper.findMatch(catMap, input.getThresholdvalue());
        System.out.println("Final:   "+matchpoints);

        return Response.status(201).entity(matchpoints).build();
    }
}