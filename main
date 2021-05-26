package com.itis.multistage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(
        new FileReader(new File("file.txt")));

    List<List<String>> coutList = new ArrayList<>();

    while (bufferedReader.ready()) {
      String[] str = bufferedReader.readLine().split(" ");

      List<String> lis = Arrays.stream(str).collect(Collectors.toList());

      coutList.add(new ArrayList<>(lis));
    }

    MultiStage multiStage = new MultiStage(coutList);
    List<String> l = multiStage.analyze();
    l.forEach(System.out::println);
  }

  public void generationData() throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(
        new FileWriter(new File("file.txt")));

    BufferedReader bufferedReader = new BufferedReader(new FileReader
        (new File("products")));

    List<String> list = new ArrayList<>();

    while (bufferedReader.ready()) {
      String str = bufferedReader.readLine();
      list.add(str);
    }

    for (int i = 0; i < 1000; i++) {
      int dia = randomValue(7, 1);

      for (int j = 0; j < dia; j++) {
        int id = randomValue(list.size() - 1, 0);
        bufferedWriter.write(list.get(id) + " ");
      }
      bufferedWriter.write("\n");
    }

    bufferedWriter.flush();
  }

  public static int randomValue(int max, int min) {
    return (int) (Math.random() * (max - min) + min);
  }
}
