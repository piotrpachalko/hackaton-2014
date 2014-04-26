package com.mpmp.freya.connector.postprocessor;

import java.net.URLEncoder;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.json.simple.JSONObject;

import com.mpmp.freya.connector.commons.Parameter;
import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;
import com.mpmp.freya.connector.fetcher.Fetcher;
import com.mpmp.freya.connector.fetcher.RestFetcher;
import com.mpmp.iface.model.Item;

@Stateless
public class ImagePostProcessor implements PostProcessor {

	@EJB
	private Fetcher fetcher;

	@Override
	public Collection<Item> postProcess(Collection<Item> items) {
		for (Item item : items) {

			Parameter actionParam = new Parameter("action", "query");
			Parameter titleParam = new Parameter("titles", URLEncoder.encode(item.getTitle()));
			Parameter propParam = new Parameter("prop", "pageimages");
			Parameter formatParam = new Parameter("format", "json");
			Parameter sizeParam = new Parameter("pithumbsize", "400");

			QueryParameters params = new QueryParameters(actionParam, titleParam, propParam, formatParam, sizeParam);
			WebAddress source = new WebAddress("http://pl.wikipedia.org/w/api.php");

			JSONObject jsonObject = (JSONObject) fetcher.retrieve(source, params);

			if (jsonObject.toJSONString().indexOf("\"source\":\"") > 0) {

				String pictureURL = jsonObject
						.toJSONString()
						.substring(jsonObject.toJSONString().indexOf("\"source\":\"") + 10)
						.substring(
								0,
								jsonObject.toJSONString()
										.substring(jsonObject.toJSONString().indexOf("\"source\":\"") + 10)
										.indexOf("\""));
				item.setPictureUrl(pictureURL);
			}
		}
		return items;
	}
}
