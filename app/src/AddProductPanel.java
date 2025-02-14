import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AddProductPanel extends JPanel implements ActionListener {

    private final JLabel introLabel;
    private final JLabel titleLabel;
    private final JLabel priceLabel;
    private final JLabel availableForClientLabel;
    private final JLabel stockLabel;
    private final JLabel imageLabel;
    private final JTextArea imagePathLabel;
    private final JTextArea errorsLabel;
    private final JButton addButton;
    private final JButton backButton;
    private final JButton chooseImageButton;
    private final JButton showImageButton;
    private final JButton deleteImageButton;
    private final JTextField titleTextField;
    private final JTextField priceTextField;
    private final JTextField stockTextField;
    private final JCheckBox availableToClientCheckBox;
    private final JFileChooser imageFileChooser;

    // Set default path to Pictures according to the OS
    private final String filePath = System.getProperty("os.name").toLowerCase().contains("windows") ? String.valueOf(Paths.get(System.getProperty("user.home") ,"Pictures").toAbsolutePath()) : "~/Pictures";

    private File file;
    private ImageIcon imageIcon;

    public AddProductPanel() {
        introLabel = new JLabel("Please enter product's details");
        titleLabel = new JLabel("Title");
        priceLabel = new JLabel("Price");
        availableForClientLabel = new JLabel("Available for client");
        stockLabel = new JLabel("Stock");
        imageLabel = new JLabel("Image");
        imagePathLabel = new JTextArea();
        errorsLabel = new JTextArea();
        addButton = new JButton("Add");
        backButton = new JButton("Back");
        chooseImageButton = new JButton("Choose image");
        showImageButton = new JButton("Show image");
        deleteImageButton = new JButton("Delete image");
        titleTextField = new JTextField();
        priceTextField = new JTextField();
        availableToClientCheckBox = new JCheckBox();
        stockTextField = new JTextField();
        imageFileChooser = new JFileChooser(filePath);

        introLabel.setPreferredSize(new Dimension(400, 30));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setOpaque(true);
        introLabel.setBackground(Color.GREEN);
        introLabel.setFont(new Font(introLabel.getFont().getName(), Font.BOLD, introLabel.getFont().getSize() + 1));
        errorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorsLabel.setLineWrap(true);
        errorsLabel.setEditable(false);
        errorsLabel.setBackground(null);
        errorsLabel.setForeground(Color.RED);
        errorsLabel.setVisible(false);
        imagePathLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePathLabel.setLineWrap(true);
        imagePathLabel.setEditable(false);
        imagePathLabel.setBackground(null);
        imagePathLabel.setVisible(false);

        // Allowing only images in file chooser
        imageFileChooser.setAcceptAllFileFilterUsed(false);
        imageFileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));


        addButton.addActionListener(this);
        backButton.addActionListener(this);
        chooseImageButton.addActionListener(this);
        showImageButton.addActionListener(this);
        deleteImageButton.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components

        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.gridwidth = 2; // Span two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(introLabel, gbc);

        gbc.gridwidth = 1; // Reset to one column span
        gbc.anchor = GridBagConstraints.LINE_START; // Right alignment for labels
        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END; // Left alignment for text fields
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(titleTextField, gbc);

        gbc.gridwidth = 1; // Reset to one column span
        gbc.anchor = GridBagConstraints.LINE_START; // Right alignment for labels
        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END; // Left alignment for text fields
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(priceTextField, gbc);

        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(availableForClientLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(availableToClientCheckBox, gbc);

        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(stockLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(stockTextField, gbc);

        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(imageLabel, gbc);

        // This panel contains chooseImageButton and showImageButton and deleteImageButton
        JPanel tempPanel = new JPanel(new GridLayout(1, 3));
        tempPanel.add(chooseImageButton);
        tempPanel.add(showImageButton);
        tempPanel.add(deleteImageButton);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tempPanel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(imagePathLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(errorsLabel, gbc);


        gbc.anchor = GridBagConstraints.CENTER; // set to default
        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(backButton, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(addButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src.equals(addButton)) {
            String title = titleTextField.getText();
            String price = priceTextField.getText();
            String stock = stockTextField.getText();
            String pathToImage = file == null ? null : file.getAbsolutePath();
            boolean availableToClient = availableToClientCheckBox.isSelected();

            ArrayList<String> errors = Validator.validateAddProductForm(title, price, stock, pathToImage);

            if (errors.isEmpty()){
                if (file != null){
                    pathToImage = FileCopier.copy(pathToImage);
                }

                // Add product to the database
                ProductHandler.addProduct(Initialize.connection, title, Float.parseFloat(price), availableToClient, pathToImage, Integer.parseInt(stock));
                Main.changePanel(new AdminProductsPanel());
                errorsLabel.setText("");
            } else {
                errorsLabel.setText("");
                for(String error : errors){
                    errorsLabel.setText(errorsLabel.getText() + "\n* " + error);
                }
            }

            errorsLabel.setVisible(true);
            Main.refreshFrame();

        } else if (src.equals(backButton)) {
            Main.changePanel(new AdminProductsPanel());

        } else if (src.equals(chooseImageButton)) {
            int option = imageFileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                file = imageFileChooser.getSelectedFile();
                this.refreshImagePathLabel();
            }
        } else if (src.equals(showImageButton)) {
            this.showImage();
        } else if (src.equals(deleteImageButton)) {
            file = null;
            this.refreshImagePathLabel();
        }
    }

    private void showImage() {
        JDialog tempDialog = new JDialog();
        tempDialog.setTitle("Image preview");
        JPanel tempPanel = new JPanel();
        JLabel label = new JLabel();

        boolean isReadyToShow = true;

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IllegalArgumentException e) {
            isReadyToShow = false;
            label.setForeground(Color.RED);
            label.setText("Error: No image input");
        } catch (IOException e) {
            isReadyToShow = false;
            label.setForeground(Color.RED);
            label.setText("Error: Something went wrong during reading image");
        } finally {
            if (bufferedImage == null) {
                isReadyToShow = false;
                label.setForeground(Color.RED);
                label.setText("Error: No image input");
            }
        }

        if (isReadyToShow) {
            imageIcon = new ImageIcon(bufferedImage);
            label.setIcon(imageIcon);
        }

        tempPanel.add(label);
        tempDialog.add(tempPanel);
        tempDialog.setModal(true);
        tempDialog.setResizable(false);
        tempDialog.setAlwaysOnTop(true);
        tempDialog.pack();
        tempDialog.setLocationRelativeTo(null);
        tempDialog.revalidate();
        tempDialog.repaint();
        tempDialog.setVisible(true);
    }

    private void refreshImagePathLabel() {
        try {
            String filePath = file.getAbsolutePath();
            imagePathLabel.setText("Image Path: \n" + file.getAbsolutePath());
            imagePathLabel.setVisible(true);
        } catch (Exception e){
            imagePathLabel.setText(null);
            imagePathLabel.setVisible(false);
        }
        Main.refreshFrame();
    }
}
