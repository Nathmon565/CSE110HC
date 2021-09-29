import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
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
  /** Whether the player as the key for the boss door */
  private boolean hasKey = false;
  private Scanner scanner;

  private String[] challengeCopy = { "A Jack of all trades is master of none", "A bad cause requires many words", "A bad excuse is better than none", "A bad penny always turns up", "A bad tree does not yield good apples", "A bad workman blames his tools", "A barking dog seldom bites", "A bird in hand is worth two in a bush", "A black plum is as sweet as a white", "A book holds a house of gold", "A broken friendship may be soldered but it will never be sound", "A broken friendship may be soldered but will never be sound", "A burden of one's own choice is not felt", "A burden of one's own choice is not felt", "A burnt child dreads the fire", "A calm sea does not make a skilled sailor", "A cat has nine lives", "A chain is no stronger than its weakest link", "A chain is no stronger than its weakest link", "A change is as good as a rest", "A closed mouth catches no flies", "A constant guest is never welcome", "A danger foreseen is half avoided", "A day of sorrow is longer than a month of joy", "A drop of ink may make a million think", "A dry March, a wet April and a cool May fill barn and cellar and bring much hay.", "A dry March, a wet April and a cool May may fill barn and cellar and bring much hay", "A fault confessed is half redressed", "A flower blooms more than once", "A fly will not get into a closed mouth", "A fool and his money are (soon) easily parted", "A fool and his money are soon easily parted", "A fool at forty is a fool forever", "A friend in need is a friend indeed", "A friend to all is a friend to none", "A friend's eye is a good mirror", "A good beginning makes a good end", "A good conscience is a soft pillow", "A good example is the best sermon", "A good mind possesses a kingdom", "A good name is better than a good face", "A growing youth has a wolf in his belly", "A guilty conscience needs no accuser", "A handful of patience is worth more than a bushel of brains", "A happy heart is better than a full purse", "A heavy purse gives to a light heart", "A hedge between keeps friendship green", "A hungry belly has no ears", "A hungry wolf is fixed to no place", "A journey of a thousand miles begins with a single step", "A journey of a thousand miles starts with a single step", "A lender nor borrower be", "A leopard cannot change its spots", "A lie begets a lie", "A little fire is quickly trodden out", "A little of what you fancy does you good", "A loaded wagon makes no noise", "A loveless life is a living death", "A loveless life is living death", "A man can die but once", "A man is as old as he feels himself to be", "A man is judged by his deeds, not by his words", "A man is known by the company he keeps", "A monkey in silk is a monkey no less", "A new broom sweeps clean (but the old brush knows all the corners...)", "A nod is as good as a wink (to a blind horse / man)", "A nod is as good as a wink (to a blind man)", "A picture paints a thousand words", "A problem shared is a problem halved", "A rising tide lifts all boats", "A rolling stone gathers no moss", "A rotten apple spoils the barrel", "A smooth sea never made a skilled mariner", "A soft answer turneth away wrath", "A stitch in time saves nine", "A stumble may prevent a fall", "A swallow does not make the summer", "A tidy house holds a bored woman", "A watched pot never boils", "A wise head keeps a still tongue", "A wonder lasts but nine days", "A young idler, an old beggar", "Absence is the mother of disillusion", "Absence makes the heart grow fonder", "Action without vision is a nightmare", "Actions speak louder than words", "Admiration is the daughter of ignorance", "Adversity makes strange bedfellows", "Advice is cheap", "Advice is least heeded when most needed", "Advisors run no risks", "After dinner rest a while, after supper walk a while", "Age before beauty", "Agree, for the law is costly", "All cats are grey in the dark", "All covet, all lose", "All days are short to industry and long to idleness", "All good things come to those who wait", "All in good time", "All is fair in love and war", "All roads lead to Rome", "All that glitters is not gold", "All things are difficult before they are easy", "All things grow with time - except grief", "All work and no play makes Jack a dull boy", "All's well that ends well", "An Englishman's home is his castle", "An Englishman's home is his castle", "An ant may well destroy a whole dam", "An apple a day keeps the doctor away", "An empty purse frightens away friends", "An idle brain is the devil's workshop", "An old fox is not easily snared", "An onion a day keeps everyone away", "An ounce of discretion is worth a pound of wit", "An ounce of prevention is worth a pound of cure", "Anger is the one thing made better by delay", "Another day, another dollar", "Any time means no time", "April showers bring May flowers", "As you sow, so shall you reap", "Ask me no questions, I'll tell you no lies", "Bad beginnings lead to bad results", "Bad news travels fast", "Be just before you are generous", "Be swift to hear, slow to speak", "Beauty is in the eye of the beholder", "Beauty is only skin deep", "Beauty is the wisdom of women. Wisdom is the beauty of men", "Better an egg today than a hen tomorrow", "Better be alone than in bad company", "Better be the head of a dog than the tail of a lion", "Better be untaught than ill-taught", "Better brain than brawn. ", "Better flatter a fool than fight him", "Better late than never", "Better lose the saddle than the horse", "Better safe than sorry", "Better the devil you know than the devil you don't know", "Better to drink the milk than to eat the cow", "Beware of Greeks bearing gifts", "Birds of a feather flock together", "Blood is thicker than water", "Blood will out", "Care is no cure", "Charity begins at home", "Children and fools tell the truth", "Children are certain cares but uncertain comforts", "Cleanliness is next to godliness", "Clear moon, frost soon", "Clothes don't make the man", "Constant occupation prevents temptation", "Courtesy is contagious", "Cross the stream where it is shallowest", "Crosses are ladders that lead to heaven", "Dead men tell no lies", "Death is the great leveller", "Diamonds cut diamonds", "Diligence is the mother of good fortune", "Discretion is the better part of valour", "Diseases of the soul are more dangerous than those of the body", "Distance makes the heart grow fonder", "Dogs of the same street bark alike", "Don't bark if you can't bite", "Don't count your chickens before they're hatched", "Don't dig your grave with your own knife and fork", "Don't judge a book by its cover", "Early to bed, and early to rise, makes a man healthy, wealthy and wise", "Easier said than done", "Eating an apple every day can help to keep you healthy", "Elbow grease is the best polish", "Empty vessels make the most noise", "Enough is as good as a feast", "Even a worm will turn", "Every ass likes to hear himself bray", "Every cloud has a silver lining", "Every man for himself", "Every man has his price", "Every man is the architect of his own fortune", "Every man thinks his own geese swans", "Every path has its puddle", "Every rose has its thorn", "Every why has a wherefore", "Everything in the garden is rosy", "Experience is the father of wisdom", "Facts speak louder than words", "Failure teaches success", "Fair exchange is no robbery", "False friends are worse than open enemies", "Familiarity breeds contempt", "Fear lends wings", "Fine words butter no parsnips", "First come, first served", "Fool me once, shame on you; fool me twice, shame on me", "Fools rush in where angels fear to tread", "Forbidden fruit is sweet", "Friendship is like money, easier made than kept", "Friendship is love with understanding", "Gardens are not made by sitting in the shade", "Give someone an inch and they will take a mile (or yard)", "Give someone enough rope and they will hang themselves", "God helps those who help themselves", "Good accounting makes good friends", "Good and quickly seldom meet", "Good management is better than good income", "Grasp all, lose all", "Great minds think alike", "Great oaks grow from small acorns", "Grief divided is made lighter", "Half a loaf is better than none", "Handsome is what handsome does", "Hard words break no bones", "Haste makes waste", "Hatred is as blind as love", "He can who believes he can", "He has enough who is content", "He laughs best who laughs last", "He who digs a pit for others falls into it himself", "He who hesitates is lost", "He who is everywhere is nowhere", "He who knows nothing doubts nothing", "He who pays the piper calls the tune", "He who plays with fire gets burnt", "He who wills the end wills the means", "Health is better than wealth", "Home is where the heart is", "Honesty is the best policy", "Honey catches more flies than vinegar", "However long the night, the dawn will break", "Hunger is a good sauce", "If a camel gets his nose in a tent, his body will follow", "If a person keeps moving from place to place, they gain neither friends nor possessions", "If in February there be no rain, 'tis neither good for hay nor grain", "If two ride a horse, one must ride behind", "If wishes were horses, then beggars would ride", "If you are patient in one moment of anger, you will avoid 100 days of sorrow", "If you chase two rabbits, you will not catch either one", "If you want a friend, be a friend", "Ignorance is bliss", "In for a penny, in for a pound", "In the land of the blind the one-eyed man is king", "In times of prosperity friends are plentiful", "It is always darkest before the dawn", "It never rains but it pours", "It takes all sorts to make a world", "It's no use crying over spilt milk", "Justice delayed is justice denied", "Kill not the goose that lays the golden egg", "Kill one to warn a hundred", "Kindle not a fire you cannot put out", "Kindness begets kindness", "Kindness, like a boomerang, always returns", "Knowledge in youth is wisdom in age", "Knowledge is power", "Laughter is the best medicine", "Learn to walk before you run", "Learning is a treasure that will follow its owner everywhere", "Least said, soonest mended", "Let bygones be bygones", "Let the chips fall where they may", "Liars need good memories", "Liberty is not licence (us: license)", "Like father, like son", "Little strokes fell good oaks", "Look before you leap", "Loose lips sink ships", "Losers weepers, finders keepers", "Love is blind", "Make a silk purse out of a sow's ear", "Man is the head of the family and woman is the neck that turns the head", "Man proposes, God disposes", "Manners make the man", "Many a true word is spoken in jest", "Many hands make light work", "March comes in like a lion and goes out like a lamb", "March winds and April showers bring forth May flowers", "Mark, learn and inwardly digest", "Marry in haste, repent as leisure", "Memory is the treasure of the mind", "Men make houses, women make homes", "Might as well be hanged for a sheep as for a lamb", "Misery loves company", "Money begets money", "Money doesn't grow on trees", "Monkey see, monkey do", "Necessity is the mother of invention", "Necessity knows no law", "Need teaches a plan", "Needs must when the devil drives", "Never put off till tomorrow what can be done today", "Never say die", "Never trouble troubles until troubles trouble you", "No cure no pay", "No joy without annoy", "No losers, no winners", "No man can serve two masters", "No man is a hero to his valet", "No man is an island", "No news is good news", "No pain, no gain", "No rain, no grain", "No smoke without fire", "No wind, no waves", "Nobody is perfect", "Nothing ventured nothing gained", "Oil and water do not mix", "Once bitten, twice shy", "One father is (worth) more than a hundred schoolmasters", "One good turn deserves another", "One man's meat is another man's poison", "One man's trash is another man's treasure", "One of these days is none of these days", "One swallow does not make the summer", "One swallow doesn't make a summer", "One today is worth two tomorrows", "Only real friends will tell you when your face is dirty", "Opportunity seldom knocks twice", "Out of sight, out of mind", "Out of the mouth of babes and sucklings", "Parents are patterns", "Penny wise, pound foolish", "People who live in glass houses should not throw stones", "Pity is akin to love", "Poverty waits at the gates of idleness", "Practice makes perfect", "Prevention is better than cure", "Pride comes before a fall", "Procrastination is the thief of time", "Punctuality is the soul of business", "Put all your eggs in one basket", "Rats desert a sinking ship", "Reason does not come before years", "Respect is greater from a distance", "Revenge is a dish best served cold", "Revenge is sweet", "Rome was not built in a day", "Safe bind, safe find", "Save me from my friends", "Saying is one thing, doing is another", "Short reckonings make long friends", "Sickness in the body brings sadness to the mind", "Silence gives consent", "Snug as a bug in a rug", "Spare the rod and spoil the child", "Speech is silver, silence is golden", "Sticks and stones will break my bones but names will never hurt me", "Still waters run deep", "Stolen fruit is the sweetest", "Stolen pleasures are the sweetest", "Tall oaks grow from little acorns", "Telling one lie will lead you to tell another one", "The apple doesn't fall far from the tree", "The best advice is found on the pillow", "The best helping hand is at the end of your sleeve", "The best things in life are free", "The darkest hour is just before dawn", "The devil looks after his own", "The devil makes work for idle hands", "The die is cast", "The early bird catches the worm", "The end justifies the means", "The first step is the hardest", "The more haste, the less speed", "The more you have, the more you want", "The mouse that has but one hole is quickly taken", "The pen is mightier than the sword", "The proof of the pudding is in the eating", "The road to hell is paved with good intentions", "The tongue wounds more than a lance", "The truth is in the wine", "The used key is always bright", "The way to a man's heart is through his stomach", "The wish is father to the thought", "Two wrongs don't make a right", "Uneasy lies the head that wears a crown", "Union is strength", "United we stand, divided we fall", "Unwillingness easily finds an excuse", "Use it or lose it", "Variety is the spice of life", "Virtue is its own reward", "Vision without action is a daydream", "Walls have ears", "Waste not, want not", "We are what we eat", "What a man says drunk, he thinks sober", "What soberness conceals, drunkenness reveals", "What the eye doesn't see, the heart doesn't grieve over", "When in Rome, do as the Romans do", "When poverty comes in the door, love goes out the window", "When the cat's away, the mice play", "Where there's a will there's a way", "Where there's life there's hope", "Who makes himself a sheep will be eaten by the wolves", "Wisdom is better than strength", "Worry often gives a small thing a big shadow", "You are never too old to learn", "You are what you eat", "You can lead a horse to water but you can't make it drink", "You can't teach an old dog new tricks", "You never know what you can do until you try", "You scratch my back and I'll scratch yours", "Accidents will happen" };
  private String[] challengeScrambles = { "able", "accent", "accept", "active", "adjust", "advice", "affect", "afford", "afraid", "agency", "agony", "agree", "aisle", "alert", "allow", "aloof", "altar", "ample", "anger", "ankle", "annual", "ant", "appear", "apple", "area", "arm", "art", "ash", "ask", "assume", "asylum", "attic", "avenue", "avoid", "awake", "awful", "axis", "bacon", "bail", "bait", "bald", "ballet", "ban", "bang", "banish", "basic", "basin", "basket", "battle", "beach", "bear", "beef", "behave", "belief", "belly", "belong", "berry", "bird", "blank", "bless", "blonde", "blood", "boil", "bold", "boom", "border", "borrow", "bottom", "bounce", "bowl", "boy", "brag", "braid", "bread", "breeze", "bride", "broken", "bronze", "bubble", "budge", "budget", "bullet", "burial", "bury", "bush", "busy", "cable", "cage", "calf", "calm", "can", "candle", "canvas", "carbon", "carpet", "cart", "carve", "cash", "cat", "cater", "cats", "cattle", "cave", "cellar", "chalk", "chaos", "charm", "cheat", "cheese", "cherry", "chew", "child", "chop", "chord", "church", "circle", "civic", "clash", "clay", "climb", "clinic", "closed", "cloud", "coach", "coast", "coerce", "coffin", "collar", "color", "comedy", "core", "cotton", "cough", "couple", "cow", "cower", "craft", "create", "crew", "crisis", "crouch", "crown", "cruel", "cruise", "curl", "daily", "dairy", "dance", "danger", "dash", "dawn", "day", "dead", "deaf", "death", "debt", "decide", "deer", "define", "deport", "deputy", "desire", "detail", "devote", "differ", "dilute", "dinner", "dip", "disk", "dive", "dollar", "donate", "donor", "dose", "dozen", "dragon", "drama", "dress", "driver", "drown", "duck", "due", "dump", "dust", "early", "eat", "echo", "egg", "elapse", "elbow", "elect", "embryo", "employ", "empty", "energy", "enjoy", "ensure", "essay", "ethics", "ethnic", "exceed", "excess", "exile", "expert", "expose", "eye", "face", "factor", "fade", "faint", "faith", "family", "far", "fare", "farmer", "favor", "fax", "feast", "fence", "fever", "fibre", "fight", "filter", "finger", "firm", "first", "fit", "flag", "flavor", "flawed", "float", "flood", "flour", "flu", "folk", "forbid", "forget", "fork", "format", "forum", "foster", "fox", "frank", "friend", "frozen", "fuel", "fund", "fuss", "galaxy", "gap", "gape", "garlic", "gasp", "gear", "gem", "get", "ghost", "gift", "glad", "glare", "gloom", "glove", "glue", "go", "goat", "gold", "gossip", "gown", "graph", "gravel", "graze", "grin", "grip", "groan", "grudge", "guilt", "gun", "habit", "halt", "hammer", "hang", "happy", "harbor", "harm", "harsh", "haul", "have", "hay", "hear", "heaven", "heel", "heir", "helmet", "herb", "hero", "hiccup", "high", "hill", "hobby", "hole", "honest", "honey", "horror", "hotdog", "human", "hunter", "ice", "ideal", "ignore", "immune", "inch", "index", "infect", "injure", "inn", "inner", "inside", "insist", "insure", "irony", "issue", "ivory", "jail", "jam", "jaw", "jelly", "jest", "jewel", "joke", "judge", "jump", "junior", "jury", "kid", "kill", "killer", "kind", "kit", "knead", "knee", "knit", "knot", "label", "lack", "lady", "lamb", "lane", "laser", "last", "latest", "launch", "lawn", "layer", "leader", "leak", "learn", "lease", "left", "lick", "life", "like", "lily", "linear", "linen", "lion", "listen", "live", "liver", "loan", "locate", "lodge", "lonely", "lose", "loud", "lounge", "lover", "loyal", "lunch", "maid", "main", "make", "makeup", "marine", "market", "marsh", "mass", "matrix", "mayor", "meadow", "mean", "means", "medal", "member", "mercy", "metal", "middle", "mild", "milk", "miner", "mirror", "mist", "mix", "mobile", "modest", "module", "mold", "moment", "monkey", "mood", "mosaic", "mother", "moving", "mud", "murder", "museum", "mutter", "myth", "native", "neck", "nerve", "node", "noisy", "north", "notion", "novel", "nuance", "nun", "nut", "obese", "occupy", "ocean", "office", "offset", "old", "opera", "oppose", "orbit", "origin", "other", "oven", "owner", "pace", "page", "palace", "pan", "pardon", "past", "path", "pause", "pawn", "pay", "peanut", "penny", "pepper", "pest", "piano", "pie", "pillow", "pin", "pitch", "plain", "planet", "plead", "pledge", "pluck", "poem", "policy", "pool", "poor", "porter", "powder", "pray", "prayer", "preach", "pride", "print", "prize", "proud", "prove", "push", "queen", "queue", "quit", "rabbit", "racial", "rack", "raid", "rain", "ranch", "rank", "rare", "rate", "reach", "ready", "rear", "rebel", "redeem", "reform", "region", "reign", "relate", "remark", "remind", "rent", "resort", "result", "retain", "reveal", "revise", "rhythm", "ribbon", "ride", "rider", "rifle", "riot", "river", "roar", "robot", "rocket", "room", "rope", "round", "rub", "rumor", "rung", "rush", "sacred", "safari", "safe", "salad", "salon", "sample", "sandal", "say", "scrap", "scrape", "sea", "season", "secret", "seem", "self", "sell", "senior", "sermon", "shake", "shape", "shark", "sharp", "shave", "sheet", "shield", "shine", "shoe", "shout", "shrug", "sick", "siege", "sieve", "silk", "singer", "sip", "sister", "skate", "sketch", "skill", "skirt", "sky", "slap", "slave", "sleeve", "slime", "slip", "slogan", "small", "smoke", "smooth", "snack", "snake", "snarl", "sniff", "soap", "sock", "solo", "solve", "sound", "spell", "spider", "spill", "spit", "spoon", "spot", "squad", "squash", "stab", "stake", "stamp", "statue", "stay", "steady", "steam", "stem", "sticky", "still", "sting", "stitch", "stone", "store", "strain", "strap", "street", "stride", "strip", "stroll", "studio", "stuff", "stun", "stupid", "suburb", "subway", "suffer", "suit", "sum", "summit", "suntan", "sure", "swarm", "sweat", "swell", "swipe", "switch", "symbol", "tablet", "tank", "target", "tasty", "taxi", "team", "tell", "tenant", "tent", "thank", "thanks", "theory", "thigh", "threat", "throat", "thrust", "thumb", "tidy", "tiger", "timber", "tiptoe", "tired", "toast", "toll", "ton", "tool", "toss", "touch", "tower", "toy", "trail", "trait", "tray", "tread", "treaty", "trench", "tribe", "truck", "true", "tumour", "tunnel", "turn", "tycoon", "ulcer", "uneasy", "union", "unique", "unity", "unlike", "urge", "useful", "utter", "vacant", "vain", "varied", "vat", "veil", "velvet", "viable", "victim", "vile", "virgin", "virus", "voyage", "wagon", "waiter", "wake", "wander", "want", "war", "water", "weak", "wealth", "weave", "weed", "whale", "wheel", "whole", "widen", "width", "window", "winner", "wire", "wolf", "wonder", "wool", "worker", "worry", "wound", "wrist", "year", "young", "zone" };

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
    print("Enter a budget: ", false);
    int budget = IntInput(1, 10);
    int r = new Random().nextInt(3);
    switch (r) {
      case 0:
        ChallengeCopy(budget);
        break;
      case 1:
        ChallengeScramble(budget);
        break;
      case 2:
        ChallengeMath(budget);
        break;
    }
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
   * 
   * @param budget The difficulty rating of the challenge.
   * @return Whether the challenge was usccessful or not
   */
  private boolean StartChallenge(int budget) {
    int c = new Random().nextInt(3);
    switch (c) {
      case 1: // Complete a simple math problem
        return ChallengeMath(budget);
      case 2: // Copy a phrase or sentence
        return ChallengeCopy(budget);
      default: // Unscramble a word
        return ChallengeScramble(budget);
    }
  }

  /**
   * Creates a random challenge where the user has to solve a math problem before
   * time runs out.
   * 
   * @param budget Determines the size of the numbers, the sign, and how long to
   *               solve.
   * @return Whether the challenge was successful or not
   */
  private boolean ChallengeMath(int budget) {
    Random random = new Random();
    int firstNum = random.nextInt(budget) + 1;
    budget -= firstNum / 2;
    int secondNum = random.nextInt(budget) + 1;
    budget -= secondNum / 2;
    double answer;
    String op;
    int opNum;
    if (budget >= 8) {
      op = "/";
      opNum = 4;
      answer = firstNum / (double) secondNum;
    } else if (budget > 5) {
      op = "*";
      opNum = 3;
      answer = firstNum * (double) secondNum;
    } else if (budget >= 3) {
      op = "-";
      opNum = 2;
      answer = firstNum - secondNum;
    } else {
      op = "+";
      opNum = 1;
      answer = firstNum + secondNum;
    }
    answer = Round(answer, 2);

    double difficulty = 0;
    if ((opNum == 4 && firstNum == secondNum) || (opNum == 3 && (firstNum == 1 || secondNum == 1))
        || (opNum == 2 && (firstNum == 1 || secondNum == 1 || firstNum == secondNum))
        || (opNum == 1 && (firstNum < 2 || secondNum < 2))) {
      difficulty = 1.5;
    } else {
      difficulty = Math.max(firstNum, secondNum) / (1.5 + random.nextDouble());
    }
    if ((opNum == 3 || opNum == 4) && difficulty != 1.5) {
      difficulty *= 1 + random.nextDouble();
    }

    double timeLimit = Round(difficulty, 2);
    if (timeLimit < 1) {
      timeLimit += 1;
    }

    print("You have " + timeLimit + " seconds to solve the following problem...");
    WaitForEnter();
    print(firstNum + " " + op + " " + secondNum + " = ", 0, false);
    double startTime = System.currentTimeMillis() / 1000.0;
    String guessString = scanner.nextLine();
    double endTime = System.currentTimeMillis() / 1000.0;
    double elapse = Round(endTime - startTime, 2);
    double guess;
    try {
      guess = Integer.parseInt(guessString);
    } catch (Exception e) {
      print("That's not a valid answer!");
      print("The answer was " + answer);
      return false;
    }
    if (guess == answer && elapse < timeLimit) {
      print("That's it! Good job!");
    } else if (elapse >= timeLimit) {
      print("Time out!");
      print(elapse + " >= " + timeLimit);
    } else {
      print("That's not the right answer");
      print("It was " + answer);
    }

    return false;
  }

  /**
   * Creates a random challenge where the user has to copy a phrase or sentence
   * before time runs out.
   * 
   * @param budget Detremines the length of the phrase/sentence, and how long to
   *               solve.
   * @return Whether the challenge was successful or not
   */
  private boolean ChallengeCopy(int budget) {
    // minimum phrase length : 14
    // maximum phrase length : 88
    // average phrase length : 35
    Random random = new Random();
    int min, max, difficulty = random.nextInt(budget + 1);
    budget -= difficulty;
    // TODO balance time limit and phrase length
    double timeLimit = 30;
    timeLimit -= budget * 5;
    while (timeLimit < 5) {
      timeLimit += 5;
      difficulty++;
    }

    switch (difficulty) {
      case 1:
        min = 14;
        max = 20;
        break;
      case 2:
        min = 20;
        max = 30;
        break;
      case 3:
        min = 25;
        max = 40;
        break;
      case 4:
        min = 30;
        max = 60;
        break;
      case 5:
      default:
        min = 50;
        max = 88;
        break;
    }
    String choice = null;
    for (int i = 0; i < 1000; i++) {
      choice = challengeCopy[random.nextInt(challengeCopy.length)];
      if (choice.length() >= min && choice.length() <= max) {
        break;
      }
    }
    if (choice == null) {
      print("ChallengeCopy() hit a safety limit of 1000 iterations and didn't find a fitting string length (between "
          + min + "-" + max + " characters)", 0);
    }

    print("Copy challenge!");
    print("\t(Difficulty: " + difficulty + " | Time Limit: " + timeLimit + " seconds)");
    WaitForEnter();
    print("Copy the following phrase as quickly as you can! No mistakes!");
    print(choice, 0);
    double startTime = System.currentTimeMillis() / 1000.0;
    String answer = scanner.nextLine();
    double endTime = System.currentTimeMillis() / 1000.0;
    double elapse = Round(endTime - startTime, 2);
    print("\n");
    if (answer.trim().equals(choice.trim()) && endTime - startTime < timeLimit) {
      print("You got it! Good job!");
      return true;
    } else if (endTime - startTime >= timeLimit) {
      print("Time out!");
      print(elapse + " >= " + timeLimit);
    } else {
      print("Not quite! You didn't copy it exactly!");
    }
    return false;
  }

  /**
   * Creates a random challenge where the user has to unscramble a word before
   * time runs out.
   * 
   * @param budget Detremines the length of the word, and how long to solve.
   * @return Whether the challenge was successful or not
   */
  private boolean ChallengeScramble(int budget) {
    Random random = new Random();
    int wordLen = 0;
    if (budget > 5) {
      wordLen = 6;
      budget -= 6;
    } else {
      wordLen = budget;
      budget = 0;
    }
    if (wordLen < 4) {
      wordLen = 4;
    }
    double timeLimit = 10 - budget;
    if (timeLimit < 5) {
      timeLimit = 5;
    }

    String choice = null;
    for (int i = 0; i < 1000; i++) {
      choice = challengeScrambles[random.nextInt(challengeScrambles.length)];
      if (choice.length() == wordLen) {
        break;
      }
    }

    if (choice == null) {
      print("ChallengeScramble() hit a safety limit of 1000 iterations and didn't find a fitting string length ("
          + wordLen + " characters)", 0);
    }

    List<String> str = Arrays.asList(choice.split(""));
    Collections.shuffle(str);
    String scrambled = "";
    for (String s : str) {
      scrambled += s;
    }

    print("Scramble Challenge!");
    print("\t(Word Length: " + wordLen + " | Time Limit: " + timeLimit + " seconds)");
    print("Unscramble the word as fast as you can!");
    WaitForEnter();
    print(scrambled, 0);
    double startTime = System.currentTimeMillis() / 1000.0;
    String answer = scanner.nextLine();
    double endTime = System.currentTimeMillis() / 1000.0;
    double elapse = Round(endTime - startTime, 2);

    boolean contains = false;
    for (String s : challengeScrambles) {
      if (s.trim().equals(answer.trim())) {
        contains = true;
        break;
      }
    }
    boolean matching = true;
    if (contains) {
      for (String s : Arrays.asList(answer.split(""))) {
        if (!scrambled.contains(s)) {
          matching = false;
        }
      }
    }
    if (contains && matching && elapse < timeLimit) {
      print("You got it! Good job!");
      return true;
    } else if (contains && matching && elapse >= timeLimit) {
      print("Time out!");
      print(elapse + " >= " + timeLimit);
    } else {
      if (!contains) {
        print("That is not a word in the list!");
      } else if (!matching) {
        print("You used letters that weren't in the original word!");
      }
      print("The word was " + choice);
    }

    return false;
  }

  /**
   * Move the player in a certain direction, updating the map and entering the new
   * room.
   * 
   * @param direction 0 - up, 1 - right, 2 - down, 3 - left
   * @return Whether the move was valid or not
   */
  private boolean MoveRooms(int direction) {
    Vector2 newPos = Vector2.Sum(Player.GetPos(), RelativeOffset(direction));
    if (!InBounds(newPos)) {
      return false;
    }
    Room room = GetRoom(newPos);
    if (!room.IsVisitable()) {
      return false;
    }
    if (room.IsDoor()) {
      if (!hasKey) {
        print("Can't move here! There's a locked door in the way!");
        return false;
      }
    } else if (room.HasKey()) {
      if (!hasKey) {
        hasKey = true;
        print("Key collected!");
      }
    }
    // Reveal adjacent grids around newPos
    for (int i = 0; i < 4; i++) {
      Vector2 pPos = Vector2.Sum(newPos, RelativeOffset(i));
      if (!InBounds(pPos)) {
        continue;
      }
      GetRoom(pPos).RevealRoom();
    }

    room.VisitRoom();

    return true;
  }

  /**
   * Convert an int direction to coordinate
   * 
   * @param dir 0 - up, 1 - right, 2 - down, 3 - left
   * @return The relative coordinate
   */
  private Vector2 RelativeOffset(int dir) {
    return new Vector2(dir == 0 || dir == 2 ? 0 : dir == 1 ? 1 : -1, dir == 1 || dir == 3 ? 0 : dir == 0 ? 1 : -1);
  }

  /**
   * Whether a coordinate is in bounds of the map
   * 
   * @return Whether it is in bounds or not
   */
  private boolean InBounds(Vector2 coord) {
    if (dungeonFloor.length == 0) {
      return false;
    }
    return !(coord.x < 0 || coord.x >= dungeonFloor[0].length || coord.y < 0 || coord.y >= dungeonFloor.length);
  }

  /**
   * Detremines whether two points on the map are continuous (can be traveled
   * between)
   * 
   * @param a The first point
   * @param b The second point
   * @return Whether the two points are continuous
   */
  private boolean IsContinuos(Vector2 a, Vector2 b) {
    if (!InBounds(a) || !InBounds(b)) {
      return false;
    }
    ArrayList<Vector2> whitelist = new ArrayList<Vector2>();
    ArrayList<Vector2> blacklist = new ArrayList<Vector2>();

    whitelist.add(a);

    while (whitelist.size() > 0) {
      Vector2 g = whitelist.get(0);
      if (g.equals(b)) {
        return true;
      }
      for (int i = 0; i < 4; i++) {
        Vector2 d = Vector2.Sum(g, RelativeOffset(i));
        if (!whitelist.contains(d) && !blacklist.contains(d)) {
          whitelist.add(g);
        }
      }
      blacklist.add(g);
      whitelist.remove(0);
    }

    return false;
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
   * Rounds a double to a certain number of decimal places.
   * 
   * @param input    The unrounded double
   * @param decimals The number of decimals to round to
   * @return The rounded number
   */
  private double Round(double input, int decimals) {
    int zeros = (int) Math.pow(10, decimals);
    return Math.round(input * zeros) / (double) zeros;
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
    scanner.nextLine();
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
    if (discovered) {
      if (!visitable) {
        return " ";
      } else if (hasKey) {
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

  public Vector2() {
    this(0, 0);
  }

  public Vector2(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    Vector2 v = (Vector2) o;
    return IsEqual(this, v);
  }

  public static boolean IsEqual(Vector2 a, Vector2 b) {
    return a.x == b.x && a.y == b.y;
  }

  public static Vector2 Sum(Vector2 a, Vector2 b) {
    return new Vector2(a.x + b.x, a.y + b.y);
  }

  public static Vector2 Product(Vector2 a, int b) {
    return new Vector2(a.x * b, a.y * b);
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
  private Vector2 pos;

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

  public static Vector2 GetPos() {
    return player.pos;
  }

  public static void SetPos(Vector2 pos) {
    player.pos = pos;
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
