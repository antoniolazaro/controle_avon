package br.com.vortice.avon.pallete.view.menu.menuitem;

import org.springframework.util.StringUtils;

import br.com.vortice.avon.pallete.business.ProdutoBusiness;
import br.com.vortice.avon.pallete.model.PalleteModel;
import br.com.vortice.avon.pallete.model.ProdutoModel;
import br.com.vortice.avon.pallete.view.ShowAlertUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AssociarProdutoMenuItem {
	
	private ProdutoBusiness produtoBussiness;
	public AssociarProdutoMenuItem() {
		produtoBussiness = new ProdutoBusiness();
	}
	
	private HBox createAdicionarPalleteSection(){
		final HBox hb = new HBox();
        
		final TextField codigoBarras = new TextField();
        codigoBarras.setPromptText("Código de Barras");
        codigoBarras.setMaxWidth(200);
        
        final TextField fscCode = new TextField();
        fscCode.setPromptText("FSC Code");
        fscCode.setMaxWidth(200);
        
        final Button addButton = new Button("Dar entrada do produto");
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	try{
            		ProdutoModel produtoModel = new ProdutoModel();
            		PalleteModel palleteModel = null;
            		if(!StringUtils.isEmpty(codigoBarras.getText())){
            			produtoModel.setCodigoBarras(codigoBarras.getText());
            			palleteModel = produtoBussiness.associarProdutoPeloCodigoBarras(produtoModel);
            		}else if(!StringUtils.isEmpty(fscCode.getText())){
            			produtoModel.setFscCode(fscCode.getText());
            			palleteModel = produtoBussiness.associarProdutoPeloFscCode(produtoModel);
            		}
	            	codigoBarras.clear();
	                fscCode.clear();
	                ShowAlertUtil.exibirMensagemInfo("Ação realizada com sucesso. Produto associado a posição: "+palleteModel.getIdentificacao());
            	}catch(Exception ex){
            		ShowAlertUtil.exibirMensagemErro("Erro: "+ex.getMessage());
                }
            }
        });
        hb.getChildren().addAll(codigoBarras, fscCode,addButton);
        hb.setSpacing(2);
        return hb;
	}
	
	
	public void buildMenuItem(Stage primaryStage){
		Scene scene = new Scene(new Group());
	    final Label label = new Label("Associar produtos a posição");
        label.setFont(new Font("Arial", 20));

        HBox adicionarPalleteSection = createAdicionarPalleteSection();
        
        final VBox vbox = new VBox();
        vbox.setSpacing(4);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label,adicionarPalleteSection);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();

	}
}
