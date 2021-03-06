package kr.or.ddit.rms.mainpage.sign_up;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import aesExam.Aes256;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.rms.main.Main;
import kr.or.ddit.rms.mainpage.login.Login_controller;
import kr.or.ddit.rms.mainpage.sign_up.common.AlertDialog;
import kr.or.ddit.rms.mainpage.sign_up.common.CaptChaApiProcess;
import kr.or.ddit.rms.vo.CustomVO;
import kr.or.ddit.rms.vo.MemberVO;
import kr.or.ddit.rms.vo.ShelterVO;

public class Signup_Shel_Controller implements Initializable {
	
	public static Registry reg;
	public static ISignupService ss = null;
	
	public CaptChaApiProcess captchaApi = new CaptChaApiProcess();
	public AlertDialog alertDlg = new AlertDialog();
	
	public static ShelterVO vo;
	public static MemberVO membervo;
	
	boolean id_check = false; //아이디 중복확인
	
	   @FXML JFXTextField SignupShel_Id_Txt;
	   @FXML JFXPasswordField SignupShel_Pw1_Txt;
	   @FXML JFXPasswordField SignupShel_Pw2_Txt;
	   @FXML JFXTextField SignupShel_Name_Txt;
	   @FXML JFXTextField SignupShel_Zipcod_Txt;
	   //@FXML JFXTextField SignupShel_Addr1_Txt;
	   @FXML JFXTextField SignupShel_Tel_Txt;
	   @FXML JFXTextField SignupShel_Email_Txt;
	   @FXML JFXTextField SignupShel_Num_Txt;
	   @FXML JFXButton SignupShel_Id_Btn;
	   //@FXML JFXButton SignupShel_Zipcod_Btn;
	   @FXML JFXTextField SignupShel_Addr2_Txt;
	   @FXML JFXTextField SignupShel_Captcha_Txt;
	   @FXML JFXButton SignupShel_CaptchaOther_Btn;
	   @FXML JFXButton SignupShel_Join_Btn;
	   @FXML JFXButton SignupShel_Cancel_Btn;
	   @FXML ImageView captchaImage;
	   @FXML Label captchaKey;
	   
	   @FXML JFXButton Close_Btn;
	   
	   

//	{
//		try {
//			reg = LocateRegistry.getRegistry("localhost", 4378);
//			ss = (ISignupService) reg.lookup("ss");
//		} catch (RemoteException e1) {
//			e1.printStackTrace();
//		} catch (NotBoundException e1) {
//			e1.printStackTrace();
//		}
//	}
	   
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		HashMap callRetMap = (HashMap) captchaApi.CaptChaApiCall("0");
		
		captchaImage.setImage((Image) callRetMap.get("IMG"));
		captchaKey.setText((String) callRetMap.get("RETKEY"));
		
		SignupShel_CaptchaOther_Btn.setOnAction(e -> {
	         // 1. 보안 문자 입력받는 TextField 값 초기화
			SignupShel_Captcha_Txt.setText("");
	         // 2. 보안 문자를 받기 위해 API 호출 및 KEY 발급, 이미지 다운로드(생성)
	         HashMap callMap = (HashMap) captchaApi.CaptChaApiCall("0");
	         // 3. 보안 문자 이미지 화면에 재 세팅
	         captchaImage.setImage((Image) callMap.get("IMG"));
	         // 4. 보안 문자 확인에 필요한 발급 받은 KEY 화면에 숨겨서 세팅
	         captchaKey.setText((String) callMap.get("RETKEY"));
	      });
		
		Close_Btn.setOnAction(e->{
			Signup_Page_Controller.Signup_Page_view.close();
		});
		
		SignupShel_Cancel_Btn.setOnAction(e->{
			Signup_Page_Controller.Signup_Page_view.close();
		});
		
		
		SignupShel_Join_Btn.setOnAction(e -> {
			
	            String id        = SignupShel_Id_Txt.getText();
	            String pw        = SignupShel_Pw1_Txt.getText();
	            String pw_check  = SignupShel_Pw2_Txt.getText();
	            String name      = SignupShel_Name_Txt.getText();
	           //String addr1     = SignupShel_Addr1_Txt.getText();
	            String addr2     = SignupShel_Addr2_Txt.getText();
	            String tel       = SignupShel_Tel_Txt.getText();
	            String email     = SignupShel_Email_Txt.getText();
	            String num       = SignupShel_Num_Txt.getText();
	            String strCaptChaValue = SignupShel_Captcha_Txt.getText();
	            
	            boolean captcha = true;
				boolean pwflag = false; //비밀번호 맞는지 검사
				boolean rule_check = true; //유효성 검사

				HashMap map = (HashMap) captchaApi.APIExamCaptchaNkeyResult(captchaKey.getText(), strCaptChaValue);

				String strCaptChaRetValue = (String) map.get("result");

				if (!strCaptChaRetValue.equals("true")) {
					captcha = false;
					// 아래 4줄 보안문자 다시보기 이벤트에 넣으면 됨
					SignupShel_Captcha_Txt.setText("");
					HashMap callMap = (HashMap) captchaApi.CaptChaApiCall("0");
					captchaImage.setImage((Image) callMap.get("IMG"));
					captchaKey.setText((String) callMap.get("RETKEY"));
				}

				// 아이디 유효성
				Pattern chkid = Pattern.compile("^[a-z0-9_]{4,12}"); //영어(소문자만), 숫자, 4~12자
				Matcher idcheck = chkid.matcher(SignupShel_Id_Txt.getText());

				Pattern chkpw = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]|.*[0-9]).{8,20}$"); //영어(대소문자), 숫자, 특수문자, 8~20자
				Matcher pwcheck = chkpw.matcher(SignupShel_Pw1_Txt.getText());
				
				Pattern chkphone = Pattern.compile("^[0-9_]{10,11}"); //숫자
				Matcher phonecheck = chkphone.matcher(SignupShel_Tel_Txt.getText());
				
				Pattern chkemail = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]|.*[0-9]).{10,25}$"); //영어(대소문자), 숫자, 특수문자, 10~25자
				Matcher emailcheck = chkemail.matcher(SignupShel_Email_Txt.getText());
				

				//입력한 비밀번호 두개가 서로 맞는지 확인
				if (SignupShel_Pw1_Txt.getText().equals(SignupShel_Pw2_Txt.getText())) {
					pwflag = true;
				} else {
					
					pwflag = false;
				}

				if (!(SignupShel_Id_Txt.getText().isEmpty() || SignupShel_Pw1_Txt.getText().isEmpty() ||
			       	  SignupShel_Pw2_Txt.getText().isEmpty() || SignupShel_Name_Txt.getText().isEmpty() ||
			       	  SignupShel_Addr2_Txt.getText().isEmpty() || SignupShel_Tel_Txt.getText().isEmpty() ||
			       	  SignupShel_Email_Txt.getText().isEmpty() || SignupShel_Num_Txt.getText().isEmpty())) {
				

					if (!idcheck.matches()) {
						alertDlg.ShowAlert("경고", "", "아이디는 영문(소문자),숫자,4~12자로 입력하세요.", AlertType.ERROR);
						rule_check = false;
					}
					if (!pwcheck.matches()) {
						alertDlg.ShowAlert("경고", "", "비밀번호는 영문(대소문자),숫자,특수문자,8~20자로 입력하세요.", AlertType.ERROR);
						rule_check = false;
					}
					if (!phonecheck.matches()) {
						alertDlg.ShowAlert("경고", "", "전화번호는 숫자만 입력하세요.", AlertType.ERROR);
						rule_check = false;
					}
					if (!emailcheck.matches()) {
						alertDlg.ShowAlert("경고", "", "이메일은  영문(대소문자),10~25자로 입력하세요.", AlertType.ERROR);
						rule_check = false;
					}
					if (idcheck.matches() && pwcheck.matches() && phonecheck.matches() && emailcheck.matches()) {
						if (pwflag) {
							if (captcha) {

								// 아래 부분은 보안문자 확인 성공이므로 회원가입 정보를 DB에 저장하는 부분을 코딩
								// alertDlg.ShowAlert("SUCCESS", "", "보안 문자 입력값이 맞았습니다.",
								// AlertType.INFORMATION);
								 ShelterVO sheltervo = new ShelterVO();
				            	 sheltervo.setShel_id(id);
				            	 sheltervo.setShel_name(name);
				            	 sheltervo.setShel_loc(addr2); //확인필요
				            	 sheltervo.setShel_tel(tel);
				            	 sheltervo.setShel_email(email);
				            	 sheltervo.setShel_bsnum(num);
				                 
				                  MemberVO memVO = new MemberVO();
				                  memVO.setMem_id(id);
				                  
				                  String key = "RescuedogManagement"; 
									Aes256 aes256;
									String encText = null;
									try {
										aes256 = new Aes256(key);
										encText = aes256.aesEncode(pw);
									} catch (UnsupportedEncodingException e2) {
										e2.printStackTrace();
									} catch (InvalidKeyException e1) {
										e1.printStackTrace();
									} catch (NoSuchAlgorithmException e1) {
										e1.printStackTrace();
									} catch (NoSuchPaddingException e1) {
										e1.printStackTrace();
									} catch (InvalidAlgorithmParameterException e1) {
										e1.printStackTrace();
									} catch (IllegalBlockSizeException e1) {
										e1.printStackTrace();
									} catch (BadPaddingException e1) {
										e1.printStackTrace();
									}
				                  memVO.setMem_pw(encText);
				                  memVO.setMem_author("2"); //보호기관
				                  memVO.setMem_report("0");
								int ShelInf2 = 0;
								int ShelInf1 = 0;
								try {
									 ShelInf2 = Main.ss.insertMember(memVO);
					                 ShelInf1 = Main.ss.insertShelter(sheltervo);
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
								if (ShelInf2 > 0 && ShelInf1 > 0) {

									alertDlg.ShowAlert("회원가입성공", "회원가입이", "완료 되었습니다.", AlertType.INFORMATION);
									Signup_Page_Controller.Signup_Page_view.close();
								}
							} else {
								alertDlg.ShowAlert("경고", "", "보안문자 입력값이 틀렸습니다.", AlertType.ERROR);
							}
						} else {
							alertDlg.ShowAlert("FAIL", "", "비밀번호가 일치하지 않습니다.", AlertType.ERROR);
						}
					} 
				} else {
					alertDlg.ShowAlert("FAIL", "", "공백없이 입력하세요.", AlertType.ERROR);
				}

			});
			
		SignupShel_Id_Btn.setOnAction(event->{
				boolean flag = false;
				List<MemberVO> list = null;
			
				try {
					list = Main.ss.getMemberAll();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i).getMem_id().equals(SignupShel_Id_Txt.getText())) {
						flag = true;
					} 
				}
				if(flag) {
					id_check = false;
					alertDlg.ShowAlert("경고", "", "이미 사용중인 아이디입니다.", AlertType.ERROR);
				} else {
					id_check = true;
					alertDlg.ShowAlert("success", "", "사용 가능한 아이디입니다.", AlertType.INFORMATION);
				}

		});
		
	}
	   
	   
}
	            
