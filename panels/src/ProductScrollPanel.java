import javax.swing.*;
import java.util.ArrayList;

public class ProductScrollPanel extends JPanel {

    public ProductScrollPanel(){
        // TODO get type of sorting from user
        ArrayList<Product> products = ProductHandler.fetchProducts(Initialize.connection,Initialize.session,ProductHandler.SORT_BY_RATING,ProductHandler.ASCENDING_ORDER);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));

        for(Product product:products) {
            listPanel.add(product.createPanel());
        }
        JScrollPane scroll = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroll);
    }

}
