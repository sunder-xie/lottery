package com.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class WebTools {
	
	public static void main(String[] args) {
		getObject();
	}
	
	public static final String UTF8 = "UTF-8";
	
	public static final String HEAD_Accept = "Accept";
	public static final String HEAD_Encoding = "Accept-Encoding";
	public static final String HEAD_Language = "Accept-Language";
	public static final String HEAD_Cache = "Cache-Control";
	public static final String HEAD_Connection = "Connection";
	public static final String HEAD_Cookie = "Cookie";
	public static final String HEAD_Host = "Host";
	public static final String HEAD_Agent = "User-Agent";
	public static final String HEAD_Referer = "Referer";
	public static final String HEAD_XR = "X-Requested-With";
	
	
	public static HttpURLConnection httpItem(String urlAll, HashMap<String, String> map) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlAll);
			connection = (HttpURLConnection) url.openConnection();
//			for (String key : map.keySet()) {
//				 System.out.println(key + "-->" + map.get(key));  
//			}
		} catch (Exception e) {
			System.out.println("httpItem -- err:" + e); 
		}
		return connection;
	}
	
	public static Object getObject() {
		String urlAll = "http://mkt.2999m.com/OddsManager/Standard?jsoncallback=jinshaCallback' -H 'Cookie: f5avrbbbbbbbbbbbbbbbb=EFNJKKEAGMNCNJCCBJEMMPAHPIIPLNPKDDDBGDIFIKMNEPEJJMBGHLJOAPIOADHLACADGKNMKJGLFKMPLCIANLAMOGAFBNBFFIJLMBBELNBPMGDIHLGOGANHPFGPJNCA; T1-IplWeb-Statsbbbbbbbbbbbbbbbb=MKGMPIPPFOCDJFICLHCJFOKIGDHCPDCEOFOADAADLKDDBMIFKHLBDFAOLKJACKKMKJEGKGCILOEEDMGBOGNPGHICFKJFOLJJBEBEECHEMPLNLDFKDFKGKKEDELGGGBFC; IBCACHE=jSKpHtD6LvSu2WA2suW0q3z9FGmLArgISFPQpZe9CSZUEvlhnUjnX1AnR5cdBpzYTlUxMldickw5bUVZYmpVN2dCVW1VSWxJcW1EbnNSMl9hdUJJdGo4a2F3Yw; LangKey=cs; ASP.NET_SessionId=dyzauux03jauyvex2qnebpea; __RequestVerificationToken=ZoXuPw4dJU4LjuhIADIp-0oXQIvfFzfgbQvjAhJGRwP0peNydq4Bjf3MI3VV9p93vgSNARGpCvcP4EElfRNluwGfOsY1; _pk_ref.5.df70=%5B%22%22%2C%22%22%2C1488545937%2C%22http%3A%2F%2Fc.pc.qq.com%2Ffcgi-bin%2Fsafeurl%3Furl%3Dhttp%3A%2F%2Fmkt.2999m.com%2Fonebook%3Flang%3Dcs%26act%3D%26webskintype%3D2%26qqdrsign%3DB005%26pagetype%3D8%26mgrtoken%3DB0E5746F3977A255D24C5A0ECE061A8D%22%5D; f5avrbbbbbbbbbbbbbbbb=HJBFDJCPKCHBLOADHAGCDNBAIKIMOHNDPPHODMKBHHOBKLFPMNFDABCHKIFGFPIDOAPDABJLOJFHLHLGBAEAJGBBOGGLHMJLMJMPFHBMOPNILDDDIHCIJFADNFJDFCII; T1-IplWeb-Statsbbbbbbbbbbbbbbbb=IKHNNFANIFMEAEEEKJFCCOJEKHPLDPCDJHLBAHEODHEDNPCMABDIABNPPOGABIMIOJNDPOMKKBHKFLOOOGBNNFGDKNBNMDAMCJACAEBHLLIIKKKAFEFFGCFCAENCNJMA; WebSkinType=2; OddsType_SPONUUS01001=2; _pk_id.5.df70=fe880de5d10518fb.1488220322.4.1488546661.1488545937.; _pk_ses.5.df70=*; _ga=GA1.2.582691219.1488220322; _gat_LicNewAsiaGACode=1' -H 'Origin: http://mkt.2999m.com' -H 'Accept-Encoding: gzip, deflate' -H 'Accept-Language: zh-CN,zh;q=0.8,en;q=0.6' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36' -H 'Content-Type: application/x-www-form-urlencoded; charset=UTF-8' -H 'Accept: text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01' -H 'Referer: http://mkt.2999m.com/onebook?lang=cs&act=&webskintype=2&qqdrsign=B005' -H 'X-Requested-With: XMLHttpRequest' -H 'Connection: keep-alive' -H '__VerfCode: jAvuomWvsbnQsWzQwzD_lwLktmNcK6ZVgoIYVj7ENeKgTAkQ6WsKucBZ8vur3O0a5Tp-B8_wH5PBcAP2ywYoudfMo1U1' --data 'FixtureType=t&SportType=1&Scope=MainMarket&IsParlay=false&SelDate=&tstamp=0";
		HashMap<String, String> map = new HashMap<>();
		map.put(WebTools.HEAD_Accept, "application/json, text/javascript, */*; q=0.01");
		map.put(WebTools.HEAD_Agent, "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");

		HttpURLConnection connection = httpItem(urlAll, map);

		try {
			connection.connect();
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, WebTools.UTF8));
			String strRead = null;
			StringBuffer sbf = new StringBuffer();
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
			}
			reader.close();
			System.out.println(sbf.toString());
			return sbf;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
