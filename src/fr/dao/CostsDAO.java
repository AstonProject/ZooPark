package fr.dao;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CostsDAO {

	@SuppressWarnings("unchecked")
	public static Map<String,String> getCosts() {
		JSONParser parser = new JSONParser();
		Map<String, String> costs = new HashMap<>();

		try {

			Object obj = parser.parse(new FileReader("/assets/json/costs.json"));

			JSONObject jsonObject = (JSONObject) obj;

			String enclosureCosts_lion = (String) jsonObject.get("enclosureCosts_lion");
			costs.put("enclosureCosts_lion", enclosureCosts_lion);
			String enclosureCosts_giraffe = (String) jsonObject.get("enclosureCosts_giraffe");
			costs.put("enclosureCosts_giraffe", enclosureCosts_giraffe);
			String enclosureCosts_elephant = (String) jsonObject.get("enclosureCosts_elephant");
			costs.put("enclosureCosts_elephant", enclosureCosts_elephant);
			String enclosureCosts_camel = (String) jsonObject.get("enclosureCosts_camel");
			costs.put("enclosureCosts_camel", enclosureCosts_camel);
			String lionCosts = (String) jsonObject.get("lionCosts");
			costs.put("lionCosts", lionCosts);
			String giraffeCosts = (String) jsonObject.get("giraffeCosts");
			costs.put("giraffeCosts", giraffeCosts);
			String elephantCosts = (String) jsonObject.get("elephantCosts");
			costs.put("elephantCosts", elephantCosts);
			String camelCosts = (String) jsonObject.get("camelCosts");
			costs.put("camelCosts", camelCosts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return costs;
	}
}
