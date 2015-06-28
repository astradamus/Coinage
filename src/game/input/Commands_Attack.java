package game.input;

import actor.Actor;
import controller.action.Attacking;
import controller.player.PlayerController;
import game.Game;
import game.display.Event;
import game.display.EventLog;
import world.Coordinate;

import java.awt.event.KeyEvent;

/**
 *
 */
public enum Commands_Attack implements Command {


  STRIKE {

    @Override
    public int getHotKeyCode() {
      return KeyEvent.VK_S;
    }

    @Override
    public String getControlText() {
      return "S: Strike an enemy.";
    }

    @Override
    public void execute() {

      PlayerController playerController = Game.getActivePlayer();
      Coordinate playerTarget = Game.getActiveInputSwitch().getPlayerTarget();

      Actor playerActor = playerController.getActor();

      if (playerTarget.equalTo(playerActor.getCoordinate())) {
        EventLog.registerEvent(Event.INVALID_ACTION, "You smack yourself upside the head.");
      } else {

        playerController.attemptAction(new Attacking(playerController,
            Game.getActiveInputSwitch().getPlayerTarget()));

      }

      Game.getActiveInputSwitch().enterMode(GameMode.EXPLORE);

    }

  }


}