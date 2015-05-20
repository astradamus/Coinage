package controller;

/**
 *
 */
public enum Action {
  MOVING    (1);

  final int beatsToPerform;

  Action(int beatsToPerform) {
    this.beatsToPerform = beatsToPerform;
  }
}
