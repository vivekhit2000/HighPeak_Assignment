
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;

public class Dilemma {
    String readFile() throws IOException{
        int ch;
        String data = "";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "sample_input.txt"));
            String line = reader.readLine();
            while (line != null) {
                if(line.trim().length() > 0) {
                    data += line.trim() + ";";

                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    void writeFile(String outputText) throws IOException {
        FileWriter fw=new FileWriter("sample_output.txt");

        for (int i = 0; i < outputText.length(); i++)
            fw.write(outputText.charAt(i));

        fw.close();
    }

    void sorting(int[] item_prices, String[] item_names) {
        int n = item_prices.length;
        int temp = 0;
        String temp2 = "";
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (item_prices[j - 1] > item_prices[j]) {
                    temp = item_prices[j - 1];
                    item_prices[j - 1] = item_prices[j];
                    item_prices[j] = temp;

                    temp2 = item_names[j - 1];
                    item_names[j - 1] = item_names[j];
                    item_names[j] = temp2;
                }
            }
        }
    }

    int operation(int employee_count, String[] item_names, int[] item_prices){

        int total_count = item_prices.length;

        sorting(item_prices, item_names);

        int start = 0;
        int end = employee_count-1;
        int answer_diff = 0;
        int answer_index = -1;
        while(end < total_count){
            int diff = item_prices[end] - item_prices[start];
            if(answer_index == -1 || answer_diff > diff) {
                answer_diff = diff;
                answer_index = start;
            }
            start++;
            end++;
        }
        return answer_index;
    }

    public static void main(String[] args) throws IOException
    {
        Dilemma obj = new Dilemma();
        String inputData = obj.readFile();

        String[] parsedInputData = inputData.split(";");
        int employee_count = 0;
        String item_names_string = "";
        String item_price_string = "";
        for(int i=0; i<parsedInputData.length; i++) {
            if(parsedInputData[i].trim() == ""){
                continue;
            }
            else if (i == 0) {
                employee_count = Integer.parseInt(parsedInputData[0].trim().split(":")[1].trim());
            }
            else if (i == 1) {
                continue;
            }
            else{
                String[] temp = parsedInputData[i].trim().split(":");
                item_names_string += temp[0].trim() + ":";
                item_price_string += temp[1].trim() + ":";
            }
        }

        String[] item_names_array = item_names_string.split(":");
        String[] item_prices_array = item_price_string.split(":");

        int[] item_prices = new int[item_names_array.length];
        String[] item_names = new String[item_names_array.length];


        for(int i=0; i<item_names_array.length; i++){
            if(item_names_array[i].trim() != ""){
                item_prices[i] = Integer.parseInt(item_prices_array[i].trim());
                item_names[i] = item_names_array[i];
            }
        }

        int starting_index = obj.operation(employee_count, item_names, item_prices);
        String outputText = "The goodies selected for distribution are:\n\n";
        for(int i=starting_index; i<starting_index+employee_count; i++){
            outputText += item_names[i]+ ": " + item_prices[i] + "\n";
        }

        outputText += "\nAnd the difference between the chosen goodie with highest price and the lowest price is " + (item_prices[starting_index+employee_count-1] - item_prices[starting_index]);
        obj.writeFile(outputText);
    }
}
