Ibn-Taymiyyah
=============

This is the project to provide full text search facility for Qura'an.
This is moved from 'Taymiyyah' for proper re-naming and re-structuring of modules

------------------------- xx -------------------------


Step for Installation.
---------------------

1- Prepare database, please refere to db_scripts/README.txt
2- Execute maven build.
3- Generate Lucene indexes by executing:
   Ibn-Taymiyyah/taymiyyah-repository/taymiyyah-repository-impl/
   src/test/java/com/maktashaf/taymiyyah/repository/analysis/generator/IndexGenerator.java
   Index container directory name must be 'index'. As already mentioned in IndexGenerator.java as context path.
4- Copy 'index' folder and paste under 'WEB-INF' folder.
5- Deploy Application and Start the tomcat sever.

---heroku deploymeny-
heroku run bash --app mustakshif. for bash access.
copy all project with pom files to a directory run following command in that directory
heroku war:deploy taymiyyah-web/target/taymiyyah-web-1.0.war --app mustakshif --includes index

System Requirement.
------------------
JDK 1.8+
Tomcat 7+
MySQL 5+

