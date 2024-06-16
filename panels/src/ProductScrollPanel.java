import javax.swing.*;
import java.util.ArrayList;

public class ProductScrollPanel extends JPanel {

    public ProductScrollPanel(){
        ArrayList<Product> products = ProductHandler.fetchProducts(Initialize.connection,Initialize.session);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));

        for(Product product:products) {
            listPanel.add(product.createPanel());
        }
        JScrollPane scroll = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroll);
    }

}
