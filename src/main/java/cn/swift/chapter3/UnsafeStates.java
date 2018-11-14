package cn.swift.chapter3;

/**
 * 内部可变状态逸出（不要这么做）
 */
public class UnsafeStates {

  private String[] states = new String[] {"AB", "AC", "AD", "AE"};

  public String[] getStates() {
    return states;
  }
}
