package com.sparta.clone77.crawling;

import com.sparta.clone77.dto.ReturnTwoDto;
import com.sparta.clone77.model.Product;
import com.sparta.clone77.model.Selects;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
@Component
public class Webdriver {

    //WebDriver 설정
    private WebDriver driver;

    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";      // Properties 설정
    public static String WEB_DRIVER_PATH = "/Users/imchahyeog/Desktop/hanghae99/clone/crawling/chromedriver/chromedriver";       // 다운받은 크롬드라이버 경로

    public Webdriver() {
        // System Property SetUp
        chrome();
    }

    private void chrome() {
        System.setProperty("webdriver.chrome.driver", WEB_DRIVER_PATH);
        System.out.println("실행중~");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        this.driver = driver;
    }

    public ReturnTwoDto useDriver(String url) {
        List<Product> productList = new ArrayList<>();
        ReturnTwoDto returnTwoDto = new ReturnTwoDto();
        try {
            // get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다.)
            driver.get(url);
            String getString = driver.getPageSource();
            // HTML태그를 삭제하는 정규표현식 적용하여 빈문자로 대체하기
            String tagRemove = getString.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
            returnTwoDto = convertJson(tagRemove);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
        return returnTwoDto;
    }

    private static ReturnTwoDto convertJson(String jsonString) {
        JSONParser parser = new JSONParser();
        Object obj;
        List<Object> objectList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        ReturnTwoDto returnTwoDto = new ReturnTwoDto();
        List<Selects> selectsList = new ArrayList<>();
        try {
            // 데이터 거르기
            obj = parser.parse(jsonString);
            JSONObject jsonObj = (JSONObject)obj;
            obj = jsonObj.get("firestore");
            JSONObject firestore = (JSONObject)obj;
            obj = firestore.get("goodslist");
            JSONObject goodsList = (JSONObject)obj;
            obj = goodsList.get("value");
            JSONArray value = (JSONArray) obj;
            int count = 0;
            // 실제 상품 리스트를 반복문을 돌면서 저장한다.
            for(Object productInfo : value) {
                objectList.add(productInfo);
                JSONObject productJson = (JSONObject) productInfo;
                String detail_name = productJson.get("detail_name").toString();
                String list_thumbnail_web = productJson.get("list_thumbnail_web").toString();
                String list_tag = productJson.get("list_tag").toString();
                String list_option = productJson.get("list_option").toString();
                String list_price = productJson.get("list_price").toString();
                String category = productJson.get("category").toString();
                String displayid = productJson.get("displayid").toString();
                Object selectsObject = productJson.get("selects");

                Product product = new Product(detail_name, list_thumbnail_web, list_tag, list_option, list_price, category, displayid);

                JSONArray selects = (JSONArray)selectsObject;

                for(Object select : selects) {
                    JSONObject selectJson = (JSONObject) select;
                    Selects tempSelects = new Selects(displayid, selectJson.get("name").toString(), product);
                    selectsList.add(tempSelects);
                }
                product.addSelect(selectsList);

                productList.add(product);
                returnTwoDto = new ReturnTwoDto(productList, selectsList);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            System.out.println(productList.size());
        }
        return returnTwoDto;
    }
}
