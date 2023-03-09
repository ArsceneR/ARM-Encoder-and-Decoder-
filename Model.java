package Lab_2;
import java.math.*;
import java.util.*;
/** The Model class for encoding ARM instructions and decoding binary and hexadecimal representations
 * @author Arscene Rubayita
 * @version fall 2022, cosc 20403
 * some ideas were taken and modified from
 * https://stackoverflow.com/questions/9246326/convert-hexadecimal-string-hex-to-a-binary-string
 */
public class Model {
    private int[] representation;
    private HashMap<String, String> map;
    /** Constructor with no parameters
     */
    public Model(){
        setBitRepresentation();
        setOpCodes();

    }
    /** Sets binary representation based on ARM instructions
     */
    public void setBitRepresentation(){

        representation = new int[]{1,1,1,0, //Condition (fixed)
                0,0,0, //format
                0,0,0,0, //operation code
                0, //s
                0,0,0,0, //registar one
                0,0,0,0, //destination registar
                0,0,0,0, 0,0,0,0, //shift
                0,0,0,0 //registar two3
        };
    }
    /** Sets opcode associations between string instuctions and binary representation of operations
     */
    public void setOpCodes(){
        map = new HashMap<String, String>();
        map.put("AND", "0000");
        map.put("EOR", "0001");
        map.put("SUB", "0010");
        map.put("RSB", "0011");
        map.put("ADD", "0100");
        map.put("ADC", "0101");
        map.put("SBC", "0110");
        map.put("RSC", "0111");
        map.put("TST", "1000");
        map.put("TEQ", "1001");
        map.put("CMP", "1010");
        map.put("CMN", "1011");
        map.put("ORR", "1100");
        map.put("MOV", "1101");
        map.put("BIC", "1110");
        map.put("MVN", "1111");


    }
    /** Changes OP Code based on ARM instructions
     * @param s opCode
     */
    public void changeOpCode(String s){
        char[] temp = map.get(s.toUpperCase()).toCharArray();
        System.out.println(Arrays.toString(temp) + ": OP CODE");

        for(int i=7, k=0; i<11; i++){
            representation[i] = temp[k++]-'0';
        }

    }
    private String changeRegistrars(String[] registrars){
        changeDestinationRegistrar(registrars[1]);
        changeRegistarOneLocation(registrars[2]);
        changeRegistarTwoLocation(registrars[3]);

        StringBuilder result = new StringBuilder();
        for(int x : representation)
            result.append(x);

        return result.toString();

    }
    private void changeDestinationRegistrar(String destination){
        int x = Integer.parseInt(destination);
        System.out.println(x+": Destination registar");
        int[] bits = decimalToBinary(x);
        for(int i=16, k=0; i<20; i++){
            representation[i] = bits[k++];
        }
    }
    private void changeRegistarOneLocation(String registarOne){
        int x = Integer.parseInt(registarOne);
        System.out.println(x+": Registar One");
        int[] bits = decimalToBinary(x);
        for(int i=12, k=0; i<16; i++){
            representation[i] = bits[k++];
        }


    }
    private void changeRegistarTwoLocation(String registarTwo){
        int x = Integer.parseInt(registarTwo);
        System.out.println(x+": Registar Two");
        int[] bits = decimalToBinary(x);
        for(int i=28, k=0; i<32; i++){
            representation[i] = bits[k++];
        }
        System.out.println(Arrays.toString(representation));


    }
    /** Sets registar one, two and destination registar in converted binary representation of ARM representation
     * @param s ARM instructions
     *  @return String representation in binary of converted ARM instructions
     */
    public String encodeBinary(String s){
        if(!s.matches("^[a-zA-Z]{3}\\s[rR]\\d{1,2},[\\s]*[rR]\\d{1,2},[\\s]*[rR]\\d{1,2}$"))
            throw new IllegalArgumentException("Incorrect format");

        StringTokenizer instruction = new StringTokenizer(s);
        for (int i = 0; i < 1 && instruction.hasMoreTokens(); i++) {
            changeOpCode(instruction.nextToken());
        }

        String[] registrars = s.split("([^0-9]+)");
        String binaryRepresentation = changeRegistrars(registrars);

        return binaryRepresentation;
    }
    /** Converts binary into ARM instructions
     * @param bits binary representation to be converted
     * @return String[] containing registar locations of binary representation "bits"
     */
    public String[] decodeBinary(String bits){
        if(isValidBinary(bits)) {

            String[] instructions = new String[4];

            String operation = getOperation(bits.substring(7, 11));
            System.out.println(operation + " is the operation");
            instructions[0] = operation;

            String registarOne = binaryToDecimal(bits.substring(12,16));
            System.out.println(registarOne + " is registarOne");
            instructions[1] = registarOne;


            String destinationRegistar = binaryToDecimal(bits.substring(16,20));
            System.out.println(destinationRegistar + " is the destinationRegistar");
            instructions[2] = destinationRegistar;

            String registarTwo = binaryToDecimal(bits.substring(28,32));
            System.out.println(registarTwo + " is registarTwo");
            instructions[3] = registarTwo;


            return instructions;
        }

        return null;
    }
    /** Converts opcode binary representation to String instruction
     * @param opCode operation code instruction
     * @throws IllegalArgumentException if no such op code exists
     * @return String instruction of binary opcode.
     */
    public String getOperation(String opCode){
        for(Map.Entry<String, String> entry : map.entrySet()){
            if(entry.getValue().equals(opCode)){
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("No such OpCode");
    }
    /** Determines whether binary representation is valid
     * @param bits representing binary representation
     * @throws IllegalArgumentException if binary representation is invalid
     * @return boolean true if binary representation is valid. False otherwise
     *
     */
    public boolean isValidBinary(String bits){
        if(bits.matches("1{3}0{4}[0-1]{4}0[0-1]{8}0{8}[0-1]{4}"))  //regex format for ARM instructions.
            return true;
        throw new IllegalArgumentException("Invalid binary/hex input");
    }
    /** Converts binary representation of ARM instructions to hexadecimal
     *
     * @return String that contains hexadecimal representation
     *
     */

    public String getHexRepresentation() {
        int[] a = representation;
        String result = "";
        String halfByte = "";

        for (int i = 0; i < 32; i++) {
            halfByte += a[i];
            if (i % 4 == 3) {
                result += halfByteToHex(halfByte);
                halfByte = "";
            }
        }
        return result;
    }

    private String halfByteToHex(String s){
        int n = Integer.parseInt(s,2);
        String ans="";
        char hexChar = "0123456789ABCDEF".charAt(n);    // determine the character of hex
        return ans+hexChar;
    }


    private int[] decimalToBinary(int n){
        int[] bits = new int[4];
        int k=3;
        while(n>0){
            bits[k--]+=n%2;
            n/=2;
        }
        return bits;
    }

    private String binaryToDecimal(String halfByte){
        return Integer.parseInt(halfByte, 2) +"";

    }
    /** Converts hexadecimal to binary
     * @param hexString hexadecimal representation of ARM instructions
     * @return String that contains converted binary representation
     *
     */
    public String hexToBinary(String hexString){
        return new BigInteger(hexString, 16).toString(2);
    }


}
