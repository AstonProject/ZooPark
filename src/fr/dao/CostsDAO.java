package fr.dao;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CostsDAO {

	@SuppressWarnings("unchecked")
	public JSONObject getCosts() {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			URL url = getClass().getResource("costs.json");
			File file = new File(url.getPath());
			Object obj = parser.parse(new FileReader(file));

			jsonObject = (JSONObject) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
