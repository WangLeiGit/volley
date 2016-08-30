
/**
 * 
 * @author Wanglei
 * @data: 2015年6月3日 下午2:16:30
 * @version: V1.0
 */
public class MainActivity {

	private Gson gson = GsonUtil.createGson();
	private List<String> param = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		post();
	}

	private void post() {
		RequestParams params = new RequestParams();
		params.put("mobile", phone.getText().toString());
		param.clear();
		addParams("mobile", phone.getText().toString(), param);
		params.put("signature", MD5Utils.validateSignatrue(param));
		RequestManager.post(URL, null,
				params, new RequestListener() {

					@Override
					public void requestSuccess(String json) {
						CommonCode codes = gson
								.fromJson(json, CommonCode.class);
						if (codes != null) {
							int code = Integer.parseInt(codes.getCode());
							if (code == 500 || code == 201 || code == 501) {
								mToast(-1, codes.getMessage());
								mTimeCount.cancel();
								smsFinish();
								return;
							}
							if (code == 300) {
								Log.e("连接属性", codes.getMessage());
								mTimeCount.cancel();
								smsFinish();
								return;
							}
							if (code == 200) {
								mPrefs.edit()
										.putString(Common.USER_PHONE_NUMBER,
												phone.getText().toString()).commit();
								mToast(-1, "发送成功!");

							} else {
								mToast(R.string.please_check_network, null);
								mTimeCount.cancel();
								smsFinish();
								return;
							}
						}
					}

					@Override
					public void requestError(VolleyError e) {
						mToast(R.string.please_check_network, null);
						mTimeCount.cancel();
						smsFinish();
						e.printStackTrace();
					}
				});
	}


}
