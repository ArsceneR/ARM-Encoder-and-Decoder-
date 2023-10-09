package Lab_2;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

/** The View class that contains all GUI elements
 * @author Arscene Rubayita
 * All the objects are defined as protected so they are accesible only to classes in the same package
 */
public class View extends JFrame {
    private JPanel mainPanel = new JPanel();
    private JPanel images = new JPanel(new GridLayout(1,1));
    private JPanel secondRow = new JPanel(new GridLayout(0,2));
    private JPanel textPanel = new JPanel(new BorderLayout(0,0));
    private JPanel thirdRow = new JPanel(new GridLayout(0,2, 5,5));
    private JTextField instruction = new JTextField();
    private JTextField textFieldOne = new JTextField();
    private JTextField textFieldTwo = new JTextField();

    protected JButton encodeBttn = new JButton("Encode");
    private JLabel one = new JLabel("To Assembly Language", SwingConstants.CENTER);
    private JLabel two = new JLabel("");

    private JLabel three = new JLabel("Binary Instructions", SwingConstants.CENTER);
    private JLabel four = new JLabel("Hex Instructions", SwingConstants.CENTER);

    protected JButton decodeBinaryBttn = new JButton("Decode Binary");
    protected JButton decodeHexBttn = new JButton("Decode Hex");


    /** Constructor with no parameters
     */

    public View(){
        mainPanel.setLayout(new GridLayout(0,1));
        mainPanel.setBackground(new java.awt.Color(240,70,110));

        images.setBackground(new java.awt.Color(240,70,110));
        addImages();
        secondRow.setBackground(new java.awt.Color(240,70,110));
        secondRow.add(instruction);
        secondRow.add(encodeBttn);
        secondRow.add(two);
        textPanel.setBackground(new java.awt.Color(240,70,110));
        textPanel.add(one, BorderLayout.CENTER);
        thirdRow.setBackground(new java.awt.Color(240,70,110));
        thirdRow.add(textFieldOne);
        thirdRow.add(textFieldTwo);
        thirdRow.add(three);
        thirdRow.add(four);
        thirdRow.add(decodeBinaryBttn);
        thirdRow.add(decodeHexBttn);


        mainPanel.add(images);
        mainPanel.add(secondRow);
        mainPanel.add(textPanel);
        mainPanel.add(thirdRow);


        add(mainPanel);
        setPreferredSize(new Dimension(550,400));


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();


    }
    private void addImages(){
        try {
            BufferedImage imageOne = ImageIO.read(getClass().getResource("chip1.jpg"));

            JLabel picLabel1 = new JLabel(new ImageIcon(imageOne));

            images.add(picLabel1);

        } catch (IOException e) {
            System.out.println("File not found");
        }

    }
    /** Displays binary representation in textField
     * @param binaryRepresentation the binary representation of encoded ARM instructions.
     */
    public void setBinaryText(String binaryRepresentation){

        textFieldOne.setText(binaryRepresentation);
    }
    /** Displays hexadecimal representation in textField
     * @param hexRepresentaion the hexadecimal representation of encoded ARM instructions.
     */
    public void setHexText(String hexRepresentaion){

        textFieldTwo.setText(hexRepresentaion);
    }
    /** Displays binary representation in textField based on registar arguments
     * @param operation the opcode of the encoded ARM instructions.
     * @param registarOne the location of registar one of the decoded ARM instructions.
     * @param destinationRegistar the location of the destination registar for the decoded ARM instructinos
     * @param registarTwo the location of registar two of the decoded ARM instructions.
     */
    public void setInstruction(String operation, String registarOne, String destinationRegistar, String registarTwo){
        instruction.setText(operation + " R"+destinationRegistar + ", R"+registarOne+ ", R"+registarTwo);
    }

    /** Provides binary representation of ARM instructions
     *
     * @return String representation of binary ARM instructions.
     */
    public String getBinaryText(){

        return textFieldOne.getText();
    }
    /** Provides hexadecimal representation of ARM instructions
     *
     * @return String representation of hexadecimal ARM instructions.
     */

    public String getHexText(){

        return textFieldTwo.getText();
    }
    /** Provides ARM instructions
     *
     * @return String representation of ARM instructions.
     */

    public String getInstructions(){
        return instruction.getText();
    }

    /**Displays unique error messages
     * @param s error message to display
     */
    public void showError(String s){
        JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
