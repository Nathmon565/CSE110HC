import java.util.Random;
import java.util.Scanner;

class CSE110HC {
  public static CSE110HC singleton;
  /** Contains an [y][x] array of Rooms representing the current dungeon */
  private Room[][] dungeonFloor;
  /** Represents the player's position */
  private Vector2 position;
  // Favors
  /** Protects the player for one damage */
  private boolean secondWind = false;
  /** Allows unlocking a door without the key */
  private boolean skeletonKey = false;
  /** Starts the floor with the layout revealed */
  private boolean dungeonMap = false;
  private Scanner scanner;

  public static void main(String[] args) {
    singleton = new CSE110HC();
    singleton.Run();
  }

  /** Runs the main program */
  private void Run() {
    scanner = new Scanner(System.in);
    // CreateDungeon(4, 4);
    // GenerateDungeon(1);
    // RevealFloor();
    // PrintKnownMap();
  }

  /**
   * Initializes the dungeon floor
   * 
   * @param width  Number of rooms wide
   * @param height Number of rooms high
   */
  private void CreateDungeon(int width, int height) {
    dungeonFloor = new Room[height][width];
  }

  /**
   * Fills the rooms of the dungeon
   */
  private void GenerateDungeon(int difficulty) {
    Random r = new Random();
    for (int x = 0; x < dungeonFloor[0].length; x++) {
      for (int y = 0; y < dungeonFloor.length; y++) {
        dungeonFloor[y][x] = new Room(new Vector2(x, y), r.nextBoolean());
      }
    }
  }

  /**
   * @param coords Vector2 coordinates of a grid
   * @return Room at coordinates, or null if out of range
   */
  private Room GetRoom(Vector2 coords) {
    return dungeonFloor[coords.y][coords.x];
  }

  /**
   * Prints the known map to the console.
   */
  private void PrintKnownMap() {
    String floor = "";
    for (Room[] row : dungeonFloor) {
      for (Room room : row) {
        if (room == null) {
          floor += "?";
          continue;
        }
        floor += room.OnMap();
      }
      floor += "\n";
    }
    print(floor, 0.01);
  }

  private void RevealFloor() {
    for (Room[] row : dungeonFloor) {
      for (Room room : row) {
        if (room == null) {
          continue;
        }
        room.RevealRoom();
        ;
      }
    }
  }

  /**
   * Creates and starts a random challenge for defeating a monster
   */
  private void StartChallenge() {
  }

  /**
   * Move the player in a certain direction, updating the map and entering the new
   * room.
   * 
   * @param direction 0 - up, 1 - right, 2 - down, 3 - left
   * @return Whether the move was valid or not
   */
  private boolean MoveRooms(int direction) {
    // Attempts to move in a direction
    boolean legalMove = true; // can we move there?
    // actually move
    // update visual rooms
    // enter room
    return legalMove;
  }

  /**
   * Prints a message to the console.
   * 
   * @param msg The message to be printed
   */
  public static void print(String msg) {
    print(msg, 1);
  }

  /**
   * Prints a message to the console.
   * 
   * @param msg     The message to be printed
   * @param newLine Whether it ends the print statement with a new line character
   */
  public static void print(String msg, boolean newLine) {
    print(msg, 1, newLine);
  }

  /**
   * Prints a message to the console.
   * 
   * @param msg      The message to be printed
   * @param speedMod A percent modifier for the character delays.
   */
  public static void print(String msg, double speedMod) {
    print(msg, speedMod, true);
  }

  /**
   * Prints a message to the console.
   * 
   * @param msg      The message to be printed
   * @param speedMod A percent modifier for the character delays.
   * @param newLine  Whether it ends the print statement with a new line character
   */
  public static void print(String msg, double speedMod, boolean newLine) {
    for (char c : msg.toCharArray()) {
      System.out.print(c);
      double delay = 0.03;
      switch (c) {
        case ',', '/', '\\', '(', ')', '{', '}', '[', ']', '-', '+':
          delay += 0.1;
          break;
        case '.', ':', ';', '|', '<', '>':
          delay += 0.2;
          break;
        case '!', '=':
          delay += 0.25;
          break;
        case '?':
          delay += 0.3;
          break;
      }
      delay *= speedMod;
      WaitForMS((int) (delay * 1000));
    }
    if (newLine) {
      System.out.println("");
    }
  }

  /**
   * Asks for the user to input a valid integer. Will keep asking until provided.
   * 
   * @return The valid number inputted by the user.
   */
  private int IntInput() {
    return IntInput(Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  /**
   * Asks for the user to input a valid integer between min and max. Will keep
   * asking until provided.
   * 
   * @param min The minmium value the user is allowed to enter (inclusive)
   * @param max The maximum value the user is allowed to enter (inclusive)
   * @return The valid number inputted by the user,
   */
  private int IntInput(int min, int max) {
    int r;
    while (true) {
      try {
        r = Integer.parseInt(scanner.nextLine());
        if (r >= min && r <= max) {
          return r;
        } else {
          throw new Exception();
        }
      } catch (Exception e) {
        boolean lowerBounds = min > Integer.MIN_VALUE;
        boolean upperBounds = max < Integer.MAX_VALUE;
        if (lowerBounds && upperBounds) {
          print("Enter a valid integer between " + min + " and " + max);
        } else if (lowerBounds) {
          print("Enter a valid integer above " + min);
        } else if (upperBounds) {
          print("Enter a valid integer below " + max);
        } else {
          print("Enter a valid integer");
        }
      }
    }
  }

  /**
   * Waits until the user presses enter.
   */
  public void WaitForEnter() {
    System.out.println("Press Enter to continue");
    try {
      System.in.read();
    } catch (Exception e) {
    }
  }

  /**
   * Causes a delay in the program execution
   * 
   * @param ms The number of miliseconds to be delayed for
   */
  public static void WaitForMS(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }
}

/**
 * A generic entity with a health value, DealDamage(int), and Die()
 */
class Entity {
  private int health;

  /**
   * Creates a generic entiy with a certain amount of health
   * 
   * @param health Amount of health to start with
   */
  public Entity(int health) {
    this.health = health;
  }

  public int GetHealth() {
    return health;
  }

  /**
   * Deals damage to this entity, calling Die() if health < 1
   * 
   * @param damage Amount of damage to deal
   */
  public void DealDamage(int damage) {
    health -= damage;
    if (health < 1) {
      Die();
    }
  }

  /**
   * Called upon this entity reaching 0 health
   */
  protected void Die() {
    return;
  }
}

/**
 * A generic enemy with a strength (difficulty), name, and appropriate
 * functions.
 */
class Enemy extends Entity {
  /** The difficulty rating of the enemy's challenge */
  private int strength;
  private String name;

  /**
   * Creates an enemy with set stats
   * 
   * @param health   The amount of health to start with
   * @param strength The difficulty of the enemy
   */
  public Enemy(int health, int strength) {
    super(health);
    this.strength = strength;
    // Generate a random name and other fun stuff
  }

  public int GetStrength() {
    return strength;
  }

  public void SetStrength(int strength) {
    this.strength = strength;
  }

  /**
   * Prints the stats and other information about the enemy to the console.
   */
  public void PrintEnemy() {
    CSE110HC.print(name + ", " + strength);
  }

  @Override
  protected void Die() {
    Player.AddScore(strength);
  }
}

/**
 * A room of the dungeon, contains all the information related to what's
 * "inside"
 */
class Room {
  private boolean visitable = false;
  /** The enemy present in the room */
  private Enemy enemy;
  /** The coordinates of this room on the floor */
  private Vector2 coordinates;
  /** Whether the room is revealed on the map */
  private boolean discovered = false;
  /** Whether the room contains the key */
  private boolean hasKey = false;
  /** Whether the room has the locked door */
  private boolean isDoor = false;
  /** Whether the room has been visited */
  private boolean visited = false;

  /**
   * Creates an empty room
   * 
   * @param coordinates The coordinates associated to the room's position in the
   *                    dungeon
   */
  public Room(Vector2 coordinates) {
    this(coordinates, false, false, false);
  }

  /**
   * Creates a room with a key
   * 
   * @param coordinates The coordinates associated to the room's position in the
   *                    dungeon
   * @param visitable   Whether the room is accessable at all
   */
  public Room(Vector2 coordinates, boolean visitable) {
    this(coordinates, visitable, false, false);
  }

  /**
   * Creates a room with a key
   * 
   * @param coordinates The coordinates associated to the room's position in the
   *                    dungeon
   * @param visitable   Whether the room is accessable at all
   * @param hasKey      Whether the room has a key or not
   */
  public Room(Vector2 coordinates, boolean visitable, boolean hasKey) {
    this(coordinates, visitable, hasKey, false);
  }

  /**
   * Creates a room with a key
   * 
   * @param coordinates The coordinates associated to the room's position in the
   *                    dungeon
   * @param visitable   Whether the room is accessable at all
   * @param hasKey      Whether the room has a key or not
   * @param isDoor      Whether the room is a locked door
   */
  public Room(Vector2 coordinates, boolean visitable, boolean hasKey, boolean isDoor) {
    this.visitable = visitable;
    this.coordinates = coordinates;
    this.hasKey = hasKey;
    this.isDoor = isDoor;
  }

  public void SetEnemy(Enemy enemy) {
    this.enemy = enemy;
  }

  public Enemy GetEnemy() {
    return enemy;
  }

  public boolean HasKey() {
    return hasKey;
  }

  public boolean IsDoor() {
    return isDoor;
  }

  public boolean IsVisited() {
    return visited;
  }

  public boolean IsDiscovered() {
    return discovered;
  }

  public boolean IsVisitable() {
    return visitable;
  }

  public Vector2 GetCoordinates() {
    return coordinates;
  }

  /**
   * @return The character representation of a room based on its status
   */
  public String OnMap() {
    if (!visitable) {
      return " ";
    }
    if (discovered) {
      if (hasKey) {
        return "%";
      } else if (isDoor) {
        return "#";
      } else if (visited) {
        return "0";
      } else {
        return "O";
      }
    } else {
      return "?";
    }
  }

  /**
   * Sets the discovered state of the room to true.
   */
  public void RevealRoom() {
    discovered = true;
  }

  public void VisitRoom() {
    visited = true;
  }
}

class Vector2 {
  public int x;
  public int y;

  public Vector2(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

/**
 * A more complex entity that is a Singleton which represents the players
 */
class Player extends Entity {
  /** A singleton reference to the active player */
  private static Player player;

  public static Player GetPlayer() {
    return player;
  }

  /** Keeps track of how much progress the player has made */
  private int score = 0;
  /**
   * Currency which can be exchanged for temporary upgrades that help the player
   * on the next floor
   */
  private int favors = 0;

  /**
   * Creates and assigns the singleton player
   * 
   * @param health The amount of health to start with
   */
  public Player(int health) {
    super(health);
    player = this;
  }

  public static int GetScore() {
    return player.score;
  }

  public static int GetFavors() {
    return player.favors;
  }

  public static void AddScore(int score) {
    player.score += score;
  }

  public static void AddFavor() {
    AddFavors(1);
  }

  public static void AddFavors(int favors) {
    player.favors += favors;
  }

  @Override
  /**
   * Ends the game and prints out the stats
   */
  protected void Die() {

  }
}
