package BasicsProg;

import PayloadFiles.payload;
import io.restassured.path.json.JsonPath;

public class ComplexParseJson {
    public static void main (String[] args) {
        JsonPath js = new JsonPath(payload.CoursePrice());

        // Scenario 1: Print no of courses returned by API
         int countCourses = js.getInt("Courses.size()");
         System.out.println("Scenario 1: Count of course: "+countCourses);

        // Scenario 2: Print purchase Amount
        int PurchaseAmount = js.getInt("Dashboard.PurchaseAmount");
        System.out.println("Scenario 2: Purchase Amount is: "+PurchaseAmount);

        // Scenario 3: Print Title of the first course
        String titleOfCourse = js.getString("Courses.Title[0]");
        System.out.println("Scenario 3: Title of first course is: "+titleOfCourse);

        // Scenario 4: Print all course titles and their respective Prices
        for(int i = 0 ; i < countCourses ; i++ ) {
            String courseTitle = js.getString("Courses["+i+"].Title");
            int CoursePrice = js.getInt("Courses["+i+"].Price");
            System.out.println("Scenario 4: Title of course is: "+courseTitle +" and Price: "+CoursePrice);
        }

        // Scenario 5: Print no of copies sold by Cypress
        for(int i = 0 ; i < countCourses ; i++ ) {
            String courseTitle = js.getString("Courses[" + i + "].Title");
            if(courseTitle.equals("Cypress")) {
                int NoofCopies = js.getInt("Courses["+i+"].Copies");
                System.out.println("Scenario 5: Number of copies of cypress is: "+ NoofCopies );
                break;
            }
        }

        // Scenario 6: Verify if sum of all courses Prices matches with Purchase Amount
        int TotalCoursePrice = 0;
        for(int i = 0 ; i < countCourses ; i++ ) {
            TotalCoursePrice = TotalCoursePrice + (js.getInt("Courses["+i+"].Price") * js.getInt("Courses["+i+"].Copies"));
        }
        System.out.println("Scenario 6: Final Total Price of course is: "+ TotalCoursePrice);
        if(PurchaseAmount == TotalCoursePrice) {
            System.out.println("Total Price of course and Purchase Amount is matched..!!");
        } else {
            System.out.println("Total Price of course and Purchase Amount does not matched..!!");
        }
    }
}
