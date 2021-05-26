package com.itis.multistage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiStage {

  private List<List<Integer>> list;
  private Map<Integer, Integer> hashMap;
  private Map<String, Integer> map;
  private Map<Integer, String> mapString;
  private int support;

  public MultiStage(List<List<String>> list) {
    hashMap = new HashMap<>();
    map = new HashMap<>();
    mapString = new HashMap<>();
    this.list = postConstruct(list);
    support = 77;
  }

  public List<List<Integer>> postConstruct(List<List<String>> list) {
    int k = 1;
    List<List<Integer>> answerList = new ArrayList<>();

    for (List<String> a : list) {
      for (String str : a) {
        if (!map.containsKey(str)) {
          map.put(str, k);
          mapString.put(k, str);
          k++;
        }
        if (!hashMap.containsKey(map.get(str))) {
          hashMap.put(map.get(str), 0);
        }
        int count = hashMap.get(map.get(str));
        hashMap.remove(map.get(str));
        hashMap.put(map.get(str), count + 1);
      }
    }

    for (List<String> a : list) {
      List<Integer> logalList = new ArrayList<>();
      for (String str : a) {
        logalList.add(map.get(str));
      }
      answerList.add(logalList);
    }

    return answerList;
  }

  public List<String> analyze() {
    List<Pair> doublePairs = multiStage();

    List<Integer> onePair = new ArrayList<>();

    List<String> answerString = new ArrayList<>();


    for (int i = 1; i <= hashMap.size(); i++) {
      if (hashMap.get(i) >= support) {
        int k = i;
        String str = mapString.get(i);
        answerString.add(str);
      }
    }


    for (Pair pair : doublePairs) {
      String str = mapString.get(pair.x) + " " + mapString.get(pair.y);
      answerString.add(str);
    }
    return answerString;
  }

  public List<Pair> multiStage() {

    List<Pair> pairList = new ArrayList<>();

    for (List<Integer> list : list) {
      for (int i = 0; i < list.size(); i++) {
        for (int j = i + 1; j < list.size(); j++) {
          pairList.add(new Pair(list.get(i), list.get(j)));
        }
      }
    }

    List<Set<Pair>> list = integerListMap(pairList, hashMap.size(), 1, 1);

    List<Pair> currentParList = getGoodPair(list, pairList);

    list = integerListMap(currentParList, hashMap.size(), 1, 2);

    currentParList = getGoodPair(list, pairList);

    List<Pair> answerList = new ArrayList<>();
    Set<Pair> answerSet = new HashSet<>();

    for (Pair pair : currentParList) {
      int x = hashMap.get(pair.x);
      int y = hashMap.get(pair.y);
      if (x < support || y < support) {

      } else {
        if (!answerSet.stream().anyMatch(k -> k.x == pair.x && k.y == pair.y)) {
          answerSet.add(pair);
          answerList.add(pair);
        }
      }
    }

    return answerList;
  }

  private List<Pair> getGoodPair(List<Set<Pair>> list, List<Pair> pairList) {

    Set<Pair> goodPairs = new HashSet<>();
    List<Pair> currentParList = new ArrayList<>();

    for (Set<Pair> pairs : list) {
      Iterator iterator = pairs.iterator();

      int currentSum = 0;
      while (iterator.hasNext()) {
        Pair pair = (Pair) iterator.next();
        currentSum += pair.count;
      }

      if (currentSum >= support) {
        goodPairs.addAll(pairs);
      }
    }

    for (Pair pair : pairList) {
      if (goodPairs.stream().anyMatch(k -> k.x == pair.x && k.y == pair.y)) {
        currentParList.add(pair);
      }
    }

    return currentParList;
  }

  public List<Set<Pair>> integerListMap(List<Pair> list, int hash, int cofX, int cofY) {

    List<Set<Pair>> answerList = new ArrayList<>();

    for (int i = 0; i < hash; i++) {
      answerList.add(new HashSet<>());
    }

    for (Pair pair : list) {

      int hashPair = (cofX * pair.x + cofY * pair.y) % hash;

      Iterator iterator = answerList.get(hashPair).iterator();
      int k = 0;
      while (iterator.hasNext()) {
        Pair pairNext = (Pair) iterator.next();
        if (pairNext.x == pair.x && pairNext.y == pair.y) {
          k = pairNext.count;
        }
      }
      answerList.get(hashPair).removeIf(key -> key.y == pair.y && key.x == pair.x);
      pair.count = k + 1;
      answerList.get(hashPair).add(pair);

    }

    return answerList;
  }


  class Pair {

    int x;
    int y;
    int count = 0;

    public Pair(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public Pair getPair() {
      return this;
    }
  }

}
