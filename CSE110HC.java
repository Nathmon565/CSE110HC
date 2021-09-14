class CSE110HC {
  /** Contains an [x][y] array of Rooms representing the current dungeon */
  private Room[][] dungeonFloor;
  /** Represents the player's position */
  private Vector2 position;
  // Favors
  /** Protects the player for one damage */
  private bool secondWind = false;
  /** Allows unlocking a door without the key */
  private bool skeletonKey = false;
  /** Starts the floor with the layout revealed */
  private bool dungeonMap = false;

  public static void main(String[] args) {
    // This is where the bulk of the game code is
  }

  /**
   * Initializes the dungeon floor
   * 
   * @param width  Number of rooms wide
   * @param height Number of rooms high
   */
  private void CreateDungeon(int width, int height) {

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
      for (Room room : dungeonFloor) {
        floor += room.OnMap();
      }
      floor += "\n";
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
  private bool MoveRooms(int direction) {
    // Attempts to move in a direction
    bool legalMove = true; // can we move there?
    // actually move
    // update visual rooms
    // enter room
    return legalMove;
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
  private void Die() {
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
  private string name;

  /**
   * Creates an enemy with set stats
   * 
   * @param health   The amount of health to start with
   * @param strength The difficulty of the enemy
   */
  public Enemy(int health, int strength) {
    super(health, strength);
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
    // Prints the stats of the enemy
  }

  private override Die() {
    Player.AddScore(strength);
  }
}

/**
 * A room of the dungeon, contains all the information related to what's
 * "inside"
 */
class Room {
  /** The enemy present in the room */
  private Enemy enemy;
  /** The coordinates of this room on the floor */
  private Vector2 coordinates;
  /** Whether the room is revealed on the map */
  private bool discovered = false;
  /** Whether the room contains the key */
  private bool hasKey = false;
  /** Whether the room has the locked door */
  private bool isDoor = false;
  /** Whether the room has been visited */
  private bool visited = false;

  /**
   * Creates an empty room
   * 
   * @param coordinates The coordinates associated to the room's position in the
   *                    dungeon
   */
  public Room(Vector2 coordinates) {
    Room(coordinates, false, false);
  }

  /**
   * Creates a room with a key
   * 
   * @param coordinates The coordinates associated to the room's position in the
   *                    dungeon
   * @param hasKey      Whether the room has a key or not
   */
  public Room(Vector2 coordinates, bool hasKey) {
    Room(coordinates, hasKey, false);
  }

  /**
   * Creates a room with a key
   * 
   * @param coordinates The coordinates associated to the room's position in the
   *                    dungeon
   * @param hasKey      Whether the room has a key or not
   * @param isDoor      Whether the room is a locked door
   */
  public Room(Vector2 coordinates, bool hasKey, bool isDoor) {
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

  public bool HasKey() {
    return hasKey;
  }

  public bool isDoor() {
    return isDoor;
  }

  public bool isVisited() {
    return visited;
  }

  public bool IsDiscovered() {
    return discovered;
  }

  /**
   * @return The character representation of a room based on its status
   */
  public String OnMap() {
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
  /** Currency which can be exchanged for temporary upgrades that help the player on the next floor */
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
  private void Die() {

  }
}
