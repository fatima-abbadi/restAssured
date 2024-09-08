
  @Test
  public static void getResponseBody(){
	   given().when().get("http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1").then().log()
	  .all();
	 
	}
  @Test
  public static void getResponseBody1(){
	  
	   given().queryParam("CUSTOMER_ID","68195")
	           .queryParam("PASSWORD","1234!")
	           .queryParam("Account_No","1")
	           .when().get("http://demo.guru99.com/V4/sinkministatement.php").then().log()
	           .body();
	}
  @Test
  public static void getResponseStatus(){
	   int statusCode= given().queryParam("CUSTOMER_ID","68195")
	           .queryParam("PASSWORD","1234!")
	           .queryParam("Account_No","1") .when().get("http://demo.guru99.com/V4/sinkministatement.php").getStatusCode();
	   System.out.println("The response status is "+statusCode);

	   given().when().get("http://demo.guru99.com/V4/sinkministatement.php").then().assertThat().statusCode(200);
	}
  @Test

  public static void getResponseHeaders(){
	   System.out.println("The headers in the response "+
	                   get("http://demo.guru99.com/V4/sinkministatement.php").then().extract()
	           .headers());
	}
  @Test

  public static void getResponseTime(){
	  System.out.println("The time taken to fetch the response "+get("http://demo.guru99.com/V4/sinkministatement.php")
	         .timeIn(TimeUnit.MILLISECONDS) + " milliseconds");
	}
  @Test

  public static void getResponseContentType(){
	   System.out.println("The content type of response "+
	           get("c").then().extract()
	              .contentType());
} @Test
 
  public static void getSpecificPartOfResponseBody() {
    
    Response response = RestAssured.when()
        .get("http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1");

    String contentType = response.getContentType();
    System.out.println("Content Type: " + contentType);

    String responseBody = response.getBody().asString();
    System.out.println("Response Body (Encoded): " + responseBody);

    String decodedResponseBody = StringEscapeUtils.unescapeHtml4(responseBody);
    System.out.println("Response Body (Decoded): " + decodedResponseBody);

    String correctedJson = decodedResponseBody
        .replace("\"result:\"", "\"result\"")
        .replace("\"ErrorCode:\"", "\"ErrorCode\"")
        .replace("\"ErrorMsg:\"", "\"ErrorMsg\"");

    System.out.println("Corrected JSON: " + correctedJson);

    try {
        JsonPath jsonPath = new JsonPath(correctedJson);

        List<String> amounts = jsonPath.getList("result.statements.AMOUNT");

        if (amounts != null && !amounts.isEmpty()) {
            int sumOfAll = 0;

            for (String amount : amounts) {
                System.out.println("The amount value fetched is " + amount);
                sumOfAll += Integer.parseInt(amount);
            }

         
            System.out.println("The total amount is " + sumOfAll);
        } else {
            System.out.println("No amounts found in the response.");
        }
    } catch (Exception e) {
        System.out.println("Failed to parse the response as JSON: " + e.getMessage());
    }
}}
