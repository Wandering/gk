package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.CaptchaConst;
import cn.thinkjoy.gk.constant.VerificationKeyConst;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Controller
@Scope("prototype")
@RequestMapping("/verifyCode")
public class VerificationCodeController extends BaseController {

	private static final Logger LOGGER= LoggerFactory.getLogger(VerificationCodeController.class);


	@RequestMapping(value = "randomVerifyCode",method = RequestMethod.GET)
	@ResponseBody
	public void getVerifyCode(@RequestParam(value="type",required = false) String type){
		if(org.apache.commons.lang.StringUtils.isBlank(type)){
			throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
		}
		BufferedImage image = null;
		try {
			image = new BufferedImage(CaptchaConst.PIC_WIDTH, CaptchaConst.PIC_HEIGHT, BufferedImage.TYPE_INT_BGR);
		} catch (Exception e) {
			new BizException(ERRORCODE.CREATE_VERIFY_CODE_ERROR.getCode(), ERRORCODE.CREATE_VERIFY_CODE_ERROR.getMessage());
		}
		try {
//			Graphics gd = image.getGraphics();
//			// 创建一个随机数生成器类
//			Random random = new Random();
//			// 将图像填充为白色
//			gd.setColor(Color.WHITE);
//			gd.fillRect(0, 0, CaptchaConst.PIC_WIDTH, CaptchaConst.PIC_HEIGHT);
//			// 创建字体，字体的大小应该根据图片的高度来定。
//			// 设置字体。
//			gd.setFont(CaptchaConst.FIXEDSYS);
//
//			// 画边框。
//			gd.setColor(Color.BLACK);
//			gd.drawRect(0, 0, CaptchaConst.PIC_WIDTH- 1, CaptchaConst.PIC_HEIGHT - 1);
//
//			// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
//			gd.setColor(Color.BLACK);
//			for (int i = 0; i < 40; i++) {
//				int x = random.nextInt(CaptchaConst.PIC_WIDTH);
//				int y = random.nextInt(CaptchaConst.PIC_HEIGHT);
//				int xl = random.nextInt(12);
//				int yl = random.nextInt(12);
//				gd.drawLine(x, y, x + xl, y + yl);
//			}
//
//			// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
//			StringBuffer randomCode = new StringBuffer();
//			int red = 0, green = 0, blue = 0;
//
//			// 随机产生codeCount数字的验证码。
//			for (int i = 0; i < CaptchaConst.RANDOM_STRING_NUM; i++) {
//				// 得到随机产生的验证码数字。
//				String code = String.valueOf(CaptchaConst.CODE_SEQUENCE[random.nextInt(36)]);
//				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
//				red = random.nextInt(255);
//				green = random.nextInt(255);
//				blue = random.nextInt(255);
//
//				// 用随机产生的颜色将验证码绘制到图像中。
//				gd.setColor(new Color(red, green, blue));
//				gd.drawString(code, (i + 1) * CaptchaConst.FONT_SPACING, CaptchaConst.TRANSLATE_START);
//
//				// 将产生的四个随机数组合在一起。
//				randomCode.append(code);
//			}

			String randomCode = CaptchaUtil.getRandomString(image).toUpperCase();
//
			String value = getCookieValue();
			if(VerificationKeyConst.COLLEGE_RECOMMENDATION_TYPE==Integer.valueOf(type)){
				session.setAttribute(VerificationKeyConst.COLLEGE_RECOMMENDATION+value, randomCode.toString());
			}else if(VerificationKeyConst.COLLEGE_EVALUATION_TYPE==Integer.valueOf(type)){
				session.setAttribute(VerificationKeyConst.COLLEGE_EVALUATION+value, randomCode.toString());
			}else if(VerificationKeyConst.GET_THE_ORDER_TYPE==Integer.valueOf(type)){
				session.setAttribute(VerificationKeyConst.GET_THE_ORDER+value, randomCode.toString());
			}else if(VerificationKeyConst.GET_BATCH_TYPE==Integer.valueOf(type)){
				session.setAttribute(VerificationKeyConst.GET_BATCH+value, randomCode.toString());
			}else{
				throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
			}
//			response.setHeader("Pragma", "no-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setDateHeader("Expires", 0);
//			response.setContentType("image/jpeg");
			//将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, CaptchaConst.JPEG, response.getOutputStream());
		} catch (Exception e) {
			throw new BizException(ERRORCODE.CREATE_VERIFY_CODE_ERROR.getCode(), ERRORCODE.CREATE_VERIFY_CODE_ERROR.getMessage());
		}
	}


	@RequestMapping(value = "verifyCode",method = RequestMethod.GET)
	@ResponseBody
	public boolean verifyCode(@RequestParam(value="type",required = false) String type,
							 @RequestParam(value="code",required = false) String code){
		Object resultCode = null;
		if(VerificationKeyConst.COLLEGE_RECOMMENDATION_TYPE==Integer.valueOf(type)){
			resultCode = session.getAttribute(VerificationKeyConst.COLLEGE_RECOMMENDATION);
		}else if(VerificationKeyConst.COLLEGE_EVALUATION_TYPE==Integer.valueOf(type)){
			resultCode = session.getAttribute(VerificationKeyConst.COLLEGE_EVALUATION);
		}else if(VerificationKeyConst.GET_THE_ORDER_TYPE==Integer.valueOf(type)){
			resultCode = session.getAttribute(VerificationKeyConst.GET_THE_ORDER);
		}else{
			throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
		}

		boolean flag = true;
		if(resultCode==null){
			flag = false;
			throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
		}

		if(!resultCode.toString().equals(code)){
			flag = false;
			throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
		}

		return flag;
	}

}
