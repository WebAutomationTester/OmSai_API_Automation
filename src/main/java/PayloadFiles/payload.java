package PayloadFiles;

public class payload {
    public static String inputFiles() {
        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"OmSai Academy\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"29, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://Ayansh.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}";
    }

    public static String CoursePrice () {
        return "{\n" +
                "  \"Dashboard\" : {\n" +
                "      \"PurchaseAmount\" : 910,\n" +
                "        \"Website\" : \"Omsai@gmail.com\"\n" +
                "        },\n" +
                "        \"Courses\" : [\n" +
                "        {\n" +
                "            \"Title\" : \"Selenium\",\n" +
                "            \"Price\" : 50,\n" +
                "            \"Copies\" : 6\n" +
                "        },\n" +
                "        {\n" +
                "             \"Title\" : \"Cypress\",\n" +
                "             \"Price\" : 40,\n" +
                "             \"Copies\" : 4\n" +
                "        },\n" +
                "        {\n" +
                "             \"Title\" : \"RPA\",\n" +
                "             \"Price\" : 45,\n" +
                "             \"Copies\" :10\n" +
                "        }\n" +
                "                \n" +
                "    ]\n" +
                "}";
    }

    public static String AddBook(String isbn, String aisle) {
        String payloadFile =  "{\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"Om Sai\"\n" +
                "}";
        return payloadFile;
    }
}
