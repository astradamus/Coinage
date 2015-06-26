package controller.action;

import actor.Actor;
import actor.attribute.Attribute;
import actor.attribute.Rank;
import controller.ActorController;
import game.Game;
import game.display.Event;
import game.display.EventLog;
import thing.Thing;
import thing.WeaponComponent;
import world.Coordinate;

import java.awt.Color;

/**
 * Actors perform attacks to inflict damage upon other actors.
 */
public class Attacking extends Action {



  private final ActorController intendedVictim;
  private ActorController actualVictim;

  public Attacking(ActorController performer, Coordinate victimWhere) {
    super(performer, victimWhere);
    this.intendedVictim = Game.getActiveControllers().getActorControllerAt(victimWhere);
  }

  @Override
  public Color getIndicatorColor() {
    return Color.RED;
  }



  @Override
  public int calcDelayToPerform() {

    final Actor actor = getPerformer().getActor();
    final Thing equippedWeapon = actor.getEquippedWeapon();

    if (equippedWeapon != null) {
      return equippedWeapon.getWeaponComponent()
          .calcAttackSpeed(actor.readAttributeLevel(Attribute.REFLEX));
    }

    return 3;
  }

  @Override
  public int calcDelayToRecover() {

    final Actor actor = getPerformer().getActor();
    final Thing equippedWeapon = actor.getEquippedWeapon();

    if (equippedWeapon != null) {
      return equippedWeapon.getWeaponComponent()
          .calcRecoverySpeed(actor.readAttributeLevel(Attribute.REFLEX));
    }

    return 2;
  }



  /**
   * Attacking will fail if the intendedVictim is already dead or if the intendedVictim is no longer at the
   * target location.
   */
  @Override
  protected boolean validate() {

    if (intendedVictim != null) {

      final boolean intendedVictimHasMoved =
          !getTarget().getSquare().getAll().contains(intendedVictim.getActor());

      if (!intendedVictimHasMoved) {
        // Our intended victim is right where we want them.
        actualVictim = intendedVictim;
      }

    }


    // If actualVictim is still null, then we either had no intended victim, or they're gone.
    final boolean intendedVictimHasMovedOrWasNotProvided = actualVictim == null;

    if (intendedVictimHasMovedOrWasNotProvided) {
      // Check if there's a new victim in the target square for the attack to hit.
      actualVictim = Game.getActiveControllers().getActorControllerAt(getTarget());
    }



    // If actualVictim is STILL null, then this attack is a miss.
    final boolean attackWillHitSomeone = actualVictim != null;

    if (!attackWillHitSomeone && getPlayerIsPerformer()) {
        if (intendedVictim == null) {
          EventLog.registerEvent(Event.INVALID_ACTION, "Your strike hits naught but air.");
        } else {
          EventLog.registerEvent(Event.INVALID_ACTION,
              intendedVictim.getActor().getName() + " eluded your attack.");
        }
    }

    return attackWillHitSomeone;

  }


  /**
   * Wound the victim, with severity based on the actor's muscle attribute. Damage ranges from
   * {@code muscle*2} to {@code muscle*5}.
   */
  @Override
  protected void apply() {

    final Actor actualVictimActor = actualVictim.getActor();

    final Actor actor = getPerformer().getActor();

    final Rank actorMuscleRank = actor.readAttributeLevel(Attribute.MUSCLE);

    final double damageBase;
    final double damageRange;


    if (actor.getEquippedWeapon() != null) {
      final WeaponComponent weapon = actor.getEquippedWeapon().getWeaponComponent();

      final double minimum = weapon.calcMinimumDamage(actorMuscleRank);
      final double maximum = weapon.calcMaximumDamage(actorMuscleRank);

      damageBase = minimum;
      damageRange = maximum-minimum;

    } else {

      damageBase = actorMuscleRank.ordinal() * 2;
      damageRange = actorMuscleRank.ordinal() * 3;

    }


    final double damage = damageBase + Game.RANDOM.nextInt((int) damageRange);

    String message = "struck "+ actualVictimActor.getName() + " for " + Double.toString(damage) + " damage.";

    if (getPlayerIsPerformer()) {
      message = "You have " + message;
    } else {
      message = actor.getName() + " has "+ message;
    }

    EventLog.registerEventIfPlayerIsNear(actualVictimActor.getCoordinate(), Event.ACTOR_WOUNDED, message);

    actualVictimActor.getHealth().wound(damage);
    actualVictim.onVictimized(getPerformer());

  }







}