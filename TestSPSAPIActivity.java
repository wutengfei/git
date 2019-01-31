package cn.org.bjca.anysign.android.api.demos.test.msps;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.org.bjca.anysign.android.api.R;
import cn.org.bjca.anysign.android.api.Interface.OnSignatureResultListener;
import cn.org.bjca.anysign.android.api.core.CachetObj;
import cn.org.bjca.anysign.android.api.core.CommentObj;
import cn.org.bjca.anysign.android.api.core.OriginalContent;
import cn.org.bjca.anysign.android.api.core.SignRule;
import cn.org.bjca.anysign.android.api.core.SignRule.KWRule;
import cn.org.bjca.anysign.android.api.core.SignRule.KWRule.SigAlignMethod;
import cn.org.bjca.anysign.android.api.core.SignRule.SignRuleType;
import cn.org.bjca.anysign.android.api.core.SignRule.XYZRule;
import cn.org.bjca.anysign.android.api.core.SignatureAPI;
import cn.org.bjca.anysign.android.api.core.SignatureObj;
import cn.org.bjca.anysign.android.api.core.Signer;
import cn.org.bjca.anysign.android.api.core.Signer.SignerCardType;
import cn.org.bjca.anysign.android.api.core.domain.AnySignBuild;
import cn.org.bjca.anysign.android.api.core.domain.CommentInputType;
import cn.org.bjca.anysign.android.api.core.domain.DataType;
import cn.org.bjca.anysign.android.api.core.domain.SignResult;
import cn.org.bjca.anysign.android.api.core.domain.SignatureType;
import cn.org.bjca.anysign.android.api.demos.test.FileUtils;
import cn.org.bjca.anysign.core.BJCAAnySignOCRCapture;

public class TestSPSAPIActivity extends Activity implements OnClickListener {

	private Button initApi, sign, multi_sign,sign_custom, Masssign, Masssign1, Masssign2,photo_evidence, sound_evidence,video_evidence,addPhotoFile,
			isReadyToUpload, genData, resetAPI, upload, saveCacheWithSession, readCacheWithSessionId,deleteCacheWithSessionId,hasCacheWithSessionId;

	private SignatureAPI api;
	private Activity app = this;
	private byte[] bTemplate = null;

	private String mSession = "";

	private String encAlg ;
	private String path_root = Environment.getExternalStorageDirectory()+"/anysign_2.3.2.txt";

	private int apiResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_sps);


		initApi = (Button) findViewById(R.id.init_api);// 初始化API
		sign = (Button) findViewById(R.id.singlesign);// 单签
		multi_sign = (Button) findViewById(R.id.singlesign1);// 单签
		sign_custom = findViewById(R.id.singlesign2);
		Masssign = (Button) findViewById(R.id.masssign);// 批注
		Masssign1 = (Button) findViewById(R.id.masssign1);// 批注
		Masssign2 = (Button) findViewById(R.id.masssign2);// 批注
		photo_evidence = (Button) findViewById(R.id.photo_evidence);// 图片证据
		sound_evidence = (Button) findViewById(R.id.sound_evidence);// 音频证据
		video_evidence = (Button) findViewById(R.id.video_evidence);// 音频证据
		addPhotoFile = (Button) findViewById(R.id.addPhotoFile);//
		isReadyToUpload = (Button) findViewById(R.id.isReadyToUpload);// 数据是否准备就绪
		genData = (Button) findViewById(R.id.gen_data);// 生成数据加密包
		upload = (Button) findViewById(R.id.gen_upload);// 重新初始化API
		resetAPI = (Button) findViewById(R.id.reset_api);// 重新初始化API

		saveCacheWithSession = (Button) findViewById(R.id.setSessionID);
		readCacheWithSessionId = (Button) findViewById(R.id.restoreSession);
		deleteCacheWithSessionId = (Button) findViewById(R.id.deleteSession);
		hasCacheWithSessionId = (Button) findViewById(R.id.hasSession);

		initApi.setOnClickListener(this);
		sign.setOnClickListener(this);
		multi_sign.setOnClickListener(this);
		sign_custom.setOnClickListener(this);
		Masssign.setOnClickListener(this);
		Masssign1.setOnClickListener(this);
		Masssign2.setOnClickListener(this);
		photo_evidence.setOnClickListener(this);
		sound_evidence.setOnClickListener(this);
		video_evidence.setOnClickListener(this);
		addPhotoFile.setOnClickListener(this);
		isReadyToUpload.setOnClickListener(this);
		genData.setOnClickListener(this);
		upload.setOnClickListener(this);
		resetAPI.setOnClickListener(this);

		saveCacheWithSession.setOnClickListener(this);
		readCacheWithSessionId.setOnClickListener(this);
		deleteCacheWithSessionId.setOnClickListener(this);
		hasCacheWithSessionId.setOnClickListener(this);

		encAlg = getIntent().getStringExtra("encAlg");
//		Log.e("XSS", encAlg);

	}


	@Override
	public void onClick(View v) {
		if (v.equals(initApi)) {
			initApi();
			Toast.makeText(app, "初始化API成功", Toast.LENGTH_LONG).show();
		}
		else if (v.equals(sign)) {

			if (api !=null) {
				apiResult = api.showSignatureDialog(0);// 弹出单签签名框签名
				if(apiResult == SignatureAPI.SUCCESS){

				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}

		}
		else if (v.equals(multi_sign)) {

			if (api !=null) {
				apiResult = api.showSignatureDialog(1);// 弹出单签签名框签名
				if(apiResult == SignatureAPI.SUCCESS){

				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}

		}

		else if (v.equals(sign_custom)) {

			if (api !=null) {
				apiResult = api.showSignatureDialog(2);// 弹出单签签名框签名
				if(apiResult == SignatureAPI.SUCCESS){

				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}

		}

		else if (v.equals(Masssign)) {

			if(!isPad(this)){
				Toast.makeText(this,"网格批注请在pad上进行运行！",Toast.LENGTH_SHORT).show();
				return;
			}

			if (api != null) {
				apiResult = api.showCommentDialog(0);// 弹出批注签名框签名

				if(apiResult == SignatureAPI.SUCCESS){

				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}

			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.equals(Masssign1)) {

			if (api != null) {
				apiResult = api.showCommentDialog(1);// 弹出批注签名框签名

				if(apiResult == SignatureAPI.SUCCESS){

				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}

			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.equals(Masssign2)) {

			if (api != null) {
				apiResult = api.showCommentDialog(2);// 弹出批注签名框签名

				if(apiResult == SignatureAPI.SUCCESS){

				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}

			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.equals(photo_evidence)) {

			if (api != null) {
				/*
				 * addEvidence(int signIndex, byte[] content, BioType evidenceType)
				 *
				 * 添加证据一定要在签名之前
				 *
				 * 设置第三方自采证据，此数据项的哈希值会保存到证书中
				 * 参数1：signIndex 此证据所有者的index值，如1签名人，则此证书同此签名人绑定
				 * 参数2：content 此证据数据原文二进制数据
				 * 参数3：evidenceType 此证据类型，如枚举中无需要使用的具体值，设置正确evidence类型即可，如PHOTO、AUDIO。
				 */
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] content = baos.toByteArray();

				apiResult = api.addPicAttach(0, content, DataType.IMAGE_PNG);
				if(apiResult == SignatureAPI.SUCCESS){
					Toast.makeText(TestSPSAPIActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}

			}

		}
		else if (v.equals(sound_evidence)) {

			if (api != null) {
				/*
				 * addEvidence(int signIndex, byte[] content, BioType evidenceType)
				 *
				 * 添加证据一定要在签名之前
				 *
				 * 设置第三方自采证据，此数据项的哈希值会保存到证书中
				 * 参数1：signIndex 此证据所有者的index值，如1签名人，则此证书同此签名人绑定
				 * 参数2：content 此证据数据原文二进制数据
				 * 参数3：evidenceType 此证据类型，如枚举中无需要使用的具体值，设置正确evidence类型即可，如PHOTO、AUDIO。
				 */
				apiResult = api.addEvidence(0, 1,"123".getBytes(), DataType.MEDIA_WAVE);
				if(apiResult == SignatureAPI.SUCCESS){
					Toast.makeText(TestSPSAPIActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.equals(video_evidence)) {

			if (api != null) {
				/*
				 * addEvidence(int signIndex, byte[] content, BioType evidenceType)
				 *
				 * 添加证据一定要在签名之前
				 *
				 * 设置第三方自采证据，此数据项的哈希值会保存到证书中
				 * 参数1：signIndex 此证据所有者的index值，如1签名人，则此证书同此签名人绑定
				 * 参数2：content 此证据数据原文二进制数据
				 * 参数3：evidenceType 此证据类型，如枚举中无需要使用的具体值，设置正确evidence类型即可，如PHOTO、AUDIO。
				 */
				apiResult = api.addEvidence(0, 2,"123".getBytes(), DataType.MEDIA_MP4);
				if(apiResult == SignatureAPI.SUCCESS){
					Toast.makeText(TestSPSAPIActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.equals(addPhotoFile)) {

			if (api != null) {
				/*
				 * addPhotoFile(int signIndex, byte[] content)
				 *
				 * 添加图片附件
				 *
				 * 设置第三方自采证据，此数据项的哈希值会保存到证书中
				 * 参数1：index 该图片的索引值
				 * 参数2：content 此图片数据原文二进制数据
				 */

				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] content = baos.toByteArray();

				/*
				 * 图片附件
				 *
				 * 参数1:代表第几个附件，取值范围0-99
				 * 参数2:utf-8编码的图片数据
				 * 参数3:图片类型
				 */
				apiResult = api.addPicAttach(0, content,DataType.IMAGE_PNG);
				if(apiResult == SignatureAPI.SUCCESS){
					Toast.makeText(TestSPSAPIActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(TestSPSAPIActivity.this, "错误码："+apiResult , Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.equals(isReadyToUpload)) {

			if (api != null) {
				/*
				 * isReadyToGen() 判断数据是否准备就绪
				 */
				if (api.isReadyToGen() == 0) {
					Toast.makeText(app, "数据准备就绪", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(TestSPSAPIActivity.this, "数据未准备，错误码：" + api.isReadyToGen()+"", Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.equals(genData)) {
			if (api != null) {

				if (api.isReadyToGen() == 0) {
					/*
					 * genSignRequest() 获取加密数据包
					 */
					String str = (String) api.genSignRequest();// 生成上传信手书服务端报文

					if (str != null && !"".equals(str)) {
						FileUtils.writeByteArrayToPath(path_root, str.getBytes());
						Log.e("XSS", "anysign.txt is write" + path_root);
						Toast.makeText(TestSPSAPIActivity.this, "打包成功" , Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(TestSPSAPIActivity.this, "打包失败" , Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(TestSPSAPIActivity.this, "错误码：" + api.isReadyToGen()+"", Toast.LENGTH_SHORT).show();
				}


			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}

		}

		else if (v.equals(upload)) {
			if (api != null) {


				new Thread() {
					public void run() {

						String url =	null;

						if(encAlg.equals("RSA")){
//						RSA
							url = "http://192.168.136.204:80/AnySign_HttpServer/servlet/ReveivePADServer";
//							url = "http://192.168.126.108:80/AnySign_HttpServer/servlet/ReveivePADServer";
						}else if(encAlg.equals("SM2")){
//						SM2
							url = "http://192.168.136.32:80/AnySign_HttpServer/servlet/ReveivePADServer";
						}

						String result = (String)api.genSignRequest();

//						sendData(result, url);

					}
				}.start();

			}

		}
		else if (v.equals(resetAPI)) {

			if (api != null) {
				/*
				 * resetAPI() 重置api
				 */
				api.resetAPI();

			}else {
				Toast.makeText(app, "请先初始化API", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.equals(saveCacheWithSession)) {
			int result = api.saveCacheDataWithSessionId("111111", "111111");
			if (result == SignatureAPI.SUCCESS) {
				setTitle("SessionID:" + mSession);
				Toast.makeText(this,"缓存成功！",Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(this,"缓存失败！",Toast.LENGTH_SHORT).show();
				Log.e("tagtag", result + "");
			}


		}
		else if (v.equals(readCacheWithSessionId)) {
			if(api.readCacheDataWithSessionId("111111", "111111") == SignatureAPI.SUCCESS )
			{
				Log.e("tagtag", "恢复缓存数据成功");
				Toast.makeText(app, "恢复缓存数据成功", Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(app, "恢复缓存数据失败", Toast.LENGTH_LONG).show();
				Log.e("tagtag", api.readCacheDataWithSessionId(mSession, "111111") + "");
			}
		}
		else if (v.equals(deleteCacheWithSessionId)) {
			if (api.deleteCacheDataWithSessionId("111111") == SignatureAPI.SUCCESS) {
				Log.e("tagtag", "删除缓存数据成功");
				Toast.makeText(app, "删除缓存数据成功", Toast.LENGTH_LONG).show();
			}else{
				Log.e("tagtag", "删除缓存数据失败");
				Toast.makeText(app, "删除缓存数据失败", Toast.LENGTH_LONG).show();
			}
		}
		else if (v.equals(hasCacheWithSessionId)) {
			if (api.hasBufferedRecord("111111")) {
				Log.e("tagtag", "查询到相关缓存信息");
				Toast.makeText(app, "查询到相关缓存信息", Toast.LENGTH_LONG).show();
			} else {
				Log.e("tagtag", "未查询到相关缓存信息");
				Toast.makeText(app, "未查询到相关缓存信息", Toast.LENGTH_LONG).show();
			}
		}

	}



	private void initApi(){
		try {
			// 设置签名算法，默认为RSA，可以设置成SM2
			AnySignBuild.Default_Cert_EncAlg = encAlg;


			/*
			 *  初始化API
			 */
			api = new SignatureAPI(this);

			/*
			 *  设置渠道号
			 */
//			所有api接口中设置成功返回 SignatureAPI.SUCCESS（0），其他错误
			apiResult = api.setChannel("999999");
			Log.e("XSS", "apiResult -- setChannel：" + apiResult);
			if(apiResult == SignatureAPI.SUCCESS){
				Log.e("XSS", "apiResult -- setChannel：成功");
			}else{
				Log.e("XSS", "apiResult -- setChannel：失败");
			}
			/*
			 * 设置模版数据
			 */
			InputStream is = this.getResources().openRawResource(R.raw.test);
			bTemplate = new byte[is.available()];
			is.read(bTemplate);

			/**
			 * 配置此次签名对应的模板数据
			 * 参数1：表示模板类型，不可为空：如果为PDF和HTML格式，调用下面构造函数
			 *        ContextID.FORMDATA_PDF：PDF格式，ContextID.FORMDATA_HTML：HTML格式
			 * 参数2：表示模板数据byte数组类型，不可为空
			 * 参数3：业务流水号/工单号，不可为空
			 */
			apiResult = api.setOrigialContent(new OriginalContent(OriginalContent.CONTENT_TYPE_HTML, bTemplate, "111", "1130"));
			Log.e("XSS", "apiResult -- setOrigialContent：" + apiResult);

			/**
			 * 手写识别配置信息，在打开手写识别参数前需配置
			 */
			BJCAAnySignOCRCapture ocrCapture = new BJCAAnySignOCRCapture();
			ocrCapture.text = "a";
			ocrCapture.IPAddress = "http://60.247.77.116:11203/HWR/RecvServlet";
			ocrCapture.appID = "123";
			ocrCapture.count = 5;
			ocrCapture.resolution = 0;
			ocrCapture.serviceID = "999999";
			api.startOCR(ocrCapture);

			/*
			 * 注册手写签名对象，可注册多个
			 */
//			 实例化签名规则，三种方式，任选其一
//			SignRule signRule = SignRule.getInstance(SignRuleType.TYPE_KEY_WORD);
//			 方式一：使用关键字方式定位签名图片位置(根据X轴偏移量+Y轴偏移量，定位)
//			 参数1：keyWord - 关键字
//			 参数2：XOffset -  签名图片相对于关键字X轴偏移量
//			 参数3：YOffset - 签名图片相对于关键字Y轴偏移量，单位dip
//			 参数4：从Pdf的第pageNo页开始搜索此关键字，直到找到或者pdf结束为止，从1开始，默认为1
//			 参数5：从Pdf的第pageNo页开始搜索第几个关键字，直到找到或者pdf结束为止，从第1页开始
//			signRule.setKWRule(new KWRule("申请人/经办人", 10, 10, 1, 1));

//			 方式二：使用坐标定位签名图片位置
//			 参数1：left - 签名图片最左边坐标值，相对于PDF当页最左下角(0,0)点
//			 参数2：top - 签名图片顶边坐标值，相对于PDF当页最左下角(0,0)点
//			 参数3：right - 签名图片最右边坐标值，相对于PDF当页最左下角(0,0)点
//			 参数4：bottom - 签名图片底边坐标值，相对于PDF当页最左下角(0,0)点
//			 参数5：pageNo - 签名在PDF中的页码
//			 参数5：unit - 坐标单位
			SignRule signRule = SignRule.getInstance(SignRuleType.TYPE_XYZ);
			signRule.setXYZRule(new XYZRule(84, 523, 200, 411, 0,"dp"));

//			 方式三：使用在服务器端配置好的信息定位签名图片位置
//			 参数1：123为服务器配置好的签名规则
//			signRule = SignRule.getInstance(SignRuleType.TYPE_USE_SERVER_SIDE_CONFIG);
//			rule.setServerConfigRule("123");

//			 实例化签名人信息
//			参数1：姓名
//			参数2：唯一id,身份证等
//			参数3：证件类型
//						SignerCardType.TYPE_IDENTITY_CARD 身份证
//						SignerCardType.TYPE_OFFICER_CARD	军官证
//						SignerCardType.TYPE_PASSPORT_CARD	护照
//						SignerCardType.TYPE_RESIDENT_CARD	户口页
			Signer signer = new Signer("绍坎", "222", SignerCardType.TYPE_IDENTITY_CARD);
//			实例化手写签名对象
//			参数1：手写签名对象索引值
			SignatureObj obj = new SignatureObj(0,signRule,signer);
//			设置签名图片高度，单位dip
			obj.single_height = 100;
//			设置签名图片宽度，单位dip
			obj.single_width = 100;
//			设置签名对话框的高度，单位dip
			obj.single_dialog_height = 500;
//			设置签名对话框的宽度，单位dip
			obj.single_dialog_width = 600;
//          是否开启字迹轨迹记录，默认不开启
			obj.enableSignatureRecording = true;
//           手写识别开关，true为开启手写识别，false为关闭手写识别
			obj.isdistinguish = false;
//			 签名是否必须,设置为true时必须进行签名，默认true
			obj.nessesary = false;
//			 设置签名笔迹颜色，默认为黑色
			obj.penColor = Color.RED;
//			 需要显示在签名框顶栏的标题
			obj.title = "请李凤国签字";
//			单字签名框中需要突出显示部分的起始位置和结束位置
			obj.titleSpanFromOffset = 1;
//			单字签名框中需要突出显示部分的起始位置和结束位置
			obj.titleSpanToOffset = 3;

//			 注册单签签名对象
			apiResult = api.addSignatureObj(obj);
			Log.e("XSS", "apiResult -- addSignatureObj：" + apiResult);





			SignRule signRule_1 = SignRule.getInstance(SignRuleType.TYPE_KEY_WORD);
			signRule_1.setKWRule(new KWRule("电子投保单", SigAlignMethod.to_right_of_keyword, 10, 1, 1));

			Signer signer1 = new Signer("孟梅孟梅孟梅", "222", SignerCardType.TYPE_IDENTITY_CARD);
			//初始化签名实体类
			SignatureObj obj_1 = new SignatureObj(1,signRule_1,signer1);
//			设置签名人信息
			obj_1.Signer = signer1;
//			设置签名规则
			obj_1.SignRule = signRule_1;
//			设置签名图片高度，单位dip
			obj_1.single_height = 100;
//			设置签名图片宽度，单位dip
			obj_1.single_width = 100;
//			设置签名对话框的高度，单位dip
			obj_1.single_dialog_height = 500;
//			设置签名对话框的宽度，单位dip
			obj_1.single_dialog_width = 600;
//          是否开启字迹轨迹记录，默认不开启
			obj_1.enableSignatureRecording = true;
//			 签名是否必须,设置为true时必须进行签名，默认true
			obj_1.nessesary = false;
//          手写识别开关
			obj_1.isdistinguish = false;
//			 设置签名笔迹颜色，默认为黑色
			obj_1.penColor = Color.BLACK;
//			 需要显示在签名框顶栏的标题
			obj_1.title = "请李白签名";
//			单字签名框中需要突出显示部分的起始位置和结束位置
			obj_1.titleSpanFromOffset = 1;
//			单字签名框中需要突出显示部分的起始位置和结束位置
			obj_1.titleSpanToOffset = 2;

//			 注册单签签名对象
			apiResult = api.addSignatureObj(obj_1);




			SignRule signRule_custom = SignRule.getInstance(SignRuleType.TYPE_KEY_WORD);
			signRule_custom.setKWRule(new KWRule("电子投保单", SigAlignMethod.to_right_of_keyword, 10, 1, 1));

			Signer signer_custom = new Signer("孟梅孟梅孟梅", "222", SignerCardType.TYPE_IDENTITY_CARD);

			SignatureObj obj_custom = new SignatureObj(2,signRule_custom,signer_custom);
//			设置签名人信息
			obj_custom.Signer = signer_custom;
//			设置签名规则
			obj_custom.SignRule = signRule_custom;
//			设置签名图片高度，单位dip
			obj_custom.single_height = 100;
//			设置签名图片宽度，单位dip
			obj_custom.single_width = 100;
//			设置签名对话框的高度，单位dip
			obj_custom.single_dialog_height = 500;
//			设置签名对话框的宽度，单位dip
			obj_custom.single_dialog_width = 600;
			obj_custom.enableSignatureRecording = true;
//			签名是否必须,设置为true时必须进行签名，默认true
			obj_custom.nessesary = false;
//          是否打开手写识别
			obj_custom.isdistinguish = true;
//			 设置签名笔迹颜色，默认为黑色
			obj_custom.penColor = Color.RED;
//			 需要显示在签名框顶栏的标题
			obj_custom.title = "请李白签名";
//			单字签名框中需要突出显示部分的起始位置和结束位置
			obj_custom.titleSpanFromOffset = 1;
//			单字签名框中需要突出显示部分的起始位置和结束位置
			obj_custom.titleSpanToOffset = 2;

			try {
				XSSCustomView customView = new XSSCustomView(this, obj_custom.getWriteObj(obj_custom));
				obj_custom.customSignature = customView;
			} catch (Throwable e) {
				e.printStackTrace();
			}

//			 注册单签签名对象
			apiResult = api.addSignatureObj(obj_custom);


			/*
			 * 注册批注对象，可注册多个
			 */
//			 实例化签名规则，签名规则跟手写签名一样
			signRule = SignRule.getInstance(SignRuleType.TYPE_KEY_WORD);
//
			signRule.setKWRule(new KWRule("本人已阅读", 200, -90, 0, 0));

//			 实例化签名人信息
//			参数1：姓名
//			参数2：唯一id,身份证等
//			参数3：证件类型
//						SignerCardType.TYPE_IDENTITY_CARD 身份证
//						SignerCardType.TYPE_OFFICER_CARD	军官证
//						SignerCardType.TYPE_PASSPORT_CARD	护照
//						SignerCardType.TYPE_RESIDENT_CARD	户口页
			signer = new Signer("11", "222", SignerCardType.TYPE_IDENTITY_CARD);
//			实例化批注对象
//			参数1：批注对象索引值
			Signer signer_commitment = new Signer("123456", "123456");
			SignRule signRule_mass1 = SignRule.getInstance(SignRuleType.TYPE_KEY_WORD);//修复签名规则
// 			 方式一：使用关键字方式定位签名图片位置(根据X轴偏移量+Y轴偏移量，定位)
//			 参数1：keyWord - 关键字
//			 参数2：XOffset -  签名图片相对于关键字X轴偏移量
//			 参数3：YOffset - 签名图片相对于关键字Y轴偏移量，单位dip
//			 参数4：从Pdf的第pageNo页开始搜索此关键字，直到找到或者pdf结束为止，从1开始，默认为1
//			 参数5：从Pdf的第pageNo页开始搜索第几个关键字，直到找到或者pdf结束为止，从第1页开始
			signRule_mass1.setKWRule(new KWRule("本人已阅读", 200, -200, 0, 0));

			CommentObj massobj = new CommentObj(0,signRule_mass1,signer_commitment);

			/**
			 * CommentInputType 批注的类型
			 * 			Scrollable  网格批注（适配在pad上）
			 *  		Normal   动画批注（适配在手机上）
			 *  		WhiteBoard 白板批注
			 */
			massobj.mass_dlg_type = CommentInputType.Scrollable;

//			批注内容
//			massobj.commitment = "本人已阅读保险条款、产品说明书和投保提示书，了解本产品特点和保单利益的不确定性";
			massobj.commitment = "签名签名签名签名";
//			生成的签名图片中单行显示的字数
			massobj.mass_words_in_single_line = 6;
//			生成的签名图片中单个字的高
			massobj.mass_word_height = 200;
//			生成的签名图片中单个字的宽
			massobj.mass_word_width = 200;
//			签名是否必须,设置为true时必须进行签名，默认true
			massobj.nessesary = true;
//			设置提示字的大小
			massobj.editBarTextSize = 20;
//			设置提示字的颜色
			massobj.editBarTextColor = Color.RED;
//			设置当前正在签署提示字的倍数
			massobj.currentEditBarTextSize = -2f;
//			设置当前正在签署提示字的颜色
			massobj.currentEditBarTextColor = Color.BLUE;
//			识别错误提示语
			massobj.distinguishErrorText = "错误";
//			背景字是否显示
			massobj.isShowBgText = true;
//			是否开启手写识别开关
			massobj.isdistinguish = false;
//			设置笔迹颜色，默认为黑色
			massobj.penColor = Color.RED;
//			注册批注对象
			apiResult = api.addCommentObj(massobj);
			Log.e("XSS", "apiResult -- addCommentObj：" + apiResult);






//			实例化批注对象
//			参数1：批注对象索引值
			CommentObj massobj_1 = new CommentObj(1,signRule,signer);
//			多字输入框类型，默认为CommentInputType.Scrollable
			/**
			 * CommentInputType 批注的类型
			 * 			Scrollable  网格批注（适配在pad上）
			 *  		Normal   动画批注（适配在手机上）
			 *  		WhiteBoard 白板批注
			 */
			massobj_1.mass_dlg_type = CommentInputType.Normal;
//			批注内容
//			massobj.commitment = "本人已阅读保险条款、产品说明书和投保提示书，了解本产品的特点和保单利益的不确定";
			massobj_1.commitment = "批注一";
//			生成的签名图片中单行显示的字数
			massobj_1.mass_words_in_single_line = 4;
//			生成的签名图片中单个字的高
			massobj_1.mass_word_height = 200;
//			生成的签名图片中单个字的宽
			massobj_1.mass_word_width = 200;
//			 签名是否必须,设置为true时必须进行签名，默认true
			massobj_1.nessesary = false;
//			设置提示字的大小
			massobj_1.editBarTextSize = 20;
//			设置提示字的颜色
			massobj_1.editBarTextColor = Color.RED;
//			设置当前正在签署提示字的倍数
			massobj_1.currentEditBarTextSize = -2f;
//			设置当前正在签署提示字的颜色
			massobj_1.currentEditBarTextColor = Color.BLUE;
//			识别错误提示语
			massobj_1.distinguishErrorText = "错误";
//			背景字是否显示
			massobj_1.isShowBgText = true;
//			是否开启手写识别开关
			massobj_1.isdistinguish = false;
//			设置笔迹颜色，默认为黑色
			massobj_1.penColor = Color.RED;
//			 注册批注对象
			apiResult = api.addCommentObj(massobj_1);
			Log.e("XSS", "apiResult -- addCommentObj：" + apiResult);





//			实例化批注对象
//			参数1：批注对象索引值
			CommentObj massobj_2 = new CommentObj(2,signRule,signer);
			/**
			 * CommentInputType 批注的类型
			 * 			Scrollable  网格批注（适配在pad上）
			 *  		Normal   动画批注（适配在手机上）
			 *  		WhiteBoard 白板批注
			 */
			massobj_2.mass_dlg_type = CommentInputType.WhiteBoard;

//			批注内容
//			massobj.commitment = "本人已阅读保险条款、产品说明书和投保提示书，了解本产品的特点和保单利益的不确定";
			massobj_2.commitment = "批注二";
//			生成的签名图片中单行显示的字数
			massobj_2.mass_words_in_single_line = 4;
//			生成的签名图片中单个字的高
			massobj_2.mass_word_height = 100;
//			生成的签名图片中单个字的宽
			massobj_2.mass_word_width = 100;
//			 签名是否必须,设置为true时必须进行签名，默认true
			massobj_2.nessesary = false;
//			设置提示字的大小
			massobj_2.editBarTextSize = 20;
//			设置提示字的颜色
			massobj_2.editBarTextColor = Color.RED;
//			设置当前正在签署提示字的倍数
			massobj_2.currentEditBarTextSize = -2f;
//			设置当前正在签署提示字的颜色
			massobj_2.currentEditBarTextColor = Color.BLUE;
//			识别错误提示语
			massobj_2.distinguishErrorText = "错误";
//			背景字是否显示
			massobj_2.isShowBgText = true;
//			是否开启手写识别开关
			massobj_2.isdistinguish = false;
//			设置笔迹颜色，默认为黑色
			massobj_2.penColor = Color.RED;
//			 注册批注对象
			apiResult = api.addCommentObj(massobj_2);
			Log.e("XSS", "apiResult -- addCommentObj：" + apiResult);

			/*
			 * 注册单位章
			 */
//			 实例化签名人信息
//			参数1：单位名称
//			参数2：唯一id,身份证等
			signer = new Signer("11", "222");
//			实例化签章对象
//			参数1：服务端配置好的公章规则
//			参数2：公章信息
			CachetObj cachetObj = new CachetObj("123456789", signer);
//			是否开启时间戳
			cachetObj.IsTSS = false;
//			注册单位章
//			apiResult = api.addChachetObj(cachetObj);
			Log.e("XSS", "apiResult -- addChachetObj：" + apiResult);



		} catch (Exception e1) {
			e1.printStackTrace();
		}
		/*
		 * 注册签名结果回调函数
		 */
		api.setOnSignatureResultListener(new OnSignatureResultListener() {


			@Override
			public void onSignResult(SignResult signResult)
			{
//				在这里取签名图片
//				参数1：签名索引值
//				参数2：签名图片
				showImgPreviewDlg(signResult.signature);

				Log.e("XSS", "onSignResult signIndex : " + signResult.signIndex + "  resultCode : " + signResult.resultCode+ "  signType : " + signResult.signType+ "  pointStack : " + signResult.pointStack);

//				ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
//				signature.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
//				byte[] content1 = baos1.toByteArray();
//				
//				ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//				signature.compress(Bitmap.CompressFormat.PNG, 100, baos2);
//				byte[] content2 = baos2.toByteArray();
//
//				String path = Environment.getExternalStorageDirectory().getPath();
//				FileUtils.writeByteArrayToPath(path+"/1.jpeg", content2);
//				FileUtils.writeByteArrayToPath(path+"/2.png", content2);
//				
//				String a = WSecxUtil.sha1DigestBase64(content1);
//				String b = WSecxUtil.sha1DigestBase64(content2);
//				
//				Log.e("tagtag", a);
//				Log.e("tagtag", b);
//				try
//				{
//					String content3 = FileUtils.readByteArrayToPath(path+"/2.jpeg");
//					String content4 = FileUtils.readByteArrayToPath(path+"/1.png");
//					
//					String c = WSecxUtil.sha1DigestBase64(content3.getBytes());
//					String d = WSecxUtil.sha1DigestBase64(content4.getBytes());
//					
//					Log.e("tagtag", c);
//					Log.e("tagtag", d);
//				} catch (IOException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

			}

			@Override
			public void onCancel(int index, SignatureType signType)
			{
				Log.e("XSS", "onCancel index : " + index + "  signType : " + signType);

			}

			@Override
			public void onDismiss(int index, SignatureType signType)
			{
				Log.e("XSS", "onDismiss index : " + index + "  signType : " + signType);
			}

		});

	}

	private void showImgPreviewDlg(Bitmap img) {
		ImageView iv = new ImageView(app);
		iv.setBackgroundColor(Color.WHITE);
		iv.setImageBitmap(img);
		new AlertDialog.Builder(app).setView(iv).show();
	}

	@Override
	public void finish() {
		if (null != api)

			/*
			 * finalizeAPI() 释放api
			 * */
			api.finalizeAPI();
		super.finish();
	}


	public static boolean isPad(Context context) {
		return (context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK)
				>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}


//	private String sendData(String data, String url) {
//		String result = "";
//		Log.e("XSS", url);
//
//		HttpClient hc = new DefaultHttpClient();
//		HttpResponse httpResponse = null;
//		HttpPost httpPost = new HttpPost(url);
//		List<NameValuePair> postparams = new ArrayList<NameValuePair>();
//		postparams.add(new BasicNameValuePair("padjson", data));
//		try {
//			httpPost.setEntity(new UrlEncodedFormEntity(postparams, HTTP.UTF_8));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block 
//			e.printStackTrace();
//		}
//		httpPost.getParams().setParameter(
//				CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//		httpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
//				60000);
//		try {
//			httpResponse = hc.execute(httpPost);
//			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				// 第3步：使用getEntity方法获得返回结果
//				result = EntityUtils.toString(httpResponse.getEntity());
//				Log.e("XSS", result);
//				httpPost.abort();
//				
//			} else {
//				result = "服务端返回错误代码:"
//						+ httpResponse.getStatusLine().getStatusCode();
//				Log.e("XSS", result);
//			} 
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Log.e("XSS", e.getMessage());
//			e.printStackTrace();
//			
//		}
//
//		return result;
//	}

}
