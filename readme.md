Steps to run: 
1. Setup Tomcat 7 server. 
2. Add dependencies as found in the pom.xml.
3. Run Tomcat7 (on port 8080)
4. Hit the matchtemplate POST API via URL: http://localhost:8080/rest/intuit/matchtemplate
4.1 Set the input parameter as POST request body parameter. 
{
"inputfile": "C:\\Users\\I863509\\IdeaProjects\\TemplateMatching\\src\\main\\resources\\sample_files\\image_with_cats.txt",
"thresholdvalue": "70"
}
4.2 Hit Send. 