package Lab_2;

import java.awt.event.*;
/** The Control class that handles the communication  between the model and the view.
 * @author Arscene Rubayita
 */
public class Control extends View implements ActionListener {
    private Model m;

    /** static method to load the Control  as object in memory.
     *  @param args    String[] with the console arguments.
     */
    public static void main(String[]args){
        new Control();
    }

    /** Constructor with no parameters
     */
    public Control(){
        m = new Model();
        encodeBttn.addActionListener(this);
        decodeBinaryBttn.addActionListener(this);
        decodeHexBttn.addActionListener(this);
    }
    /**
     *  {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == encodeBttn){
            String command = getInstructions();
            try {
                String binaryRepresentation = m.encodeBinary(command);
                setBinaryText(binaryRepresentation);
                String hexRepresentation = m.getHexRepresentation() ;
                setHexText(hexRepresentation);
            }
            catch(ArrayIndexOutOfBoundsException | NullPointerException f){
                System.out.println(f);
                showError("Invalid Registar Location Input");
            }
            catch(IllegalArgumentException f){
                showError("Incorrect format");
            }
            catch(Exception f) {
                System.out.println(f);
                showError("Invalid Input");
            }
        }
        try {
            if (e.getSource() == decodeBinaryBttn) {
                System.out.println("Decoding binary: " + getBinaryText());
                String[] decodedInstructions = m.decodeBinary(getBinaryText());
                setInstruction(decodedInstructions[0], decodedInstructions[1], decodedInstructions[2], decodedInstructions[3]);

                String command = getInstructions();
                m.encodeBinary(command);
                setHexText( m.getHexRepresentation());
                }
        }
        catch(Exception f){
            System.out.println(f);
            showError("Please enter a valid 32 bit number");
        }

        if(e.getSource() == decodeHexBttn){
            try {
                System.out.println("Decoding hex: " + getHexText());
                String toBinary = m.hexToBinary(getHexText());
                System.out.println(getHexText() + " toBinary is " +toBinary );
                String[] decodedInstructions = m.decodeBinary(toBinary);
                setInstruction(decodedInstructions[0], decodedInstructions[1], decodedInstructions[2], decodedInstructions[3]);

                setBinaryText(toBinary);
            }
            catch(NumberFormatException f){
                System.out.println(f);
                showError("Please enter a valid hexadecimal number");
            }
            catch(IllegalArgumentException f){
                System.out.println(f);
                showError("Invalid hexadecimal input");
            }
            catch(Exception f) {
                showError("Invalid hexadecimal input");
            }
        }
    }



}
