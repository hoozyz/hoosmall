package com.hoozy.hoosshop.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hoozy.hoosshop.entity.Category;
import com.hoozy.hoosshop.entity.Img;
import com.hoozy.hoosshop.entity.Product;
import com.hoozy.hoosshop.service.CategoryService;
import com.hoozy.hoosshop.service.ImgService;
import com.hoozy.hoosshop.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
	
	private final ProductService productService;
	private final ImgService imgService;
	private final CategoryService categoryService;

	@GetMapping("/parsing")
	public void parsing() {
		String[] textArr = {"캠핑", "그래픽카드", "모니터", "생활용품"}; // 검색을 원하는 카페고리 종류
		
		for(String text : textArr) {
			try {
				text = URLEncoder.encode(text, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("검색어 인코딩 실패", e);
			}
			
			String body = parsing(text);
			
			// json 문자열을 jsonparser를 통해 json 객체로 바꾸는 코드
			JsonObject jsonMain = (JsonObject) JsonParser.parseString(body);
			
			// json 객체 내의 배열값을 jsonarray 객체로 바꾸는 코드
			JsonArray jsonItems = (JsonArray) jsonMain.get("items");
			
			if(jsonItems.size() > 0) {
				for(int i = 0; i < jsonItems.size(); i++) {
					JsonObject jsonItem = (JsonObject) jsonItems.get(i); // item 하나씩 가져오기
					String cate = "";
					for(int j = 3; j >= 1; j--) { // 카테고리 찾는 반복문
						cate = jsonItem.get("category"+j).toString();
						if(cate.length() == 2) { // 카테고리가 없으면("") 상위 카테고리 찾기
							continue;
						} else {
							break;
						}
					}
					String title = jsonItem.get("title").toString();
					String image = jsonItem.get("image").toString();
					int price = Integer.parseInt(jsonItem.get("lprice").toString().replaceAll("[^0-9]", ""));
					Img img = Img.builder()
								.link(image)
								.build();
					
					Category category = Category.builder()
											.cate(cate)
											.build();

					Product product = Product.builder()
										.category(category)
										.img(img)
										.price(price)
										.title(title)
										.build();
					imgService.save(img);
					categoryService.save(category);
					productService.save(product);
				}
			}
		}

	}
	
	private String parsing(String query) {
		String clientId = "89md1AA5SI29hG5tPaVa"; // 애플리케이션 클라이언트 아이디
		String clientSecret = "0yZWZlwD_W"; // 애플리케이션 클라이언트 시크릿
		
		String apiURL = "https://openapi.naver.com/v1/search/shop.json?query=" + query; // JSON 결과

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);
		
		return responseBody;
	}

	private String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 오류 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
		}
	}
}
