class CSE110HC {
  private Room[][] dungeonFloor;
  private Vector2 position;
  //Favors
  private bool secondWind = false;
  private bool skeletonKey = false;
  private bool dungeonMap = false;

  public static void main(String[] args) {
    //This is where the bulk of the game code is
  }

  private void CreateDungeon(int width, int height) {
    //Initializes the dungeon floor
  }

  private Room GetRoom(Vector2 coords) {
    Return dungeonFloor[coords.y][coords.x];
  }

  private void PrintKnownMap() {
    //Prints the known map to the console
  }

  private void StartChallenge() {
    //Creates and starts a random challenge for defeating a monster
  }

  private bool MoveRooms(int direction) {
    //Attempts to move in a direction
    legalMove = //can we move there?
    //actually move
    return legalMove;
  }
}

class Entity {
  private int health;
  
  public Entity(int health) {
    this.health = health;
  }

  public int GetHealth() {
    return health;
  }

  public void DealDamage(int damage) {
    health -= damage;
    if(health < 1) { Die(); }
  }

  private void Die() {
    return;
  }
}

class Enemy extends Entity {
  private int strength;
  private string name;

  public Enemy(int health, int strength) {
    base(health, strength);
    this.strength = strength;
    //Generate a random name and other fun stuff
  }

  public int GetStrength() [
    return strength;
  }

  public void SetStrength(int strength) {
    this.strength = strength;
  }

  public void PrintEnemy() {
    //Prints the stats of the enemy
  }

  private override void Die() {
    	Player.AddScore(strength);
  }
}

class Room {
  private Enemy enemy;
  private Vector2 coordinates;
  private bool discovered = false;
  private bool hasKey = false;
  private bool isDoor = false;

  public Room(Vector2 coordinates, hasKey = false, isDoor = false) {
    this.coordinates = coordinates;
    this.hasKey = hasKey;
    this.isDoor = isDoor;
  }

  public void SetEnemy(Enemy enemy) {
    this.enemy = enemy;
  }

  public Enemy GetEnemy() { return enemy; }
  public bool HasKey() { return hasKey; }
  public bool isDoor() { return isDoor; }

  public bool IsDiscovered() { return discovered; }

  public void RevealRoom() {
    discovered = true;
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

//Singleton player class
class Player extends Entity {
  private static Player player;
  public static Player GetPlayer() {
    return player;
  ]

  private int score = 0;
  private int favors = 0;

  public Player() {
    player = this;
  }

  public static void AddScore(int score) {
    player.score += score;
  }

  public static void AddFavors(int favors = 1) {
    player.favors += favors;
  }

  private override void Die() {
    //TODO end game and show stats
  }
}
