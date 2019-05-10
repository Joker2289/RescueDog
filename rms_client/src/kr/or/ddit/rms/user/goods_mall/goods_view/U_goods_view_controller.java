package kr.or.ddit.rms.user.goods_mall.goods_view;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.text.xml.simpleparser.NewLineHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.rms.main.Main;
import kr.or.ddit.rms.mainpage.login.Login_controller;
import kr.or.ddit.rms.vo.CartVO;
import kr.or.ddit.rms.vo.ProdVO;

public class U_goods_view_controller implements Initializable {

	@FXML AnchorPane Good_View_Pane;
	@FXML JFXTextField Goods_Search_Txt;
	@FXML JFXButton Goods_Bascket_Btn;
	@FXML JFXButton Goods_Search_Btn;
	@FXML Label Hide_Lbl;
	
	@FXML FontAwesomeIcon bascket;
	public static Stage goods_view_dialog;
	
	
	//DB의 모든 상품정보를 담을 리스트
	List<ProdVO> list = null;
	
	//상품 정보(AccessibleText에 정보값 숨김)
	public static ImageView log_img = null; 
	public static JFXButton log_btn = null; 
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		try {
			list = Main.gm_u.getProdAll();
			showProdList(list);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//검색 버튼 이벤트
		Goods_Search_Btn.setOnAction(e->{
			String searchText = Goods_Search_Txt.getText();
			ProdVO vo = new ProdVO();
			vo.setProd_name(searchText);
	
			try {
				List<ProdVO> searchList = Main.gm_u.getLikeProd(vo);
				
				if(searchList.size() == 0) {
					Good_View_Pane.getChildren().clear();
					Hide_Lbl.setText("검색된 상품이 없습니다");
					VBox v = new VBox();
					v.getChildren().add(Hide_Lbl);
					v.setPrefWidth(1100);
					v.setPrefHeight(524);
					v.setAlignment(Pos.CENTER);
					Good_View_Pane.getChildren().add(v);
					return;
				}
				list = null;
				showProdList(searchList);
				
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
		});
		
		
		
		
//		HBox hBox = new HBox();
//		//VBox vBox = new VBox();
//		
//		VBox mainBox = new VBox();
//		mainBox.setPrefWidth(1050);
//		//mainBox.setAlignment(Pos.CENTER); // 가운제 출력
//		
//		try {
//			
//			list = Main.gm_u.getProdAll();
//			int x = 0;
//			int y;
//			if((list.size() % 3) != 0) {
//				x = 1;
//			}
//			
//			for(int j=0; j<list.size()/3 + x; j++) {
//				HBox H = new HBox();
//				H.setPrefWidth(1050.0);
//				H.setPrefHeight(350.0);
//				
//				for(int i=0; i<3; i++) {
//					y = i+(j*3);
//					//제약조건
//					if(list.size() < y+1) {
//						break;
//					}
//					VBox V = new VBox();
//					V.setPrefWidth(350);
//					V.setPrefHeight(350);
//					V.setAlignment(Pos.CENTER);
//					
//					ImageView goods_view = new ImageView();
//					Image img = new Image("kr/or/ddit/rms/user/goods_mall/goods_img/"+ list.get(i+(j*3)).getProd_img());
//					goods_view.setImage(img);
//					goods_view.setFitWidth(292.0);
//					goods_view.setFitHeight(216.0);
//					goods_view.setAccessibleText(list.get(i+(j*3)).getProd_name()
//							+"/"+list.get(i+(j*3)).getPrice()
//							+"/"+list.get(i+(j*3)).getProd_img()
//							+"/"+list.get(i+(j*3)).getProd_info()
//							+"/"+list.get(i+(j*3)).getProd_num());
//					
//					//굿즈 이미지 클릭
//					goods_view.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
//						 
//						log_img = (ImageView) e.getSource();
//						System.out.println(log_img.getAccessibleText());
//						//새창 띄우기
//						goods_view_dialog = new Stage(StageStyle.UTILITY);
//						
//						goods_view_dialog.initModality(Modality.APPLICATION_MODAL);
//						//dialog.initOwner();
//						goods_view_dialog.setTitle("굿즈 상세조회");
//						
//						Parent parent = null;
//						try {
//							parent = FXMLLoader.load(getClass().getResource("goods_mall_detail_view.fxml"));
//						} catch(IOException ee) {
//							ee.printStackTrace();
//						}
//						
//						Scene scene = new Scene(parent);
//						
//						goods_view_dialog.setScene(scene);
//						goods_view_dialog.setResizable(false);//크기고정
//						goods_view_dialog.show();
//						
//					});
//					
//					
//					Label goods_num = new Label();
//					goods_num.setText(list.get(i+(j*3)).getProd_num());
//					goods_num.setAlignment(Pos.CENTER);
//					//goods_num.setTextFill("#c92d2d");
//					goods_num.setFont(Font.font(".Apple SD Gothic NeoI", 22));
//					
//					Label goods_name = new Label();
//					goods_name.setText(list.get(i+(j*3)).getProd_name());
//					goods_name.setAlignment(Pos.CENTER);
//					goods_name.setFont(Font.font(".Apple SD Gothic NeoI", 22));
//					
//					Label goods_price = new Label();
//					goods_price.setText(list.get(i+(j*3)).getPrice() + " Won");
//					goods_price.setAlignment(Pos.CENTER);
//					goods_price.setFont(Font.font(".Apple SD Gothic NeoI", 22));
//					
//					FontAwesomeIcon icon = new FontAwesomeIcon();
//					icon.setIconName("CART_PLUS");
//					icon.setSize("2em");
//					
//					FontAwesomeIcon icon2 = new FontAwesomeIcon();
//					icon2.setIconName("WON");
//					icon2.setSize("2em");
//					
//					
//					JFXButton add_bascket_btn = new JFXButton();
//					add_bascket_btn.setGraphic(icon);
//					add_bascket_btn.setAccessibleText(list.get(i+(j*3)).getProd_num());
//					
//					//VBox에 차례로 담는다
//					V.getChildren().add(goods_view);
//					V.getChildren().add(goods_num);
//					V.getChildren().add(goods_name);
//					V.getChildren().add(goods_price);
//					V.getChildren().add(add_bascket_btn);
//					
//					H.getChildren().add(V);
//					
//					//장바구니 아이콘 클릭 -> 장바구니 추가
//					add_bascket_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
//						CartVO cvo = new CartVO();
//						log_btn = (JFXButton) e.getSource();
//						String prod_info = log_btn.getAccessibleText();
//						
//
//						try {
//							 //먼저 custom_id 와 prod_num 이 있는지 확인한다 
//							 cvo.setCustom_id(Login_controller.log_c.getCustom_id());
//							 cvo.setProd_num(prod_info);
//							 List<CartVO> search_result = Main.gm_u.getSearchCart(cvo);
//							
//							 //검색 결과가 있다면 상품 수량 증가(update문)
//							 if(search_result.size() != 0) {
//								 //검색결과로 수량을 가져옴
//								 int cnt = Integer.parseInt(search_result.get(0).getCart_cnt());
//								 
//								 //아이디와 상품번호가 같은 컬럼의 수량에 +1
//								 cvo.setCustom_id(Login_controller.log_c.getCustom_id());
//								 cvo.setProd_num(prod_info);
//								 cvo.setCart_cnt((cnt+1)+"");
//								 
//								 //업데이트
//								 Main.gm_u.updateCart(cvo);
//								 
//								 alertTest(prod_info + "번 상품 수량 증가");
//							 }
//							 
//							 
//							 //검색결과가 없다면 상품 추가
//							 if(search_result.size() == 0) {
//								//상품결과가 없다면 장바구니 추가(insert문)
//								cvo.setProd_num(prod_info);
//								cvo.setCustom_id(Login_controller.log_c.getCustom_id());
//								cvo.setCart_cnt("1");
//								int cnt = Main.gm_u.insertCart(cvo);
//								
//								alertTest(prod_info + "번 상품 장바구니 추가");
//							}
//							 
//						} catch (RemoteException e1) {
//							e1.printStackTrace();
//						}
//						
//					});
//					
//					
//				}//i for문 종료
//				
//				mainBox.getChildren().add(H);
//			}//for문 종료
//			
//			Good_View_Pane.getChildren().add(mainBox);
//		} catch (RemoteException e1) {
//			e1.printStackTrace();
//		}
		
	}//initializable 종료
	
	private void showProdList(List<ProdVO> ex_lsit) {
		HBox hBox = new HBox();
		//VBox vBox = new VBox();
		
		VBox mainBox = new VBox();
		mainBox.setPrefWidth(1050);
		//mainBox.setAlignment(Pos.CENTER); // 가운제 출력
		
		//list = Main.gm_u.getProdAll();
		int x = 0;
		int y;
		if((ex_lsit.size() % 3) != 0) {
			x = 1;
		}
		
		for(int j=0; j<ex_lsit.size()/3 + x; j++) {
			HBox H = new HBox();
			H.setPrefWidth(1050.0);
			H.setPrefHeight(350.0);
			
			for(int i=0; i<3; i++) {
				y = i+(j*3);
				//제약조건
				if(ex_lsit.size() < y+1) {
					break;
				}
				VBox V = new VBox();
				V.setPrefWidth(350);
				V.setPrefHeight(350);
				V.setAlignment(Pos.CENTER);
				
				ImageView goods_view = new ImageView();
				Image img = new Image("file:\\\\Sem-pc\\공유폴더\\Rescuedog\\goods_img\\"+ ex_lsit.get(i+(j*3)).getProd_img());
//				Image img = new Image("image/prod/"+ ex_lsit.get(i+(j*3)).getProd_img());
				goods_view.setImage(img);
				goods_view.setFitWidth(292.0);
				goods_view.setFitHeight(216.0);
				goods_view.setAccessibleText(ex_lsit.get(i+(j*3)).getProd_name()
						+"/"+ex_lsit.get(i+(j*3)).getPrice()
						+"/"+ex_lsit.get(i+(j*3)).getProd_img()
						+"/"+ex_lsit.get(i+(j*3)).getProd_info()
						+"/"+ex_lsit.get(i+(j*3)).getProd_num());
				
				//굿즈 이미지 클릭
				goods_view.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
					 
					log_img = (ImageView) e.getSource();
					System.out.println(log_img.getAccessibleText());
					//새창 띄우기
					goods_view_dialog = new Stage(StageStyle.UTILITY);
					
					goods_view_dialog.initModality(Modality.APPLICATION_MODAL);
					//dialog.initOwner();
					goods_view_dialog.setTitle("굿즈 상세조회");
					
					Parent parent = null;
					try {
						parent = FXMLLoader.load(getClass().getResource("goods_mall_detail_view.fxml"));
					} catch(IOException ee) {
						ee.printStackTrace();
					}
					
					Scene scene = new Scene(parent);
					
					goods_view_dialog.setScene(scene);
					goods_view_dialog.setResizable(false);//크기고정
					goods_view_dialog.show();
					
				});
				
				
				Label goods_num = new Label();
				goods_num.setText(ex_lsit.get(i+(j*3)).getProd_num());
				goods_num.setAlignment(Pos.CENTER);
				//goods_num.setTextFill("#c92d2d");
				goods_num.setFont(Font.font("HCR Dotum", 19));
				
				Label goods_name = new Label();
				goods_name.setText(ex_lsit.get(i+(j*3)).getProd_name());
				goods_name.setAlignment(Pos.CENTER);
				goods_name.setFont(Font.font("HCR Dotum", 19));
				
				Label goods_price = new Label();
				goods_price.setText(ex_lsit.get(i+(j*3)).getPrice() + " Won");
				goods_price.setAlignment(Pos.CENTER);
				goods_price.setFont(Font.font("HCR Dotum", 18));
				
				FontAwesomeIcon icon = new FontAwesomeIcon();
				icon.setIconName("CART_PLUS");
				icon.setSize("2em");
				
				FontAwesomeIcon icon2 = new FontAwesomeIcon();
				icon2.setIconName("WON");
				icon2.setSize("2em");
				
				
				JFXButton add_bascket_btn = new JFXButton();
				add_bascket_btn.setGraphic(icon);
				add_bascket_btn.setAccessibleText(ex_lsit.get(i+(j*3)).getProd_num());
				
				//VBox에 차례로 담는다
				V.getChildren().add(goods_view);
				V.getChildren().add(goods_num);
				V.getChildren().add(goods_name);
				V.getChildren().add(goods_price);
				V.getChildren().add(add_bascket_btn);
				
				H.getChildren().add(V);
				
				//장바구니 아이콘 클릭 -> 장바구니 추가
				add_bascket_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
					CartVO cvo = new CartVO();
					log_btn = (JFXButton) e.getSource();
					String prod_info = log_btn.getAccessibleText();
					

					try {
						 //먼저 custom_id 와 prod_num 이 있는지 확인한다 
						 cvo.setCustom_id(Login_controller.log_c.getCustom_id());
						 cvo.setProd_num(prod_info);
						 List<CartVO> search_result = Main.gm_u.getSearchCart(cvo);
						
						 //검색 결과가 있다면 상품 수량 증가(update문)
						 if(search_result.size() != 0) {
							 //검색결과로 수량을 가져옴
							 int cnt = Integer.parseInt(search_result.get(0).getCart_cnt());
							 
							 //아이디와 상품번호가 같은 컬럼의 수량에 +1
							 cvo.setCustom_id(Login_controller.log_c.getCustom_id());
							 cvo.setProd_num(prod_info);
							 cvo.setCart_cnt((cnt+1)+"");
							 
							 //업데이트
							 Main.gm_u.updateCart(cvo);
							 
							 alertTest("'"+ goods_name.getText()+"'" + "  상품 수량 증가");
						 }
						 
						 
						 //검색결과가 없다면 상품 추가
						 if(search_result.size() == 0) {
							//상품결과가 없다면 장바구니 추가(insert문)
							cvo.setProd_num(prod_info);
							cvo.setCustom_id(Login_controller.log_c.getCustom_id());
							cvo.setCart_cnt("1");
							int cnt = Main.gm_u.insertCart(cvo);
							
							alertTest("'"+ goods_name.getText()+"'"  + "  상품 장바구니 추가");
						}
						 
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					
				});
				
				
			}//i for문 종료
			
			mainBox.getChildren().add(H);
		}//for문 종료
		
		if(!Good_View_Pane.getChildren().isEmpty()) {
			Good_View_Pane.getChildren().clear();
		}
		
		Good_View_Pane.getChildren().add(mainBox);
	}
	
	private void alertTest(String message) {
		Alert alertErorr = new Alert(AlertType.INFORMATION);
		alertErorr.setTitle("결제완료");
		alertErorr.setContentText(message);
		alertErorr.showAndWait();
	}
	
}
