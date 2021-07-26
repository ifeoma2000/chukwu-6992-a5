package ucf.assignments;


public class InventoryItem {
    public String name;
    public String serialNumber;
    public String value;

    InventoryItem(String name, String value)
    {
        //constructor ListItem(parameters) assigns the parameters to the classwide variables description, date, complete
        this.name = name;
        this.value = value;
        this.serialNumber = generateSerialNumber(name);
    }

    InventoryItem(String name, String serial, String value)
    {
        //constructor ListItem(parameters) assigns the parameters to the classwide variables description, date, complete
        this.name = name;
        this.value = value;
        this.serialNumber = serial;
    }

    public static String generateSerialNumber(String name){
        int charToAdd = 5;
        String tempNum = "";
        for (int i =0; i < name.length(); i++){
            tempNum += Character.getNumericValue(name.charAt(i));
            if (tempNum.length() > 7)
                break;
        }
        long num = Integer.parseInt(tempNum);
        while (num < 1000000000){
            num *= num;
        }
        String strNum = String.valueOf(num);
        while (charToAdd > 0) {
            int ran = (int) (Math.random() * strNum.length());
            int ranChar = (int) (Math.random() * 26) + 65;
            String temp = strNum.substring(0, ran);
            temp += String.valueOf((char)ranChar);
            temp += strNum.substring(ran + 1);
            strNum = temp;
            charToAdd--;
        }
        return strNum.substring(0, 10);
    }

    @Override
    //method that formats the list items in the application
    public String toString(){
        int space = 85;

        String finalString = "";
        finalString += value;
        for (int i = 0; i < space - value.length(); i++)
            finalString += " ";
        finalString += serialNumber;
        for (int i = 0; i < space - serialNumber.length(); i++)
            finalString += " ";
        finalString += name;
        for (int i = 0; i < space - name.length(); i++)
            finalString += " ";

        return  finalString;
    }
}
