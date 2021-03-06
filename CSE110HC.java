import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import kuusisto.tinysound.TinySound;
import kuusisto.tinysound.Music;

class CSE110HC {
	public static CSE110HC singleton;
	/** Contains an [y][x] array of Rooms representing the current dungeon */
	private Room[][] dungeonFloor;

	/** Represents the statuses the game can be in */
	private enum Status {
		menu, tavern, dungeon, combat
	};

	/** Current status of the game */
	private Status status = Status.menu;
	private int dungeonWidth;
	private int dungeonHeight;
	private int dungeonDepth;
	private int seed;
	private Random random;
	private static double difficulty = 1;
	private static double messageSpeedModifier = 1;
	// Favors
	/** Reduces enemies on the next floor */
	private boolean reducedEnemies = false;
	private Scanner scanner;

	private TutorialHandler tutorial;

	// Audio
	private Music dungeonAmbiance;
	private Music tavernAmbiance;
	private Music tavernMusic;
	private Music fightMusic;
	private Music bossMusic;

	private String tutorialDump = "Welcome to the dungeon!\nThe goal of the game is to get as deep in the dungeon as possible, accumulating the highest score you can. You get score by doing actions in the game: defeating monsters, discovering new floors, etc.\nThe start of every dungeon is the floor itself. Here's a key to explain what the symbols mean:\n\t@ = your location\n\t? = an unknown location\n\t! = an enemy location\n\t  = an explored location\n\t% = the key\n\t# = the locked door\n\t- = a solid wall\n\nBegin traversing the dungeon floor, find the key, and get to the door! Good luck!\nYou found a key! There is one key per floor, these keys do not stack and do not carry over to the next floor. They key is able to unlock the locked door somewhere else in this dungeon...\nYou found the locked door! If you don't have the key, you should keep exploring. If you do have the key, you can unlock the door and exit the dungeon floor. However, be careful, there's always a strong enemy guarding the way home, and once you decide to fight it, there's no way back into the dungeon!\nYou found an enemy! Combat in this game isn't standard \"hit for 2 damage\", instead it's skill and time based. You will be prompted with a problem (transcription, math, or unscrambling) that you will have to solve before time runs out. If you make a mistake or take too long, you take damage (and you can't heal!)\nSo, be careful, don't make mistakes, and keep calm...\nSo you decided to open the door! At the end of each floor is a boss fight, a super strong enemy which prevents you from getting back to the tavern. The boss fight works the same way as a standard enemy, only they're strong and have more difficult challenges. Good luck\nYou should take a break, you've earned it! Once you defeat the final boss fight, you return to the tavern where you can take a second and relax. As you complete dungeons, you'll receive favors, which you can redeem for goodies and treasures which will help you clear out even more dungeons. Take your time here, as once you leave the tavern, you're stuck in the dungeon until you defeat the next boss!\nItems are one-time use: they will help you in your journey. They range from a torch to extend your vision to a magical key which can open any lock. You cannot purchase more than one of each item. They are powerful and essential in helping you get to the deeper depths of the dungeon...\nYou've entered the next floor of the dungeon! As you get deeper, difficulty will increase... good luck!\nOccasionally, after hiring an extermination team, you may find their handiwork on the next floor. You should buy them a round when you return as thanks.";
	private String[] challengeCopy = { "A Jack of all trades is master of none", "A bad cause requires many words", "A bad excuse is better than none", "A bad penny always turns up", "A bad tree does not yield good apples", "A bad workman blames his tools", "A barking dog seldom bites", "A bird in hand is worth two in a bush", "A black plum is as sweet as a white", "A book holds a house of gold", "A broken friendship may be soldered but it will never be sound", "A broken friendship may be soldered but will never be sound", "A burden of one's own choice is not felt", "A burden of one's own choice is not felt", "A burnt child dreads the fire", "A calm sea does not make a skilled sailor", "A cat has nine lives", "A chain is no stronger than its weakest link", "A chain is no stronger than its weakest link", "A change is as good as a rest", "A closed mouth catches no flies", "A constant guest is never welcome", "A danger foreseen is half avoided", "A day of sorrow is longer than a month of joy", "A drop of ink may make a million think", "A dry March, a wet April and a cool May fill barn and cellar and bring much hay.", "A dry March, a wet April and a cool May may fill barn and cellar and bring much hay", "A fault confessed is half redressed", "A flower blooms more than once", "A fly will not get into a closed mouth", "A fool and his money are (soon) easily parted", "A fool and his money are soon easily parted", "A fool at forty is a fool forever", "A friend in need is a friend indeed", "A friend to all is a friend to none", "A friend's eye is a good mirror", "A good beginning makes a good end", "A good conscience is a soft pillow", "A good example is the best sermon", "A good mind possesses a kingdom", "A good name is better than a good face", "A growing youth has a wolf in his belly", "A guilty conscience needs no accuser", "A handful of patience is worth more than a bushel of brains", "A happy heart is better than a full purse", "A heavy purse gives to a light heart", "A hedge between keeps friendship green", "A hungry belly has no ears", "A hungry wolf is fixed to no place", "A journey of a thousand miles begins with a single step", "A journey of a thousand miles starts with a single step", "A lender nor borrower be", "A leopard cannot change its spots", "A lie begets a lie", "A little fire is quickly trodden out", "A little of what you fancy does you good", "A loaded wagon makes no noise", "A loveless life is a living death", "A loveless life is living death", "A man can die but once", "A man is as old as he feels himself to be", "A man is judged by his deeds, not by his words", "A man is known by the company he keeps", "A monkey in silk is a monkey no less", "A new broom sweeps clean (but the old brush knows all the corners...)", "A nod is as good as a wink (to a blind horse / man)", "A nod is as good as a wink (to a blind man)", "A picture paints a thousand words", "A problem shared is a problem halved", "A rising tide lifts all boats", "A rolling stone gathers no moss", "A rotten apple spoils the barrel", "A smooth sea never made a skilled mariner", "A soft answer turneth away wrath", "A stitch in time saves nine", "A stumble may prevent a fall", "A swallow does not make the summer", "A tidy house holds a bored woman", "A watched pot never boils", "A wise head keeps a still tongue", "A wonder lasts but nine days", "A young idler, an old beggar", "Absence is the mother of disillusion", "Absence makes the heart grow fonder", "Action without vision is a nightmare", "Actions speak louder than words", "Admiration is the daughter of ignorance", "Adversity makes strange bedfellows", "Advice is cheap", "Advice is least heeded when most needed", "Advisors run no risks", "After dinner rest a while, after supper walk a while", "Age before beauty", "Agree, for the law is costly", "All cats are grey in the dark", "All covet, all lose", "All days are short to industry and long to idleness", "All good things come to those who wait", "All in good time", "All is fair in love and war", "All roads lead to Rome", "All that glitters is not gold", "All things are difficult before they are easy", "All things grow with time - except grief", "All work and no play makes Jack a dull boy", "All's well that ends well", "An Englishman's home is his castle", "An Englishman's home is his castle", "An ant may well destroy a whole dam", "An apple a day keeps the doctor away", "An empty purse frightens away friends", "An idle brain is the devil's workshop", "An old fox is not easily snared", "An onion a day keeps everyone away", "An ounce of discretion is worth a pound of wit", "An ounce of prevention is worth a pound of cure", "Anger is the one thing made better by delay", "Another day, another dollar", "Any time means no time", "April showers bring May flowers", "As you sow, so shall you reap", "Ask me no questions, I'll tell you no lies", "Bad beginnings lead to bad results", "Bad news travels fast", "Be just before you are generous", "Be swift to hear, slow to speak", "Beauty is in the eye of the beholder", "Beauty is only skin deep", "Beauty is the wisdom of women. Wisdom is the beauty of men", "Better an egg today than a hen tomorrow", "Better be alone than in bad company", "Better be the head of a dog than the tail of a lion", "Better be untaught than ill-taught", "Better brain than brawn. ", "Better flatter a fool than fight him", "Better late than never", "Better lose the saddle than the horse", "Better safe than sorry", "Better the devil you know than the devil you don't know", "Better to drink the milk than to eat the cow", "Beware of Greeks bearing gifts", "Birds of a feather flock together", "Blood is thicker than water", "Blood will out", "Care is no cure", "Charity begins at home", "Children and fools tell the truth", "Children are certain cares but uncertain comforts", "Cleanliness is next to godliness", "Clear moon, frost soon", "Clothes don't make the man", "Constant occupation prevents temptation", "Courtesy is contagious", "Cross the stream where it is shallowest", "Crosses are ladders that lead to heaven", "Dead men tell no lies", "Death is the great leveller", "Diamonds cut diamonds", "Diligence is the mother of good fortune", "Discretion is the better part of valour", "Diseases of the soul are more dangerous than those of the body", "Distance makes the heart grow fonder", "Dogs of the same street bark alike", "Don't bark if you can't bite", "Don't count your chickens before they're hatched", "Don't dig your grave with your own knife and fork", "Don't judge a book by its cover", "Early to bed, and early to rise, makes a man healthy, wealthy and wise", "Easier said than done", "Eating an apple every day can help to keep you healthy", "Elbow grease is the best polish", "Empty vessels make the most noise", "Enough is as good as a feast", "Even a worm will turn", "Every ass likes to hear himself bray", "Every cloud has a silver lining", "Every man for himself", "Every man has his price", "Every man is the architect of his own fortune", "Every man thinks his own geese swans", "Every path has its puddle", "Every rose has its thorn", "Every why has a wherefore", "Everything in the garden is rosy", "Experience is the father of wisdom", "Facts speak louder than words", "Failure teaches success", "Fair exchange is no robbery", "False friends are worse than open enemies", "Familiarity breeds contempt", "Fear lends wings", "Fine words butter no parsnips", "First come, first served", "Fool me once, shame on you; fool me twice, shame on me", "Fools rush in where angels fear to tread", "Forbidden fruit is sweet", "Friendship is like money, easier made than kept", "Friendship is love with understanding", "Gardens are not made by sitting in the shade", "Give someone an inch and they will take a mile (or yard)", "Give someone enough rope and they will hang themselves", "God helps those who help themselves", "Good accounting makes good friends", "Good and quickly seldom meet", "Good management is better than good income", "Grasp all, lose all", "Great minds think alike", "Great oaks grow from small acorns", "Grief divided is made lighter", "Half a loaf is better than none", "Handsome is what handsome does", "Hard words break no bones", "Haste makes waste", "Hatred is as blind as love", "He can who believes he can", "He has enough who is content", "He laughs best who laughs last", "He who digs a pit for others falls into it himself", "He who hesitates is lost", "He who is everywhere is nowhere", "He who knows nothing doubts nothing", "He who pays the piper calls the tune", "He who plays with fire gets burnt", "He who wills the end wills the means", "Health is better than wealth", "Home is where the heart is", "Honesty is the best policy", "Honey catches more flies than vinegar", "However long the night, the dawn will break", "Hunger is a good sauce", "If a camel gets his nose in a tent, his body will follow", "If a person keeps moving from place to place, they gain neither friends nor possessions", "If in February there be no rain, 'tis neither good for hay nor grain", "If two ride a horse, one must ride behind", "If wishes were horses, then beggars would ride", "If you are patient in one moment of anger, you will avoid 100 days of sorrow", "If you chase two rabbits, you will not catch either one", "If you want a friend, be a friend", "Ignorance is bliss", "In for a penny, in for a pound", "In the land of the blind the one-eyed man is king", "In times of prosperity friends are plentiful", "It is always darkest before the dawn", "It never rains but it pours", "It takes all sorts to make a world", "It's no use crying over spilt milk", "Justice delayed is justice denied", "Kill not the goose that lays the golden egg", "Kill one to warn a hundred", "Kindle not a fire you cannot put out", "Kindness begets kindness", "Kindness, like a boomerang, always returns", "Knowledge in youth is wisdom in age", "Knowledge is power", "Laughter is the best medicine", "Learn to walk before you run", "Learning is a treasure that will follow its owner everywhere", "Least said, soonest mended", "Let bygones be bygones", "Let the chips fall where they may", "Liars need good memories", "Liberty is not licence (us: license)", "Like father, like son", "Little strokes fell good oaks", "Look before you leap", "Loose lips sink ships", "Losers weepers, finders keepers", "Love is blind", "Make a silk purse out of a sow's ear", "Man is the head of the family and woman is the neck that turns the head", "Man proposes, God disposes", "Manners make the man", "Many a true word is spoken in jest", "Many hands make light work", "March comes in like a lion and goes out like a lamb", "March winds and April showers bring forth May flowers", "Mark, learn and inwardly digest", "Marry in haste, repent as leisure", "Memory is the treasure of the mind", "Men make houses, women make homes", "Might as well be hanged for a sheep as for a lamb", "Misery loves company", "Money begets money", "Money doesn't grow on trees", "Monkey see, monkey do", "Necessity is the mother of invention", "Necessity knows no law", "Need teaches a plan", "Needs must when the devil drives", "Never put off till tomorrow what can be done today", "Never say die", "Never trouble troubles until troubles trouble you", "No cure no pay", "No joy without annoy", "No losers, no winners", "No man can serve two masters", "No man is a hero to his valet", "No man is an island", "No news is good news", "No pain, no gain", "No rain, no grain", "No smoke without fire", "No wind, no waves", "Nobody is perfect", "Nothing ventured nothing gained", "Oil and water do not mix", "Once bitten, twice shy", "One father is (worth) more than a hundred schoolmasters", "One good turn deserves another", "One man's meat is another man's poison", "One man's trash is another man's treasure", "One of these days is none of these days", "One swallow does not make the summer", "One swallow doesn't make a summer", "One today is worth two tomorrows", "Only real friends will tell you when your face is dirty", "Opportunity seldom knocks twice", "Out of sight, out of mind", "Out of the mouth of babes and sucklings", "Parents are patterns", "Penny wise, pound foolish", "People who live in glass houses should not throw stones", "Pity is akin to love", "Poverty waits at the gates of idleness", "Practice makes perfect", "Prevention is better than cure", "Pride comes before a fall", "Procrastination is the thief of time", "Punctuality is the soul of business", "Put all your eggs in one basket", "Rats desert a sinking ship", "Reason does not come before years", "Respect is greater from a distance", "Revenge is a dish best served cold", "Revenge is sweet", "Rome was not built in a day", "Safe bind, safe find", "Save me from my friends", "Saying is one thing, doing is another", "Short reckonings make long friends", "Sickness in the body brings sadness to the mind", "Silence gives consent", "Snug as a bug in a rug", "Spare the rod and spoil the child", "Speech is silver, silence is golden", "Sticks and stones will break my bones but names will never hurt me", "Still waters run deep", "Stolen fruit is the sweetest", "Stolen pleasures are the sweetest", "Tall oaks grow from little acorns", "Telling one lie will lead you to tell another one", "The apple doesn't fall far from the tree", "The best advice is found on the pillow", "The best helping hand is at the end of your sleeve", "The best things in life are free", "The darkest hour is just before dawn", "The devil looks after his own", "The devil makes work for idle hands", "The die is cast", "The early bird catches the worm", "The end justifies the means", "The first step is the hardest", "The more haste, the less speed", "The more you have, the more you want", "The mouse that has but one hole is quickly taken", "The pen is mightier than the sword", "The proof of the pudding is in the eating", "The road to hell is paved with good intentions", "The tongue wounds more than a lance", "The truth is in the wine", "The used key is always bright", "The way to a man's heart is through his stomach", "The wish is father to the thought", "Two wrongs don't make a right", "Uneasy lies the head that wears a crown", "Union is strength", "United we stand, divided we fall", "Unwillingness easily finds an excuse", "Use it or lose it", "Variety is the spice of life", "Virtue is its own reward", "Vision without action is a daydream", "Walls have ears", "Waste not, want not", "We are what we eat", "What a man says drunk, he thinks sober", "What soberness conceals, drunkenness reveals", "What the eye doesn't see, the heart doesn't grieve over", "When in Rome, do as the Romans do", "When poverty comes in the door, love goes out the window", "When the cat's away, the mice play", "Where there's a will there's a way", "Where there's life there's hope", "Who makes himself a sheep will be eaten by the wolves", "Wisdom is better than strength", "Worry often gives a small thing a big shadow", "You are never too old to learn", "You are what you eat", "You can lead a horse to water but you can't make it drink", "You can't teach an old dog new tricks", "You never know what you can do until you try", "You scratch my back and I'll scratch yours", "Accidents will happen" };
	private String[] challengeScrambles = { "able", "accent", "accept", "active", "adjust", "advice", "affect", "afford", "afraid", "agency", "agony", "agree", "aisle", "alert", "allow", "aloof", "altar", "ample", "anger", "ankle", "annual", "ant", "appear", "apple", "area", "arm", "art", "ash", "ask", "assume", "asylum", "attic", "avenue", "avoid", "awake", "awful", "axis", "bacon", "bail", "bait", "bald", "balm", "ballet", "ban", "bang", "banish", "basic", "basin", "basket", "battle", "beach", "bear", "beard", "beef", "behave", "belief", "belly", "belong", "berry", "bird", "blank", "bless", "blonde", "blood", "boil", "bold", "boom", "border", "borrow", "bottom", "bounce", "bowl", "boy", "brag", "braid", "bread", "breeze", "bride", "broken", "bronze", "bubble", "budge", "budget", "bullet", "burial", "bury", "bush", "busy", "cable", "cage", "calf", "calm", "can", "candle", "canvas", "carbon", "carpet", "cart", "carve", "cash", "cast", "cat", "cater", "cattle", "cave", "cellar", "chalk", "chaos", "charm", "cheat", "cheese", "cherry", "chew", "child", "chin", "chop", "chord", "church", "circle", "civic", "clam", "clash", "clay", "climb", "clinic", "closed", "cloud", "coach", "coast", "coerce", "coffin", "collar", "color", "comedy", "core", "cotton", "cough", "couple", "cow", "cower", "craft", "create", "crew", "crisis", "crouch", "crown", "cruel", "cruise", "curl", "daily", "dairy", "dance", "danger", "dash", "dawn", "day", "dead", "deaf", "death", "debt", "decide", "deer", "define", "deport", "deputy", "desire", "detail", "devote", "differ", "dilute", "dinner", "dip", "dire", "disk", "dive", "dollar", "donate", "donor", "doom", "dose", "dozen", "dragon", "drama", "dress", "driver", "drown", "duck", "due", "dump", "dust", "early", "eat", "echo", "egg", "elapse", "elbow", "elect", "embryo", "employ", "empty", "energy", "enjoy", "ensure", "essay", "ethics", "ethnic", "evil", "exceed", "excess", "exile", "expert", "expose", "eye", "face", "factor", "fade", "faint", "faith", "family", "far", "fare", "farmer", "favor", "fax", "feast", "fence", "fever", "fibre", "fight", "file", "filter", "finger", "firm", "first", "fit", "flag", "flavor", "flawed", "float", "flood", "flour", "flu", "folk", "forbid", "forget", "fork", "format", "forum", "foster", "fox", "frank", "friend", "frozen", "fuel", "fund", "fuss", "galaxy", "gap", "gape", "garlic", "gasp", "gear", "gem", "get", "ghost", "gift", "glad", "glare", "gloom", "glove", "glue", "go", "goat", "gold", "gossip", "gown", "graph", "gravel", "graze", "grab", "grin", "grip", "groan", "grudge", "guilt", "gun", "habit", "halt", "hammer", "hang", "happy", "harbor", "harm", "harsh", "haul", "have", "hay", "hear", "heaven", "heel", "heir", "helmet", "herb", "hero", "hiccup", "high", "hill", "hobby", "hole", "honest", "honey", "horror", "hotdog", "human", "hunter", "ice", "ideal", "ignore", "immune", "inch", "index", "infect", "injure", "inn", "inner", "inside", "insist", "insure", "irony", "issue", "ivory", "jail", "jam", "jaw", "jelly", "jewel", "joke", "judge", "jump", "junior", "jury", "kale", "keen", "kid", "kill", "killer", "kind", "kit", "knead", "knee", "knit", "knot", "label", "lack", "lady", "lake", "lamb", "lane", "laser", "last", "latest", "launch", "lawn", "layer", "leader", "leak", "learn", "lease", "left", "lick", "life", "like", "lily", "linear", "linen", "lion", "listen", "live", "liver", "loan", "locate", "lodge", "lonely", "lose", "loud", "lounge", "lover", "loyal", "lunch", "maid", "main", "make", "makeup", "march", "marine", "market", "marsh", "mass", "matrix", "mayor", "meadow", "mean", "means", "meat", "medal", "member", "mercy", "metal", "middle", "mild", "milk", "miner", "mirror", "mist", "mix", "mobile", "modest", "module", "mold", "moment", "monkey", "mood", "mope", "mosaic", "mother", "moving", "mud", "murder", "museum", "mutter", "myth", "name", "native", "neck", "nerve", "node", "noisy", "north", "notion", "novel", "nuance", "nun", "nut", "obese", "occupy", "ocean", "office", "offset", "old", "opera", "oppose", "orbit", "origin", "other", "oven", "owner", "pace", "page", "palace", "pan", "pardon", "past", "path", "pause", "pawn", "pay", "peanut", "penny", "pepper", "pest", "piano", "pie", "pillow", "pin", "pitch", "plain", "planet", "plead", "pledge", "pluck", "poem", "policy", "pool", "poor", "porter", "powder", "pray", "prayer", "preach", "pride", "print", "prize", "proud", "prove", "push", "queen", "queue", "quit", "rabbit", "racial", "rack", "raid", "rain", "ranch", "rank", "rare", "rate", "reach", "ready", "rear", "rebel", "redeem", "reform", "region", "reign", "relate", "remark", "remind", "rent", "resort", "result", "retain", "reveal", "revise", "rhythm", "ribbon", "ride", "rider", "rifle", "riot", "river", "roar", "robot", "rocket", "room", "rope", "round", "rub", "rumor", "rung", "rush", "sacred", "safari", "safe", "salad", "salon", "sample", "sandal", "say", "scrap", "scrape", "sea", "season", "secret", "seem", "self", "sell", "senior", "sermon", "shake", "shape", "shark", "sharp", "shave", "sheet", "shield", "shine", "shoe", "shout", "shrug", "sick", "siege", "sieve", "silk", "singer", "sip", "sister", "skate", "sketch", "skill", "skirt", "sky", "slap", "slave", "sleeve", "slime", "slip", "slogan", "small", "smoke", "smooth", "snack", "snake", "snarl", "sniff", "soap", "sock", "solo", "solve", "sound", "spell", "spider", "spill", "spit", "spoon", "spot", "squad", "squash", "stab", "stake", "stamp", "statue", "stay", "steady", "steak", "steam", "stem", "sticky", "still", "sting", "stitch", "stone", "stop", "store", "strain", "strap", "step", "street", "stride", "strip", "stroll", "studio", "stuff", "stun", "stupid", "suburb", "subway", "suffer", "suit", "sum", "summit", "suntan", "sure", "swarm", "sweat", "swell", "swipe", "switch", "symbol", "tablet", "tank", "target", "tasty", "taxi", "team", "tear", "tell", "tenant", "tent", "thank", "thanks", "theory", "thigh", "threat", "throat", "thrust", "thumb", "tidy", "tiger", "timber", "tiptoe", "tired", "toast", "toll", "ton", "tool", "toss", "touch", "tower", "toy", "trail", "trait", "tray", "tread", "treaty", "trench", "tribe", "trial", "tried", "truck", "true", "tumour", "tunnel", "turn", "tycoon", "ulcer", "uneasy", "union", "unique", "unity", "unlike", "urge", "useful", "utter", "vacant", "vain", "varied", "vat", "veil", "velvet", "viable", "victim", "vile", "virgin", "virus", "voyage", "wagon", "waiter", "wake", "wander", "want", "war", "water", "weak", "wealth", "weave", "weed", "whale", "wheel", "whole", "widen", "width", "window", "winner", "wire", "wolf", "wonder", "wool", "worker", "worry", "wound", "wrist", "year", "young", "zone" };

	private final String VERSION = "1.3.1";

	public static void main(String[] args) {
		TinySound.init();
		singleton = new CSE110HC();
		singleton.Run();
		TinySound.shutdown();
		System.exit(0);
	}

	/** Runs the main program */
	private void Run() {
		print("Loading...\n");
		scanner = new Scanner(System.in);
		ResetGame();
		dungeonAmbiance = TinySound.loadMusic("/sounds/music/ambience.wav");
		tavernAmbiance = TinySound.loadMusic("/sounds/music/tavern_ambience.wav");
		tavernMusic = TinySound.loadMusic("/sounds/music/tavern_music.wav");
		bossMusic = TinySound.loadMusic("/sounds/music/battle_boss.wav");
		fightMusic = TinySound.loadMusic("/sounds/music/battle.wav");

		print("Welcome to the dungeon!");
		print("Version " + VERSION);
		WaitForMS(100);
		while (true) {
			switch (status) {
				case menu:
					print("\n");
					int choice = IntInputList(new String[] { "Start Game", "Set Seed", "Set Difficulty",
							"Set Global Text Speed Modifier",
							tutorial == null ? "Enable In-Game Tutorial" : "Disable In-Game Tutorial", "Display Help Text", "Quit" },
							0, true);
					switch (choice) {
						case 1:
							if (tutorial == null) {
								tutorial = new TutorialHandler(false);
							}
							TinySound.loadSound("/sounds/effects/door_open.wav").play();
							print("You descend into the first floor of the dungeon...");
							dungeonAmbiance.play(true);
							dungeonWidth = 4 + random.nextInt(4);
							dungeonHeight = 3 + random.nextInt(2);
							dungeonDepth = 1;
							CreateDungeon(dungeonWidth, dungeonHeight);
							GenerateDungeon(Round(dungeonDepth * difficulty), random);
							tutorial.PrintTutorial(TutorialHandler.Tutorials.dungeonFloor);
							status = Status.dungeon;
							break;
						case 2:
							seed = IntInput("Enter a seed: ");
							random = new Random(seed);
							break;
						case 3:
							print("Enter a difficulty");
							int diff = IntInputList(new String[] { "Casual", "Default", "Challenging" }, 0);
							switch (diff) {
								case 1:
									CSE110HC.SetDifficulty(0.5);
									break;
								case 2:
									CSE110HC.SetDifficulty(1);
									break;
								case 3:
									CSE110HC.SetDifficulty(1.5);
									break;
							}
							break;
						case 4:
							print("Enter a speed modifier from 0 to 1: ", false);
							messageSpeedModifier = DoubleInput(0, 1);
							break;
						case 5:
							if (tutorial == null) {
								print("Tutorial enabled!");
								tutorial = new TutorialHandler(true);
							} else {
								print("Tutorial disabled!");
								tutorial = null;
							}
							break;
						case 6:
						case -1:
							print(tutorialDump, 0);
							break;
						case 7:
							return;
					}
					break;
				case dungeon:
					PrintKnownMap();
					PlayerMovePrompt();
					// TODO better traveling UI
					break;
				case combat:
					Room r = GetRoom();
					Enemy e = r.GetEnemy();
					if (difficulty <= 1) {
						print("The " + e.GetName() + " has " + e.GetHealth() + " health");
					}
					// While the enemy is alive
					while (e.GetHealth() > 0) {
						// If the challenge was beaten
						if (StartChallenge(e.GetStrength(), random)) {
							// Deal damage to the enemy
							TinySound.loadSound("/sounds/effects/hit_enemy.wav").play();
							e.DealDamage(1);
							print("You hit the enemy " + e.GetName() + "!");
							if (difficulty <= 1) {
								print("It has " + e.GetHealth() + " health remaining!");
							}

						} else {
							// Deal damage
							print("The " + e.GetName() + " attacks you!");
							if (Player.HasSecondWind()) {
								Player.SetSecondWind(false);
								TinySound.loadSound("/sounds/effects/hit_player_block.wav").play();
								print("Your armor blocks the attack, but is ruined in the process!");
							} else {
								Player.GetPlayer().DealDamage(1);
								TinySound.loadSound("/sounds/effects/hit_player.wav").play();
								print("You have " + Player.GetPlayer().GetHealth() + " health remaining!");
								if (Player.GetPlayer().GetHealth() == 1) {
									TinySound.loadSound("/sounds/effects/health_warning.wav").play();
								}
							}
							// Exit if the player died
							if (Player.GetPlayer().GetHealth() < 1) {
								dungeonAmbiance.stop();
								fightMusic.stop();
								bossMusic.stop();
								TinySound.loadSound("/sounds/effects/defeat_player.wav").play();
								break;
							}
						}
					}
					if (Player.GetPlayer().GetHealth() < 1) {
						break;
					}
					// When the enemy is dead
					fightMusic.stop();
					bossMusic.stop();

					r.ClearEnemy();
					if (GetRoom().IsDoor()) {
						Player.AddScore(10);
						Player.AddFavor();
						status = Status.tavern;
						TinySound.loadSound("/sounds/effects/defeat_boss.wav").play();
						print("You have successfully defeated the boss of this floor! The stairs to the tavern are now clear!");
						TinySound.loadSound("/sounds/effects/door_close.wav").play();
						print("After felling the beast, you return to the tavern to recover.\n(+1 favor)");
						tavernAmbiance.play(true);
						tavernMusic.play(true);
						if (IsDungeonClear()) {
							print("The tavernkeep is impressed by your handiwork at clearing the dungeon completely!\n(+1 favor)");
							Player.AddScore(5);
							Player.AddFavor();
						}
						if (Player.HasCursedMap() || Player.HasDungeonMap()) {
							Player.SetCursedMap(false);
							Player.SetDungeonMap(false);
							Player.AddFavor();
							Player.AddScore(2);
							print("You give the now detailed map back to the tavernkeep.\n(+1 favor)");
						}
						if (Player.HasTorch()) {
							Player.SetTorch(false);
							print("You discard the now charred torch.\n");
						}
						print("\n", 0);
						tutorial.PrintTutorial(TutorialHandler.Tutorials.tavern);
					} else {
						TinySound.loadSound("/sounds/effects/defeat_enemy.wav").play();
						Player.AddScore(3);
						status = Status.dungeon;
					}
					break;
				case tavern:
					int favors = Player.GetFavors();
					tutorial.PrintTutorial(TutorialHandler.Tutorials.favor);
					print("\nYou have " + favors + " favor" + (favors == 0 || favors > 1 ? "s" : "") + ".");
					int tavernChoice = IntInputList(new String[] { "Enter the next dungeon (To Floor " + (dungeonDepth + 1) + ")",
							"Pruchase a large torch (1 favor)",
							"Hire an extermination team for the next floor (1 favor)", "Purchase a set of armor (1 Favor)",
							"Purchase a Skeleton Key (2 favors)", "Purchase a Dungeon Map (3 favors)",
							"Purchase a Cursed Dungeon Map (4 favors)" }, 0, true);
					switch (tavernChoice) {
						case -1:
							tutorial.ForcePrintTutorial(TutorialHandler.Tutorials.favor);
							break;
						case 1:
							tavernAmbiance.stop();
							tavernMusic.stop();
							TinySound.loadSound("/sounds/effects/door_open.wav").play();
							tutorial.PrintTutorial(TutorialHandler.Tutorials.nextFloor);
							EnterNextDungeon(random);
							break;
						case 2:
							if (favors >= 1 && !Player.HasTorch()) {
								Player.AddFavors(-1);
								TinySound.loadSound("/sounds/effects/redeem_torch.wav").play();
								print("You purchase a large torch, wrapped in cloth and dripping with pungent alcohol.");
								Player.SetTorch(true);
							} else if (favors < 1) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You don't have enough favors to redeem this item.");
							} else if (Player.HasTorch()) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("One torch will last the whole dungeon.");
							}
							break;
						case 3:
							if (favors >= 1 && !reducedEnemies) {
								Player.AddFavors(-1);
								TinySound.loadSound("/sounds/effects/walk.wav").play();
								print("You hire an extermination team to thin out the enemies of the next floor");
								reducedEnemies = true;
							} else if (favors < 1) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You don't have enough favors to redeem this item.");
							} else if (reducedEnemies) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("The extermination team is already busy.");
							}
							break;
						case 4:
							if (favors >= 1 && !Player.HasSecondWind()) {
								Player.AddFavors(-1);
								TinySound.loadSound("/sounds/effects/redeem_armor.wav").play();
								print("You purchase a powerful, but brittle, set of armor.");
								Player.SetSecondWind(true);
							} else if (favors < 1) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You don't have enough favors to redeem this item.");
							} else if (Player.HasSecondWind()) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You can't wear another set of armor over your current set.");
							}
							break;
						case 5:
							if (favors >= 2 && !Player.HasSkeletonKey()) {
								Player.AddFavors(-2);
								TinySound.loadSound("/sounds/effects/redeem_key.wav").play();
								print("You purchase a blank key with the handle in the shape of a skull.");
								Player.SetSkeletonKey(true);
							} else if (favors < 2) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You don't have enough favors to redeem this item.");
							} else if (Player.HasSkeletonKey()) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You already have a key of this kind.");
							}
							break;
						case 6:
							if (favors >= 3 && !Player.HasDungeonMap() && !Player.HasCursedMap()) {
								Player.AddFavors(-3);
								TinySound.loadSound("/sounds/effects/redeem_map.wav").play();
								print("You purchase an incomplete map of the next dungeon floor.");
								Player.SetDungeonMap(true);
							} else if (favors < 3) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You don't have enough favors to redeem this item.");
							} else if (Player.HasDungeonMap()) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You already have a map for the next floor.");
							} else if (Player.HasCursedMap()) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You get the feeling you won't need a normal map...");
							}
							break;
						case 7:
							if (favors >= 4 && !Player.HasCursedMap() && !Player.HasDungeonMap()) {
								Player.AddFavors(-4);
								TinySound.loadSound("/sounds/effects/redeem_map.wav").play();
								print("You purchase an old, blank sheet of parchment which gives you the chills.");
								Player.SetCursedMap(true);
							} else if (favors < 4) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You don't have enough favors to redeem this item.");
							} else if (Player.HasCursedMap()) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("No way. One is aready frightening enough.");
							} else if (Player.HasDungeonMap()) {
								TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
								print("You probably won't need two maps, but there's something about that cursed map...");
							}
							break;
					}
					break;
			}
			if (Player.GetPlayer().GetHealth() < 1) {
				print("You died! Score: " + Round(Player.GetScore()) + "\nSeed: " + seed + "\nDifficulty: "
						+ CSE110HC.GetDifficulty());
				ResetGame();
				status = Status.menu;
			}
		}
	}

	/**
	 * @return Whether the entire current dungeon was explored.
	 */
	private boolean IsDungeonClear() {
		for (Room[] rooms : dungeonFloor) {
			for (Room room : rooms) {
				if (room.IsVisitable() && (room.GetEnemy() != null || !room.IsDiscovered())) {
					return false;
				}
			}
		}
		return true;
	}

	private void ResetGame() {
		new Player(3);
		random = new Random();
		seed = random.nextInt();
		random = new Random(seed);
		dungeonWidth = 0;
		dungeonHeight = 0;
		dungeonDepth = 0;
		status = Status.menu;
	}

	private void EnterNextDungeon(Random random) {
		print("\nGenerating new dungeon...\n");
		Player.AddScore(2);
		WaitForMS(500);
		dungeonDepth++;
		dungeonWidth += random.nextInt(2);
		dungeonHeight += random.nextInt(1);
		CreateDungeon(dungeonWidth, dungeonHeight);
		GenerateDungeon(dungeonDepth, random);
		if (Player.HasTorch()) {
			TinySound.loadSound("/sounds/effects/torch_start.wav").play();
		}
		if (Player.HasCursedMap()) {
			TinySound.loadSound("/sounds/effects/dungeon_map.wav").play();
			print("The ancient map writes itself, revealing the secrets of the dungeon...");
			RevealFloor();
		}
		if (Player.HasDungeonMap()) {
			TinySound.loadSound("/sounds/effects/redeem_map.wav").play();
			print("The crude drawings give you a general idea of the dungeon layout.");
			RevealFloorReduced();
		}
		// TODO handle normal map vs cursed map
		status = Status.dungeon;
	}

	/**
	 * Initializes the dungeon floor
	 * 
	 * @param width  Number of rooms wide
	 * @param height Number of rooms high
	 */
	private void CreateDungeon(int width, int height) {
		dungeonFloor = new Room[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				SetRoom(new Room(new Vector2(x, y), false));
			}
		}
	}

	/**
	 * Fills the rooms of the dungeon
	 * 
	 * @param difficulty The number of extra rooms, enemies, and strength of enemies
	 * @param random     The random generator used
	 */
	private void GenerateDungeon(double difficulty, Random random) {
		int keyX = -1, keyY = -1;
		int doorX = -1, doorY = -1;
		int strtX = -1, strtY = -1;
		difficulty *= CSE110HC.GetDifficulty();

		// Generate key and door positions
		while (!InBounds(keyX, keyY)) {
			keyX = random.nextInt(dungeonFloor[0].length - (dungeonFloor[0].length / 5) - 1) + (dungeonFloor[0].length / 5)
					+ 1;
			keyY = random.nextInt(dungeonFloor.length);
		}
		while (!InBounds(doorX, doorY) || (doorX == keyX && doorY == keyY)) {
			doorX = random.nextInt(dungeonFloor[0].length - (dungeonFloor[0].length / 5) - 1) + (dungeonFloor[0].length / 5)
					+ 1;
			doorY = random.nextInt(dungeonFloor.length);
			if (new Vector2(doorX, doorY)
					.Distance(new Vector2(keyX, keyY)) <= Math.max(dungeonFloor.length, dungeonFloor[0].length) / 3) {
				doorX = -1;
				doorY = -1;
			}
		}
		SetRoom(new Room(new Vector2(keyX, keyY), true, true));
		SetRoom(new Room(new Vector2(doorX, doorY), true, false, true));

		strtX = 0;
		strtY = random.nextInt(dungeonFloor.length);
		Vector2 pos = new Vector2(strtX, strtY);

		// Generate path from key to door
		Vector2 pathCoord = new Vector2(doorX, doorY);
		while (!IsContinuous(new Vector2(keyX, keyY), new Vector2(doorX, doorY))) {
			pathCoord = CoordTowards(pathCoord, new Vector2(keyX, keyY), random);
			SetRoom(new Room(pathCoord, true));
		}

		// Generate path from start to random tunnel piece
		pathCoord = new Vector2(strtX, strtY);
		Vector2 targetCoord = GetVisitableRoom(random).GetCoordinates();
		while (!IsContinuous(new Vector2(strtX, strtY), targetCoord)) {
			pathCoord = CoordTowards(pathCoord, targetCoord, random);
			SetRoom(new Room(pathCoord, true));
		}

		// Generate extra tunnel pieces
		for (int i = 0; i < dungeonFloor.length * dungeonFloor[0].length + difficulty * 2; i += 5) {
			Room room = GetVisitableRoom(random);
			Vector2 newCoord = room.GetCoordinates().Sum(RelativeOffset(random.nextInt(4)));
			if (!InBounds(newCoord) || GetRoom(newCoord).IsVisitable()) {
				i -= 5;
				continue;
			}
			SetRoom(new Room(newCoord, true));
		}

		int enemiesReduced = Round(difficulty / 2.0);
		enemiesReduced *= reducedEnemies ? 1 : 0;
		reducedEnemies = false;
		for (int i = 0, safety = 1000; i < difficulty && safety > 0; safety--) {
			Room room = GetVisitableRoom(random);
			if (!room.IsDoor() && !room.HasKey() && !room.GetCoordinates().equals(pos) && room.GetEnemy() == null
					&& !room.GetReduced()) {
				// If there should be reduced enemies
				if (enemiesReduced > 0) {
					room.SetReduced(true);
					enemiesReduced--;
				} else {
					room.SetEnemy(new Enemy(Round(1 + difficulty / 5), Round(1 + difficulty / 2), random));
				}
				i++;
			}
		}

		GetRoom(doorX, doorY).SetEnemy(new Enemy(Round(3 + difficulty / 5), Round(difficulty), random));

		// Place player on left side of map
		if (!GetRoom(pos).IsVisitable()) {
			SetRoom(new Room(new Vector2(strtX, strtY), true));
		}
		MoveTo(pos);
		GetRoom().RevealRoom();
	}

	/**
	 * @param x X position of a grid
	 * @param y Y position of a grid
	 * @return Room at coordinates, or null if out of range
	 */
	private Room GetRoom(int x, int y) {
		return GetRoom(new Vector2(x, y));
	}

	/**
	 * @param coords Vector2 coordinates of a grid
	 * @return Room at coordinates, or null if out of range
	 */
	private Room GetRoom(Vector2 coords) {
		return InBounds(coords) ? dungeonFloor[coords.y][coords.x] : null;
	}

	/**
	 * @return The room the player is currently in
	 */
	private Room GetRoom() {
		return GetRoom(Player.GetPos());
	}

	/**
	 * @param random The random generator used
	 * @return A random room which can be visited
	 */
	private Room GetVisitableRoom(Random random) {
		for (int i = 0; i < 1000; i++) {
			Room r = GetRandomRoom(random);
			if (r.IsVisitable()) {
				return r;
			}
		}
		print("ERROR! GetVisitableRoom() reached the safety limit!");
		return null;
	}

	/**
	 * @return A random room in the dungeon
	 * @param random The random generator used
	 */
	private Room GetRandomRoom(Random random) {
		return GetRoom(random.nextInt(dungeonFloor[0].length), random.nextInt(dungeonFloor.length));
	}

	/**
	 * Overrides the room at the new room's coordinates
	 * 
	 * @param room The room to be placed in the dungeon
	 * @return The room provided
	 */
	private Room SetRoom(Room room) {
		Vector2 c = room.GetCoordinates();
		dungeonFloor[c.y][c.x] = room;
		return room;
	}

	/**
	 * Prints the known map to the console.
	 */
	private void PrintKnownMap() {
		String floor = "\t";
		for(int i = 0; i < dungeonFloor[0].length + 2; i++) {
			floor += "-";
		}
		floor += "\n\t";
		for (Room[] row : dungeonFloor) {
			floor += "-";
			for (Room room : row) {
				if (room == null) {
					floor += "?";
					continue;
				}
				floor += room.OnMap();
			}
			floor += "-\n\t";
		}

		for(int i = 0; i < dungeonFloor[0].length + 2; i++) {
			floor += "-";
		}
		print(floor, 0);
	}

	/**
	 * Reveals every room in the dungeon for debugging purposes or dungeon map
	 */
	private void RevealFloor() {
		for (Room[] row : dungeonFloor) {
			for (Room room : row) {
				if (room == null) {
					continue;
				}
				room.RevealRoom();
			}
		}
	}

	private void RevealFloorReduced() {
		for (Room[] row : dungeonFloor) {
			for (Room room : row) {
				if (room == null || room.IsVisitable()) {
					continue;
				}
				room.RevealRoom();
			}
		}
	}

	/**
	 * Creates and starts a random challenge for defeating a monster
	 * 
	 * @param budget The difficulty rating of the challenge.
	 * @param random The random generator used
	 * @return Whether the challenge was usccessful or not
	 */
	private boolean StartChallenge(double budget, Random random) {
		int c = random.nextInt(3);
		switch (c) {
			case 1: // Complete a simple math problem
				return ChallengeMath(budget, random);
			case 2: // Copy a phrase or sentence
				return ChallengeCopy(budget, random);
			default: // Unscramble a word
				return ChallengeScramble(budget, random);
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
	private boolean ChallengeMath(double budget, Random random) {
		double answer;
		String op;
		int opNum = random.nextInt(3);
		double timeLimit = 10 - ((5.0 * budget) / (budget + 10.0));

		int firstNum = random.nextInt(Round(budget * (1 + random.nextInt(5)))) + 1;
		int secondNum = random.nextInt(Round(budget * (1 + random.nextInt(5)))) + 1;
		switch (opNum) {
			case 0:
				answer = firstNum + secondNum;
				op = "+";
				break;
			case 1:
				answer = firstNum - secondNum;
				op = "-";
				break;
			default:
				answer = firstNum * secondNum;
				op = "*";
				timeLimit *= Math.min(firstNum, secondNum) / 2.0;
				if (firstNum == 10 || secondNum == 10) {
					timeLimit = 5;
				}
				break;

		}
		timeLimit = Round(timeLimit, 2);

		print("\nMath Challenge!");
		print("You have " + timeLimit + " seconds to solve the following problem");
		WaitForEnter();
		print(firstNum + " " + op + " " + secondNum + " = ", 0, false);
		double startTime = System.currentTimeMillis() / 1000.0;
		String guessString = scanner.nextLine();
		double endTime = System.currentTimeMillis() / 1000.0;
		double elapse = Round(endTime - startTime, 1);
		double guess;
		try {
			guess = Round(Double.parseDouble(guessString), 2);
		} catch (Exception e) {
			TinySound.loadSound("/sounds/effects/challenge_failure.wav").play();
			print("That's not a valid answer!");
			print("The answer was " + answer);
			return false;
		}
		if (guess == answer && elapse < timeLimit) {
			TinySound.loadSound("/sounds/effects/challenge_success.wav").play();
			print("Good job! (" + elapse + "/" + timeLimit + ")");
			return true;
		} else if (elapse >= timeLimit) {
			TinySound.loadSound("/sounds/effects/challenge_failure.wav").play();
			print("Time out!");
			print(elapse + " >= " + timeLimit);
			print("The answer was " + answer);
		} else {
			TinySound.loadSound("/sounds/effects/challenge_failure.wav").play();
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
	private boolean ChallengeCopy(double budget, Random random) {
		// minimum phrase length : 14
		// maximum phrase length : 88
		// average phrase length : 35
		String choice = challengeCopy[random.nextInt(challengeCopy.length)];
		int length = 0;
		for (char c : choice.toCharArray()) {
			if (c != ' ') {
				length++;
			}
		}
		length /= 5;
		int startingWpm = 20;
		// The asymptote is 150wpm
		double wpm = Round((5.0 * budget + 46.0) / (budget + 50) * startingWpm, 2);
		double timeLimit = Round(length / (wpm / 60), 2);

		print("\nCopy Challenge!");
		print("\tTime Limit: " + timeLimit + " seconds @ " + wpm + " wpm");
		print("Copy the following phrase as quickly as you can! No mistakes!");
		WaitForEnter();
		print(choice, 0);
		double startTime = System.currentTimeMillis() / 1000.0;
		String answer = scanner.nextLine();
		double endTime = System.currentTimeMillis() / 1000.0;
		double elapse = Round(endTime - startTime, 2);
		print("\n");
		double uWpm = Round((length / elapse) * 60, 2);
		if (answer.trim().equals(choice.trim()) && endTime - startTime < timeLimit) {
			TinySound.loadSound("/sounds/effects/challenge_success.wav").play();
			print("Good job! (" + elapse + "/" + timeLimit + " @ " + uWpm + " wpm)");
			return true;
		} else if (endTime - startTime >= timeLimit) {
			TinySound.loadSound("/sounds/effects/challenge_failure.wav").play();
			print("Time out!");
			print(elapse + " >= " + timeLimit + " @ " + uWpm + " wpm");
		} else {
			TinySound.loadSound("/sounds/effects/challenge_failure.wav").play();
			print("Not quite! You didn't copy it exactly! " + uWpm + " wpm");
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
	private boolean ChallengeScramble(double budget, Random random) {
		int wordLen = 4;
		double timeLimit = 10;
		budget -= 4;
		for (int i = 0; i < 5; i++) {
			if (budget < 1) {
				break;
			}

			if (wordLen < 6 && random.nextDouble() > 0.90f) {
				wordLen++;
				budget--;
				timeLimit += 10;
			}
		}

		timeLimit -= ((5 * budget) / (budget + 10));
		if (timeLimit < 5) {
			timeLimit = 5;
		}
		timeLimit = Round(timeLimit, 2);

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

		print("\nScramble Challenge!");
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
			if (scrambled.length() != answer.length()) {
				matching = false;
			}
		}
		if (contains && matching && elapse < timeLimit) {
			TinySound.loadSound("/sounds/effects/challenge_success.wav").play();
			print("Good job! (" + elapse + "/" + timeLimit + ")");
			return true;
		} else if (contains && matching && elapse >= timeLimit) {
			TinySound.loadSound("/sounds/effects/challenge_failure.wav").play();
			print("Time out!");
			print(elapse + " >= " + timeLimit);
		} else {
			TinySound.loadSound("/sounds/effects/challenge_failure.wav").play();
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
	 * Prompts the player to enter a valid cardinal direction or character in order
	 * to move and moves the player there.
	 */
	private void PlayerMovePrompt() {
		Vector2 moveDir;
		print("Enter a cardinal direction ", 0, false);
		while (true) {
			print("(1, 2, 3, 4 or N, E, S, W): ", 0, false);
			String in = scanner.nextLine();
			int i = -1;
			try { // Try if the user inputted an integer
				i = Integer.parseInt(in) - 1;
				if (i < 0 || i > 3) {
					TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
					print("Enter a valid cardinal direction ", false);
					continue;
				}
			} catch (Exception e) {
				// Otherwise check to see if it was a string
				switch (in.toLowerCase()) {
					case "?":
						tutorial.ForcePrintTutorial(TutorialHandler.Tutorials.dungeonFloor);
						PrintKnownMap();
						PlayerMovePrompt();
						return;
					case "n":
					case "north":
						i = 0;
						break;
					case "e":
					case "east":
						i = 1;
						break;
					case "s":
					case "south":
						i = 2;
						break;
					case "w":
					case "west":
						i = 3;
						break;
					default:
						TinySound.loadSound("/sounds/effects/redeem_error.wav").play();
						print("Enter a valid cardinal direction ", false);
						continue;
				}
			}
			// Check to see if the move was valid

			moveDir = RelativeOffset(i).Sum(Player.GetPos());
			Room r = GetRoom(moveDir);
			if (r != null && r.IsVisitable()) {
				break;
			} else {
				TinySound.loadSound("/sounds/effects/walk_fail.wav").play();
				print("You cannot move into a solid wall!");
			}
		}
		TinySound.loadSound("/sounds/effects/walk.wav").play();
		// The move is valid, move there
		MoveTo(moveDir);
	}

	/**
	 * Forces the player to move to coord, and does the according action, along with
	 * revealing nearby grids.
	 * 
	 * @param coord The coord to move to
	 * @return Whether the move is legal or not
	 */
	private boolean MoveTo(Vector2 coord) {
		if (!InBounds(coord)) {
			return false;
		}
		Room room = GetRoom(coord);
		if (!room.IsVisitable()) {
			return false;
		}
		if (room.IsDoor()) {
			boolean flag = false;
			tutorial.PrintTutorial(TutorialHandler.Tutorials.lockedDoor);
			if (!Player.HasKey() && !Player.HasSkeletonKey()) {
				TinySound.loadSound("/sounds/effects/door_locked.wav").play();
				if (!room.IsVisited()) {
					print("There's a locked door with a large sign that says \"Exit\". You don't have the key.");
				} else {
					print("Yup, door's still locked.");
				}
			} else {
				if (Player.HasKey()) {
					print(
							"There's a locked door with a large sign that says \"Exit\". You have the matching key. Would you like to unlock the door?");
					if (IntInputList(new String[] { "Use key", "Don't use key" }, 0) == 1) {
						TinySound.loadSound("/sounds/effects/door_unlock.wav").play();
						print("The key you found on this floor fits into the door.");
						Player.SetHasKey(false);
						flag = true;
					}
				} else if (Player.HasSkeletonKey()) {
					print(
							"There's a locked door with a large sign that says \"Exit\".\nThe key you purchased seems different from before...");
					if (IntInputList(new String[] { "Use Skeleton key", "Don't use skeleton key" }) == 1) {
						TinySound.loadSound("/sounds/effects/skeleton_key.wav").play();
						WaitForMS(250);
						TinySound.loadSound("/sounds/effects/door_unlock.wav").play();
						print("It fits perfectly into the door, but breaks once you unlock it.");
						Player.SetSkeletonKey(false);
						Player.SetHasKey(false);
						flag = true;
					}
				}
				if (flag) {
					tutorial.PrintTutorial(TutorialHandler.Tutorials.boss);
					if (room.GetEnemy().GetName().equals("Giant Spider")) {
						bossMusic = TinySound.loadMusic("/sounds/music/battle_boss_spider.wav");
					} else {
						bossMusic = TinySound.loadMusic("/sounds/music/battle_boss.wav");
					}
					bossMusic.play(true);
					print("There's an enemy " + room.GetEnemy().GetName() + " defending the stairs!");
					status = Status.combat;
				}
			}
		} else if (room.HasKey()) {
			if (!Player.HasKey()) {
				Player.SetHasKey(true);
				TinySound.loadSound("/sounds/effects/key_pickup.wav").play();
				tutorial.PrintTutorial(TutorialHandler.Tutorials.key);
				print("Key collected!");
			}
		} else if (room.GetEnemy() != null) {
			fightMusic.play(true);
			tutorial.PrintTutorial(TutorialHandler.Tutorials.enemy);
			print("There's an enemy " + room.GetEnemy().GetName() + " here!");
			status = Status.combat;
		} else if (room.GetReduced()) {
			tutorial.PrintTutorial(TutorialHandler.Tutorials.reducedEnemy);
			print("You see the remains of what once was a monster.");
			room.SetReduced(false);
		}

		for (int i = 0; i < 4; i++) {
			Vector2 pPos = Vector2.Sum(coord, RelativeOffset(i));
			if (!InBounds(pPos)) {
				continue;
			}
			Room r = GetRoom(pPos);
			if (Player.HasTorch() || !r.IsVisitable()) {
				r.RevealRoom();
			}
		}

		Player.SetPos(room.GetCoordinates());
		room.RevealRoom();
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
		return new Vector2(dir == 0 || dir == 2 ? 0 : dir == 1 ? 1 : -1, dir == 1 || dir == 3 ? 0 : dir == 0 ? -1 : 1);
	}

	/**
	 * Whether a coordinate is in bounds of the map
	 * 
	 * @return Whether it is in bounds or not
	 */
	private boolean InBounds(int x, int y) {
		return InBounds(new Vector2(x, y));
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
	private boolean IsContinuous(Vector2 a, Vector2 b) {
		if (!InBounds(a) || !InBounds(b)) {
			return false;
		}
		ArrayList<Vector2> whitelist = new ArrayList<Vector2>();
		ArrayList<Vector2> blacklist = new ArrayList<Vector2>();

		whitelist.add(a);
		int limit = 1000;
		while (whitelist.size() > 0 && limit > 0) {
			Vector2 g = whitelist.get(0);
			if (g.equals(b)) {
				return true;
			}
			for (int i = 0; i < 4; i++) {
				Vector2 d = Vector2.Sum(g, RelativeOffset(i));
				if (InBounds(d) && !whitelist.contains(d) && !blacklist.contains(d) && GetRoom(d) != null
						&& GetRoom(d).IsVisitable()) {
					whitelist.add(d);
				}
			}
			blacklist.add(g);
			whitelist.remove(0);
			limit--;
		}
		if (limit == 0) {
			print("ERROR: Safety limit reached!", 0);
		}

		return false;
	}

	/**
	 * Returns a coordinate one grid closer to b (randomly choosing to move x or y)
	 * 
	 * @param a      The starting point
	 * @param b      The goal
	 * @param random The random generator used
	 * @return The new coordinate, one grid closer
	 */
	private Vector2 CoordTowards(Vector2 a, Vector2 b, Random random) {
		if (a.equals(b)) {
			return b;
		}
		Vector2 c = a;
		boolean needsX = a.x != b.x, needsY = a.y != b.y, isChangingX, needsIncrease;
		int from, to;
		if (needsX && needsY) {
			isChangingX = random.nextBoolean();
		} else {
			isChangingX = needsX ? true : false;
		}
		from = isChangingX ? a.x : a.y;
		to = isChangingX ? b.x : b.y;
		needsIncrease = from < to;
		return c.Sum(new Vector2(isChangingX ? needsIncrease ? 1 : -1 : 0, isChangingX ? 0 : needsIncrease ? 1 : -1));
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
		// TODO word wrap
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
			delay *= speedMod * messageSpeedModifier;
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
	 * Asks for the user to input a valid integer after printing a message. Will
	 * keep asking until provided.
	 * 
	 * @param message The string message to print before receiving input
	 * @return The valid number inputted by the user.
	 */
	private int IntInput(String message) {
		print(message, false);
		return IntInput();
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
		return IntInput(min, max, false);
	}

	/**
	 * Asks for the user to input a valid integer between min and max. Will keep
	 * asking until provided.
	 * 
	 * @param min       The minmium value the user is allowed to enter (inclusive)
	 * @param max       The maximum value the user is allowed to enter (inclusive)
	 * @param allowHelp Whether the user can enter ? and return -1
	 * @return The valid number inputted by the user,
	 */
	private int IntInput(int min, int max, boolean allowHelp) {
		int r;
		String input = "";
		while (true) {
			try {
				input = scanner.nextLine();
				r = Integer.parseInt(input);
				if (r >= min && r <= max) {
					return r;
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				if (allowHelp && input.equals("?")) {
					return -1;
				}
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
	 * Asks the user to input a valid integer after prompting them with a selection
	 * menu
	 * 
	 * @param choices The string list of choices
	 * @return The valid number inputted by the user
	 */
	private int IntInputList(String[] choices) {
		return IntInputList(choices, 1);
	}

	/**
	 * Asks the user to input a valid integer after prompting them with a selection
	 * menu
	 * 
	 * @param choices      The string list of choices
	 * @param messageSpeed The speed of the print messages
	 * @return The valid number inputted by the user
	 */
	private int IntInputList(String[] choices, int messageSpeed) {
		return IntInputList(choices, messageSpeed, false);
	}

	/**
	 * Asks the user to input a valid integer after prompting them with a selection
	 * menu
	 * 
	 * @param choices      The string list of choices
	 * @param messageSpeed The speed of the print messages
	 * @param allowHelp    Whether the user can enter ? and return -1
	 * @return The valid number inputted by the user
	 */
	private int IntInputList(String[] choices, int messageSpeed, boolean allowHelp) {
		for (int i = 0; i < choices.length; i++) {
			print((i + 1) + ") " + choices[i], messageSpeed);
		}
		return IntInput(1, choices.length, allowHelp);
	}

	/**
	 * Asks for the user to input a valid double between min and max. Will keep
	 * asking until provided. Does not allow inputting "?"
	 * 
	 * @param min The minmium value the user is allowed to enter (inclusive)
	 * @param max The maximum value the user is allowed to enter (inclusive)
	 * @return The valid number inputted by the user,
	 */
	private double DoubleInput(double min, double max) {
		double r;
		while (true) {
			try {
				r = Double.parseDouble(scanner.nextLine());
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
	 * Rounds a double to an int
	 * 
	 * @param input The unrounded double
	 * @return The rounded integer
	 */
	private int Round(double input) {
		return (int) Math.round(input);
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

	public static double GetDifficulty() {
		return difficulty;
	}

	private static void SetDifficulty(double diff) {
		difficulty = diff;
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

	private static String[] enemyName = { "Slime", "Orc", "Ogre", "Troll", "Wisp", "Killer Bunny", "Giant Spider",
			"Zombie", "Skeleton", "Flying Fish", "Giant Bee", "Swarm of Bees", "Baby Demon", "Giant Fly",
			"Swarm of horse flies", "Mimic", "Baby Dragon", "Fire Spirit", "Ice Elemental", "Fire Elemental",
			"Water Elemental", "Living Voodoo Doll", "Phantom", "Giant Mushroom", "Wandering Spirit", "Wasp", "Queen Bee",
			"Black Bear", "Polar Bear", "Brown Bear", "Wendigo", "Evil Plague Doctor", "Evil Fairy", "Nimbus", "Crocodile",
			"Alligator", "Giant Beetle", "Stone Golem", "Water Monster", "Ent", "Imp", "Swarm of poisonous baby spiders",
			"Zombie Horse", "Skeleton Horse", "Dark Knight", "Possessed Armor", "Wandering Eye", "Cthulhu's Servant",
			"Lunatic Cultist", "Demon", "Fallen Angel", "Giant Worm", "Elvish Archer", "Giant Snake", "Giant Dragonfly" };

	/**
	 * Creates an enemy with set stats and a random name
	 * 
	 * @param health   The amount of health to start with
	 * @param strength The difficulty of the enemy
	 */
	public Enemy(int health, int strength) {
		this(health, strength, new Random());
		System.out.println("Warning: no seed is being used!");
	}

	/**
	 * Creates an enemy with set stats and a random name
	 * 
	 * @param health   The amount of health to start with
	 * @param strength The difficulty of the enemy
	 * @param random   The random generator used
	 */
	public Enemy(int health, int strength, Random random) {
		super(health);
		this.strength = strength;
		this.name = enemyName[random.nextInt(enemyName.length)];
	}

	public int GetStrength() {
		return strength;
	}

	public void SetStrength(int strength) {
		this.strength = strength;
	}

	public String GetName() {
		return name;
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
	/** Whether the room had an enemy at one point, but was reduced */
	private boolean reduced = false;

	/**
	 * Creates an empty room
	 * 
	 * @param coordinates The coordinates associated to the room's position in the
	 *                    dungeon
	 */
	public Room(Vector2 coordinates) {
		this(coordinates, false, false, false);
	}

	public boolean GetReduced() {
		return reduced;
	}

	/**
	 * Marks the room as reduced or not
	 * 
	 * @param isReduced Whether the room was reduced
	 */
	public void SetReduced(boolean isReduced) {
		reduced = isReduced;
		enemy = null;
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
				return "-";
			} else if (Player.GetPos() != null && Player.GetPos().equals(coordinates)) {
				return "@";
			} else if (hasKey) {
				return "%";
			} else if (isDoor) {
				return "#";
			} else if (enemy != null) {
				return "!";
			}
			return " ";
		}
		return "?";
	}

	/**
	 * Sets the discovered state of the room to true.
	 */
	public void RevealRoom() {
		discovered = true;
	}

	/**
	 * Marks the room as visited, and removes the key if present
	 */
	public void VisitRoom() {
		visited = true;
		if (hasKey) {
			hasKey = false;
		}
	}

	/**
	 * Clears the enemy from the room
	 */
	public void ClearEnemy() {
		enemy = null;
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

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public Vector2 Sum(Vector2 v) {
		return Sum(this, v);
	}

	public int Distance(Vector2 v) {
		return Distance(this, v);
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

	public static int Distance(Vector2 a, Vector2 b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
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
	private double score = 0;
	/**
	 * Currency which can be exchanged for temporary upgrades that help the player
	 * on the next floor
	 */
	private int favors = 0;
	private Vector2 pos;
	/** Extends vision to 1 square */
	private boolean torch = false;
	/** Protects the player for one damage */
	private boolean secondWind = false;
	/** Allows unlocking a door without the key */
	private boolean skeletonKey = false;
	/** Starts the floor with the layout revealed */
	private boolean dungeonMap = false;
	/** Starts the floor with everything revealed */
	private boolean cursedMap = false;
	/** Whether the player as the key for the boss door */
	private boolean hasKey = false;

	/**
	 * Creates and assigns the singleton player
	 * 
	 * @param health The amount of health to start with
	 */
	public Player(int health) {
		super(health);
		player = this;
	}

	public static double GetScore() {
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

	public static void AddScore(double score) {
		player.score += score * CSE110HC.GetDifficulty();
	}

	public static void AddFavor() {
		AddFavors(1);
	}

	public static void AddFavors(int favors) {
		player.favors += favors;
	}

	public static boolean HasTorch() {
		return player.torch;
	}

	public static boolean HasSecondWind() {
		return player.secondWind;
	}

	public static boolean HasSkeletonKey() {
		return player.skeletonKey;
	}

	public static boolean HasDungeonMap() {
		return player.dungeonMap;
	}

	public static boolean HasCursedMap() {
		return player.cursedMap;
	}

	public static boolean HasKey() {
		return player.hasKey;
	}

	public static void SetTorch(boolean b) {
		player.torch = b;
	}

	public static void SetSecondWind(boolean state) {
		player.secondWind = state;
	}

	public static void SetSkeletonKey(boolean state) {
		player.skeletonKey = state;
	}

	public static void SetDungeonMap(boolean state) {
		player.dungeonMap = state;
	}

	public static void SetCursedMap(boolean b) {
		player.cursedMap = b;
	}

	public static void SetHasKey(boolean state) {
		player.hasKey = state;
	}

}

/** Handles displaying of tutorials when called in-game */
class TutorialHandler {
	public enum Tutorials {
		dungeonFloor, key, lockedDoor, enemy, boss, tavern, favor, nextFloor, reducedEnemy
	};

	private boolean[] tutorials;

	/**
	 * @param enabled Whether the tutorial system is enabled or not
	 */
	public TutorialHandler(boolean enabled) {
		tutorials = new boolean[Tutorials.values().length];
		for (int i = 0; i < tutorials.length; i++) {
			tutorials[i] = enabled;
		}
	}

	/**
	 * Prints the tutorial message of tutorial regardless of settings
	 * 
	 * @param tutorial The tutorial segment to display
	 */
	public void ForcePrintTutorial(Tutorials tutorial) {
		PrintTutorialText(tutorial);
	}

	/**
	 * Prints the tutorial message of tutorial if its true, setting its value to
	 * false
	 * 
	 * @param tutorial The tutorial segment to check and display
	 */
	public void PrintTutorial(Tutorials tutorial) {
		if (!CheckAndSet(tutorial)) {
			return;
		}
		PrintTutorialText(tutorial);
	}

	/**
	 * Prints the tutorial message of tutorial.
	 * 
	 * @param tutorial The tutorial segment to check and display
	 */
	private void PrintTutorialText(Tutorials tutorial) {
		switch (tutorial) {
			case dungeonFloor:
				CSE110HC.print(
						"Welcome to the dungeon!\nThe goal of the game is to get as deep in the dungeon as possible, accumulating the highest score you can. You get score by doing actions in the game: defeating monsters, discovering new floors, etc.\nThe start of every dungeon is the floor itself. Here's a key to explain what the symbols mean:\n\t@ = your location\n\t? = an unknown location\n\t! = an enemy location\n\t  = an explored location\n\t% = the key\n\t# = the locked door\n\t- = a solid wall\n\nBegin traversing the dungeon floor, find the key, and get to the door! Good luck!");
				break;
			case key:
				CSE110HC.print(
						"You found a key! There is one key per floor, these keys do not stack and do not carry over to the next floor. They key is able to unlock the locked door somewhere else in this dungeon...");
				break;
			case lockedDoor:
				CSE110HC.print(
						"You found the locked door! If you don't have the key, you should keep exploring. If you do have the key, you can unlock the door and exit the dungeon floor. However, be careful, there's always a strong enemy guarding the way home, and once you decide to fight it, there's no way back into the dungeon!");
				break;
			case enemy:
				CSE110HC.print(
						"You found an enemy! Combat in this game isn't standard \"hit for 2 damage\", instead it's skill and time based. You will be prompted with a problem (transcription, math, or unscrambling) that you will have to solve before time runs out. If you make a mistake or take too long, you take damage (and you can't heal!)\nSo, be careful, don't make mistakes, and keep calm...");
				break;
			case boss:
				CSE110HC.print(
						"So you decided to open the door! At the end of each floor is a boss fight, a super strong enemy which prevents you from getting back to the tavern. The boss fight works the same way as a standard enemy, only they're strong and have more difficult challenges. Good luck!");
				break;
			case tavern:
				CSE110HC.print(
						"You should take a break, you've earned it! Once you defeat the final boss fight, you return to the tavern where you can take a second and relax. As you complete dungeons, you'll receive favors, which you can redeem for goodies and treasures which will help you clear out even more dungeons. Take your time here, as once you leave the tavern, you're stuck in the dungeon until you defeat the next boss!");
				break;
			case favor:
				CSE110HC.print(
						"Items are one-time use: they will help you in your journey. They range from a torch to extend your vision to a magical key which can open any lock. You cannot purchase more than one of each item. They are powerful and essential in helping you get to the deeper depths of the dungeon...");
				break;
			case nextFloor:
				CSE110HC.print(
						"You've entered the next floor of the dungeon! As you get deeper, difficulty will increase... good luck!");
				break;
			case reducedEnemy:
				CSE110HC.print(
						"Occasionally, after hiring an extermination team, you may find their handiwork on the next floor. You should buy them a round when you return as thanks.");
				break;
		}
	}

	/**
	 * Sets the value of the tutorial to false, returns what it was before setting
	 * 
	 * @param tut The tutorial value to check
	 * @return Whether the tutorial was available before
	 */
	private boolean CheckAndSet(Tutorials tut) {
		boolean s = tutorials[tut.ordinal()];
		tutorials[tut.ordinal()] = false;
		return s;
	}
}