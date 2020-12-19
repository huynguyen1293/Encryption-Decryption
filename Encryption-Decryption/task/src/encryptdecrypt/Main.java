package encryptdecrypt;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

interface Algorithm {
    String solve(int key, String input, String mode);
}

class UnicodeAlg implements Algorithm {
    @Override
    public String solve(int key, String input, String mode) {
        StringBuilder result = new StringBuilder();

        if ("dec".equals(mode)) {
            for (int i = 0; i < input.length(); i++) {
                int temp = input.charAt(i) - key;
                result.append((char) temp);
            }
        } else {
            for (int i = 0; i < input.length(); i++) {
                int temp = input.charAt(i) + key;
                result.append((char) temp);
            }
        }

        return result.toString();
    }
}

class ShiftAlg implements Algorithm {
    @Override
    public String solve(int key, String input, String mode) {
        StringBuilder result = new StringBuilder();

        if ("dec".equals(mode)) {
            for (int i = 0; i < input.length(); i++) {
                int temp = input.charAt(i);

                if (input.charAt(i) >= 65 && input.charAt(i) <= 90) {
                    temp = (26 + (input.charAt(i) - 65 - key)) % 26 + 65;
                } else if (input.charAt(i) >= 97 && input.charAt(i) <= 122){
                    temp = (26 + (input.charAt(i) - 97 - key)) % 26 + 97;
                }

                result.append((char) temp);
            }
        } else {
            for (int i = 0; i < input.length(); i++) {
                int temp = input.charAt(i);

                if (input.charAt(i) >= 65 && input.charAt(i) <= 90) {
                    temp = (input.charAt(i) - 65 + key) % 26 + 65;
                } else if (input.charAt(i) >= 97 && input.charAt(i) <= 122){
                    temp = (input.charAt(i) - 97 + key) % 26 + 97;
                }

                result.append((char) temp);
            }
        }

        return result.toString();
    }
}

class AlgorithmSolver {
    private Algorithm algorithm;

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String solve(int key, String input, String mode){
        return this.algorithm.solve(key, input, mode);
    }
}

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        int key = 0;
        String data = "";
        String inFile = "";
        String outFile = "";
        String alg = "shift";
        boolean isData = false;

        for (int i = 0; i < args.length-1; i+= 2) {
            if ("-mode".equals(args[i])) {
                if ("".equals(args[i + 1])) {
                    System.out.println("Error, no mode entered!");
                    return;
                }
                mode = args[i + 1];
                continue;
            }

            if ("-key".equals(args[i])) {
                if ("".equals(args[i + 1])) {
                    System.out.println("Error, no key entered!");
                    return;
                }
                key = Integer.parseInt(args[i + 1]);
                continue;
            }

            if ("-in".equals(args[i])) {
                if ("".equals(args[i + 1])) {
                    System.out.println("Error, no input file entered!");
                    return;
                }
                inFile = args[i + 1];
                continue;
            }

            if ("-out".equals(args[i])) {
                if ("".equals(args[i + 1])) {
                    System.out.println("Error, no output file entered!");
                    return;
                }
                outFile = args[i + 1];
                continue;
            }

            if ("-alg".equals(args[i])) {
                if ("".equals(args[i + 1])) {
                    System.out.println("Error, no algorithm entered!");
                    return;
                }
                alg = args[i + 1];
                continue;
            }

            if ("-data".equals(args[i])) {
                data = args[i + 1];
                isData = true;
            }
        }

        if (!isData && !"".equals(inFile)) {
            codec(key, inFile, outFile, mode, alg);
        } else {
            codec(data, key, outFile, mode, alg);
        }
    }

    public static void codec(String message, int key, String outFile, String mode, String alg) {
        //Solving
        AlgorithmSolver solver = new AlgorithmSolver();
        if ("shift".equals(alg)) {
            solver.setAlgorithm(new ShiftAlg());
        } else {
            solver.setAlgorithm(new UnicodeAlg());
        }
        String result = solver.solve(key, message, mode);

        //Writing result
        if ("".equals(outFile)) {
            System.out.println(result);
        } else {
            File file = new File(outFile);
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.println(result);
            } catch (IOException e) {
                System.out.println("The output file does not exist.");
            }
        }
    }

    public static void codec(int key, String inFile, String outFile, String mode, String alg) {
        File file = new File(inFile);
        String input = "";

        //Reading input
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNext()) {
                input = scanner.nextLine();
            } else {
                System.out.println("The input file is empty.");
                return;
            }
        } catch (IOException e) {
            System.out.println("The input file does not exist.");
        }

        //Solving
        AlgorithmSolver solver = new AlgorithmSolver();
        if ("shift".equals(alg)) {
            solver.setAlgorithm(new ShiftAlg());
        } else {
            solver.setAlgorithm(new UnicodeAlg());
        }
        String result = solver.solve(key, input, mode);

;       //Writing result
        if ("".equals(outFile)) {
            System.out.println(result);
        } else {
            file = new File(outFile);
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.println(result);
            } catch (IOException e) {
                System.out.println("The output file does not exist.");
            }
        }
    }
}