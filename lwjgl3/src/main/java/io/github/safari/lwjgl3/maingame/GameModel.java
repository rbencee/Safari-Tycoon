    package io.github.safari.lwjgl3.maingame;

    import io.github.safari.lwjgl3.positionable.npc.animals.*;
    import io.github.safari.lwjgl3.positionable.npc.human.*;
    import io.github.safari.lwjgl3.positionable.objects.*;
    import io.github.safari.lwjgl3.positionable.Position;
    import io.github.safari.lwjgl3.positionable.npc.security.Security;
    import io.github.safari.lwjgl3.positionable.objects.Environment;
    import io.github.safari.lwjgl3.positionable.visitors.Jeep;
    import io.github.safari.lwjgl3.positionable.visitors.Tourist;
    import org.lwjgl.opengl.ARBStencilTexturing;

    import java.util.*;


    public class GameModel implements EdibleCollection{
        private int money;
        private int income;
        private int monthlyexpense;
        private int speed; // 1 - 3
        private int difficulty; // 1 - 3
        private int dayspassed;

        private int ticketprice;
        private int touristcount;
        private Position entrancepos;

        private ArrayList<Herd> herds;
        private ArrayList<Poacher> poachers;
        private ArrayList<Ranger> rangers;
        private ArrayList<Jeep> jeeps;
        private ArrayList<Security> securities;
        private ArrayList<Environment> environments;
        private ArrayList<Road> roads;
        private ArrayList<Tourist> tourists;


        ArrayList<Animal> allHerbivores = new ArrayList<>();
        ArrayList<HerbivoreEdible> allHerbivoreEdible = new ArrayList<>();
        ArrayList<Drinkable> allDrinkable = new ArrayList<>();


        @Override
        public List<HerbivoreEdible> getAllHerbivoreEdible() {
            return allHerbivoreEdible;
        }

        @Override
        public ArrayList<Animal> getAllHerbivores() {
            return allHerbivores;
        }

        @Override
        public ArrayList<Drinkable> getAllDrinkable() {
            return allDrinkable;
        }

        private int objectNumber = 50;
        private float mapWidth = 3200;
        private float mapHeight = 3200;
        private Random random;
        private float minDistance = 64;

        private float timeacc = 0;

        public GameModel(int difficulty)
        {
            this.difficulty = difficulty;
            this.random = new Random();
            this.herds = new ArrayList<>();
            this.rangers = new ArrayList<>();
            this.dayspassed = 0;
            this.income = 0;
            this.touristcount = 0;
            this.speed = 1;

            environments = new ArrayList<>();
            this.jeeps = new ArrayList<>();
            this.roads = new ArrayList<>();
            this.tourists = new ArrayList<>();
            this.money = 50000000;

            InitializeGame();
            AnimalFactory.gameModel = this;
        }

        //Setters and getters


        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getIncome() {
            return income;
        }

        public int getMoney() {
            return money;
        }

        public int getDayspassed() {
            return dayspassed;
        }

        public int getTouristcount() {
            return touristcount;
        }

        public ArrayList<Environment> getEnvironments() {return environments;}

        public ArrayList<Herd> getHerds() {return herds;}

        public ArrayList<Jeep> getJeeps() {return jeeps;}

        public ArrayList<Road> getRoads(){ return roads;}

        public ArrayList<Tourist> getTourists() {return tourists;}

        public void InitializeGame()
        {
            generateMap();
        }

        private void generateMap()
        {
            int objectCount = 0;
            while (objectCount < objectNumber) {
                float x = random.nextInt((int)(mapWidth / 32)) * 32;
                float y = random.nextInt((int)(mapHeight / 32)) * 32;
                int width = 96;
                int height = 110;

                if (positionFound(x, y, width, height)) {
                    Tree tree = new Tree(new Position(x, y, width, height));
                    environments.add(tree);
                    allHerbivoreEdible.add(tree);
                    objectCount++;
                }
            }

            objectCount = 0;
            while (objectCount < objectNumber) {
                float x = random.nextInt((int)(mapWidth / 32)) * 32;
                float y = random.nextInt((int)(mapHeight / 32)) * 32;
                int width = 64;
                int height = 64;

                if (positionFound(x, y, width, height)) {
                    Bush bush = new Bush(new Position(x, y, width, height));
                    environments.add(bush);
                    allHerbivoreEdible.add(bush);
                    objectCount++;
                }
            }

            objectCount = 0;
            while (objectCount < objectNumber) {
                float x = random.nextInt((int)(mapWidth / 32)) * 32;
                float y = random.nextInt((int)(mapHeight / 32)) * 32;
                int width = 96;
                int height = 96;

                if (positionFound(x, y, width, height)) {
                    Lake lake = new Lake(new Position(x, y, width, height));
                    environments.add(lake);
                    allDrinkable.add(lake);
                    objectCount++;
                }
            }

            objectCount = 0;
            while (objectCount < objectNumber) {
                float x = random.nextInt((int)(mapWidth / 32)) * 32;
                float y = random.nextInt((int)(mapHeight / 32)) * 32;
                int width = 64;
                int height = 64;


                if (positionFound(x, y, width, height)) {
                    Grass grass = new Grass(new Position(x, y, width, height));
                    environments.add(grass);
                    allHerbivoreEdible.add(grass);
                    objectCount++;
                }
            }


            //Belepo kilepo
            boolean entranceb = false;
            boolean exitb = false;

                while(!entranceb && !exitb) {

                    float gridSize = 64f;

                    float entranceY =(random.nextInt((int)(mapHeight / gridSize))) * gridSize;
                    float exitY = (random.nextInt((int)(mapHeight / gridSize))) * gridSize;



                    if(positionFound(0,entranceY,64,64)) {
                        Position entrapos = new Position(0,entranceY,64,64);
                        Road entrance = new Road(entrapos, 2);
                        this.entrancepos = entrapos;

                        roads.add(entrance);
                        //System.out.println("entrance:" + entrance.getRoadtype() + "   " + entrance.getPosition().getX() + "   " + entrance.getPosition().getY());
                        entranceb = true;
                    }

                    if(positionFound(mapWidth - 64,exitY,64,64)) {
                        Road exit = new Road(new Position(mapWidth - 64 , exitY, 64, 64), 3);
                        roads.add(exit);
                        exitb = true;
                    }
                }



        }

        public boolean positionFound(float x, float y, int width, int height) {
            if (x < 0 || y < 0 || x + width > mapWidth || y + height > mapHeight) {
                return false;
            }

            for (Environment environment : environments) {
                if (environment.getPosition() == null) continue;

                float envX = environment.getPosition().getX();
                float envY = environment.getPosition().getY();
                int envWidth = environment.getPosition().getWidth();
                int envHeight = environment.getPosition().getHeight();

                if (x + width > envX && x < envX + envWidth && y + height > envY && y < envY + envHeight) {
                    return false;
                }
            }


            for(Road road : roads)
            {
                Position pos = road.getPosition();

                if (Math.abs(pos.getX() - x) < 32 && Math.abs(pos.getY() - y) < 32) {
                    System.out.println("ROAD BLOCKED");
                    return false;
                }
            }



            return true;
        }

        public void Simulation(float delta)
        {
            int timeinterval = 1;

            if(speed == 2)
            {
                timeinterval = 7;
            }
            else if(speed == 3)
            {
                timeinterval = 30;
            }

            if(!isGameOver())
            {
                int previousDays = dayspassed;
                timeacc += delta;
                SummonTourist();

                if(timeacc >= 3.0f)
                {
                    dayspassed += timeinterval;
                    timeacc = 0;

                    if((dayspassed - previousDays) % 30 == 0) {
                        calculateIncome();
                    }
                }

                for(Jeep jeep : jeeps)
                {
                    Road roadtogo = getNextRoadTowardsEntrance(jeep, jeep.isTostart());
                    if(roadtogo != null) {
                        jeep.moveTowards(roadtogo.getPosition(), timeinterval);
                        //System.out.println("ROAD TO GO: " + roadtogo.getPosition());
                    }else
                    {
                        //System.out.println("ROAD TO GO NOT FOUND");
                    }

                }
            }

        }

        public void increasemoney(int money)
        {
            this.money += money;
        }

        public void calculateIncome()
        {
            this.money += touristcount * 5 + (sumUniqueAnimals() * sumAnimals() * 3) - payrangers();
            this.income = touristcount * 5 + (sumUniqueAnimals() * sumAnimals() * 3) - payrangers();

        }

        private void SummonTourist()
        {
            if (entrancepos == null) return;

            Random random = new Random();

            int offsetX = random.nextInt(65) - 32;
            int offsetY = random.nextInt(65) - 32;

            Position newPos = new Position(
                entrancepos.getX() + offsetX,
                entrancepos.getY() + offsetY,
                64, 64
            );


            if(touristcount < sumUniqueAnimals()) {
                this.touristcount = touristcount + 1;
                this.tourists.add(new Tourist(newPos));
            }

        }

        private int payrangers()
        {
            return rangers.size() * 50;

        }

        private boolean isGameOver()
        {
            return false;
        }

        public void ChangeTicketPrice(int ticketprice)
        {
            this.ticketprice = ticketprice;
        }

        private int sumAnimals()//Kell herdbe egy cucc, ammi visszaadja hogy hany allat van benne
        {
            int sum = 0;
            for (Herd herd : herds)
            {
                sum += herd.animalcount(); //Lehet meg kell nezni hogy biztos el e.
            }

            return sum;
        }

        private int sumUniqueAnimals()
        {
            Set<AnimalSpecies> uniqueAnimals = new HashSet<>();
            for (Herd herd : herds) {
                uniqueAnimals.add(herd.getAnimalSpecies());
            }

            return uniqueAnimals.size();
        }

        public int sumHerbivores(){
            int sum = 0;
            for (Herd herd : herds){
                if (herd.getAnimalSpecies().getAnimalType().equals(AnimalType.HERBIVORE)){
                    sum += herd.animalcount();
                }
            }
            return sum;
        }

        public int sumPredators(){
            int sum = 0;
            for (Herd herd : herds){
                if (herd.getAnimalSpecies().getAnimalType().equals(AnimalType.PREDATOR)){
                    sum += herd.animalcount();
                }
            }
            return sum;
        }





        public boolean CanBuy(ShopItem selectedItem)
        {
            if(money - selectedItem.getPrice() >= 0)
            {
                return true;
            }
            return false;
        }

        public void addtoenvironment(Environment environment)
        {
            this.environments.add(environment);
        }

        public void addtojeeps(Jeep jeep)
        {
            this.jeeps.add(jeep);
        }

        public void addtoroads(Road road)
        {
            this.roads.add(road);
        }

        public void Decrease_My_Money(int decrease_amount)
        {
            this.money -= decrease_amount;
        }



        public boolean Is_There_Road(float x, float y)
        {
            for(Road road : roads)
            {

                    if(Math.abs(road.getPosition().getX() - x) < 32 )
                        if(Math.abs(road.getPosition().getY() - y) < 32)
                        {
                            return true;
                        }

            }

            return false;

        }

        public Road getClosestRoad(Position pos) {
            Road closest = null;
            float minDist = Float.MAX_VALUE;

            for (Road road : roads) {
                float dx = pos.getX() - road.getPosition().getX();
                float dy = pos.getY() - road.getPosition().getY();
                float dist = dx * dx + dy * dy;

                if (dist < minDist) {
                    minDist = dist;
                    closest = road;
                }
            }

            return closest;
        }

        public Road getEntranceRoad() {
            for (Road road : roads) {
                if (road.getRoadtype() == 2) {
                    return road;
                }
            }
            return null;
        }

        public Road getExitRoad() {
            for (Road road : roads) {
                if (road.getRoadtype() == 3) {
                    return road;
                }
            }
            System.out.println("RETURNING NULL!");
            return null;
        }


        public List<Road> getAdjacentRoads(Road road) {
            List<Road> adjacent = new ArrayList<>();
            Position pos = road.getPosition();

            for (Road other : roads) {
                if (other == road) continue;

                Position otherPos = other.getPosition();

                float dx = Math.abs(pos.getX() - otherPos.getX());
                float dy = Math.abs(pos.getY() - otherPos.getY());

                if ((dx == 64 && dy == 0) || (dx == 0 && dy == 64)) {
                    adjacent.add(other);
                }
            }

            return adjacent;
        }


        public Road getNextRoadTowardsEntrance(Jeep jeep, boolean isentrancedestionation) {
            System.out.println("GOTO: " +  isentrancedestionation);
            Position startPos = jeep.getPosition();
            Road startRoad = getClosestRoad(startPos);
            Road entrance;
            if(isentrancedestionation) {
                entrance = getEntranceRoad();
            } else
            {
                entrance = getExitRoad();
            }


            if (startRoad == null || entrance == null) return null;
            System.out.println("Not triggered");


            if (startRoad.getPosition().equals(entrance.getPosition())) {
                if(isentrancedestionation)
                {
                    CheckForTourist_Here(jeep.getPosition(), jeep);
                } else
                {
                    this.touristcount -= jeep.Drop_Off_Tourists();
                }

                jeep.setTostart(!jeep.isTostart());
            }


            Map<Road, Road> cameFrom = new HashMap<>();
            Queue<Road> queue = new LinkedList<>();
            Set<Road> visited = new HashSet<>();

            queue.add(startRoad);
            visited.add(startRoad);
            cameFrom.put(startRoad, null);

            while (!queue.isEmpty()) {
                Road current = queue.poll();

                if (current.equals(entrance)) {

                    Road step = current;
                    Road prev = cameFrom.get(step);

                    while (prev != null && !prev.equals(startRoad)) {
                        step = prev;
                        prev = cameFrom.get(step);
                    }

                    return step;
                }

                for (Road neighbor : getAdjacentRoads(current)) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        cameFrom.put(neighbor, current);
                    }
                }
            }

            return null; // Nincs út
        }

        private void CheckForTourist_Here(Position position, Jeep jeep)
        {
            Iterator<Tourist> iterator = tourists.iterator();
            while (iterator.hasNext()) {
                Tourist t = iterator.next();
                if (jeep.trytoaddtourist(t)) {
                    iterator.remove();
                }
            }





        }

        /*
        private CheckInRange()
        {

        }
        */
    }
