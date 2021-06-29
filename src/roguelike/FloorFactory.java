package roguelike;

import hardware.Random;

public class FloorFactory {
	private static int DIR_UP = 0;
	private static int DIR_RIGHT = 1;
	private static int DIR_DOWN = 2;
	private static int DIR_LEFT = 3;
	

	static Floor getRandomFloor() {
		//try making a room often, and only take ones that don't overlap. this is a value that should be played with.
		int roomCreationTries = Resources.MAX_FLOORFACTORY_ROOMTRIES;
		RoomCollection rooms = new RoomCollection();
		for (int i = 0; i < roomCreationTries; i++) {
			if(rooms.getCount()>=Resources.MAX_FLOORFACTORY_ROOMS) break;
			int posx = Random.rand(0, Resources.MAX_PLAYFIELD_WIDTH-1);
			int posy = Random.rand(0, Resources.MAX_PLAYFIELD_HEIGHT-1);
			int width = Random.rand(5, 26);
			int height = Random.rand(3, 10);
			//ensure values are in bounds
			if(posx+width>=Resources.MAX_PLAYFIELD_WIDTH) {
				posx -= (posx + width) - Resources.MAX_PLAYFIELD_WIDTH + 1;
			}
			if(posy+height>=Resources.MAX_PLAYFIELD_HEIGHT) {
				posy -= (posy + height) - Resources.MAX_PLAYFIELD_HEIGHT + 1;
			}
			Room newR = new SquareRoom(posx, posy, width, height);
			boolean overlap = false;
			for(Room r : rooms.getRooms()) {
				if(r==null) continue;
				if(roomsOverlap(newR, r)) {
					//rooms overlap
					overlap=true;
					break;
				}
			}
			//remove this debug
			newR.setSeen(true);
			if(!overlap)
				rooms.append(newR);
		}
		//generate basic floor object, at this point we only have rooms.
		Floor f = new Floor(rooms);
		
		/*
		//work on connections between rooms
		fillMaze(f);
		https://www.saschawillems.de/blog/2010/02/07/random-dungeon-generation/  */
		
		//TODO: backup plan, linien-basiert wege (langweilig)
		//ensureRoomConnections(f);
		return f;
	}
	
	private static void ensureRoomConnections(Floor f) {
		//select one random room as spawn room
		int cap = f.getRooms().getCount();
		int rIndex = Random.rand(0, cap-1);
		Room spawnRoom = f.getRooms().getRooms()[rIndex];
		//make bool array of which rooms we know are connected
		boolean[] connected = new boolean[cap];
		//set spawn room as true (connected to itself)
		connected[rIndex] = true;
		while(anyFalse(connected)) {
			//select room that is not yet connected
			int index = -1;
			for (int i = 0; i < cap; i++) {
				if(!connected[i]) index = i;
				break;
			}
			//find closest room that is already connected by using top-left coordinates
			Room roomToBeConnected = f.getRooms().getRooms()[index];
			int minDistance = 999999999;
			Room nearestRoom = null;
			for (Room r : f.getRooms().getRooms()) {
				int distance = roomToBeConnected.getTopLeft().distanceTo(r.getTopLeft());
				if(distance<minDistance) nearestRoom = r;
			}
			//now we have the nearest room, do the connection
			//connectRooms(roomToBeConnected, nearestRoom, f);
		}
	}
	
	private static void connectRooms(Room leftRoom, Room rightRoom, Floor f) {
		//we take a point on either left or right side of one room (preferrably whichever is closer to other room)
		//and a point on either up or down side of other room (again preferrably whichever is closer)
		//we then draw lines orthogonal to the axis of the side the point is on through the point
		// (so parallel to y axis if on top or bottom, or parallel to x axis if on left or right side)
		//then take another point which is one tile away from the side the original point was on, for both points
		//and find both intersections of axis-parallel lines going through the points.
		//hopefully, at least one of them should NOT go through a room
		
		
		//is leftRoom above or below rightRoom
		boolean lAboveR = leftRoom.getTopLeft().getPosy() < rightRoom.getTopLeft().getPosy();
		boolean lLeftOfR = leftRoom.getTopLeft().getPosx() < rightRoom.getTopLeft().getPosx();
		
		//todo: finish this part and draw connections
		if(!lAboveR && !lLeftOfR) { //right side on r, top side on l
			Coordinate lCoord = new Coordinate(leftRoom.getTopLeft().getPosx()+Random.rand(1, leftRoom.getWidth()-2), leftRoom.getTopLeft().getPosy()+1);
			Coordinate rCoord = new Coordinate(rightRoom.getTopRight().getPosx()+1, rightRoom.getTopRight().getPosy()+Random.rand(1,rightRoom.getHeight()-2));
			
		} else if(lAboveR && lLeftOfR) { //left side on r, bottom side on l
		
		} else if(lAboveR) { //right side on r, bottom side on l
		
		} else { //left side on r, top side on l
		
		}
	}
	
	private static boolean anyFalse(boolean[] bools) {
		for(boolean b : bools) if(!b) return false;
		return true;
	}
	
	
	
	private static final int OVERLAP_BORDER = 2;
	//https://developer.mozilla.org/en-US/docs/Games/Techniques/2D_collision_detection
	private static boolean roomsOverlap(Room r1, Room r2) {
		return     r1.getX()<r2.getX()+r2.getWidth()+OVERLAP_BORDER
				&& r1.getX()+r1.getWidth()+OVERLAP_BORDER>r2.getX()
				&& r1.getY()<r2.getY()+r2.getHeight()+OVERLAP_BORDER
				&& r1.getY()+r1.getHeight()+OVERLAP_BORDER>r2.getY();
	}
	
	
	/* this is all way too complicated
	static class CoordInformation {
		private boolean blockedByRoom;
		private boolean blockedByPath;
		private boolean visited;
	}
	
	private static void fillMaze(Floor f) {
		CoordInformation[][] blockMap = new CoordInformation[Resources.MAX_PLAYFIELD_HEIGHT][Resources.MAX_PLAYFIELD_WIDTH];
		Room[] rs = f.getRooms();
		for (int y = 0; y < Resources.MAX_PLAYFIELD_HEIGHT; y++) {
			for (int x = 0; x < Resources.MAX_PLAYFIELD_WIDTH; x++) {
				Coordinate c = new Coordinate(x,y);
				for (Room r : rs) {
					CoordInformation ci = new CoordInformation();
					if(r.containsCoordinate(c)) {
						ci.blockedByRoom = true;
						ci.visited = true;
					}
					blockMap[y][x] = ci;
				}
			}
		}
		
		//now we have a perfect 2d map of which tiles on the floor are blocked by rooms
		//get a random coordinate on from this blockMap where we are not yet blocked and recursively do the following:
		//mark curr cell as visited, and while the cell has unvisited neighbors:
		//choose one of the unvisited neighbors, mark it as path, call recursively on cell
		Coordinate startCoord = null;
		while(startCoord==null||blockMap[startCoord.getPosy()][startCoord.getPosx()].blockedByRoom) {
			startCoord = new Coordinate(Random.rand(0, Resources.MAX_PLAYFIELD_WIDTH-1), Random.rand(0, Resources.MAX_PLAYFIELD_HEIGHT-1));
		}
		blockMap[startCoord.getPosy()][startCoord.getPosx()].blockedByPath=true;
		iterativeMaze(blockMap, startCoord);
		//make coord list, get every coord that is blocked by a path, add to list and then to floor
		CoordinateList paths = new CoordinateList();
		for (int y = 0; y < Resources.MAX_PLAYFIELD_HEIGHT; y++) {
			for (int x = 0; x < Resources.MAX_PLAYFIELD_WIDTH; x++) {
				if(blockMap[y][x].blockedByPath) paths.add(new Coordinate(x,y));
			}
		}
		f.setPathTileCoords(paths);
	}
	
	static class CoordStack {
		private CoordWrapper tail = null;
		class CoordWrapper {
			private Coordinate c;
			private CoordWrapper prev;
			CoordWrapper(Coordinate c, CoordWrapper prev) {
				this.c = c;
				this.prev = prev;
			}
		}
		
		private boolean hasData() {
			return tail != null;
		}
		
		private void push(Coordinate c) {
			tail = new CoordWrapper(c, tail);
		}
		
		private Coordinate pop() {
			Coordinate retval = tail.c;
			tail = tail.prev;
			return retval;
		}
	}
	private static void iterativeMaze(CoordInformation[][] blockMap, Coordinate initialCell) {
		int lastDir = -1;
		//get a stack of coordinates
		CoordStack cs = new CoordStack();
		//mark init cell visited
		blockMap[initialCell.getPosy()][initialCell.getPosx()].visited = true;
		cs.push(initialCell);
		while(cs.hasData()) {
			Coordinate currCell = cs.pop();
			DirectionCollection neighbors = getUnblockedNeighbors(currCell, blockMap);
			if(!neighbors.isEmpty()) {
				Direction dir;
				if(neighbors.hasDir(lastDir)) { //TODO: add random factor here
					Serial.print("poggies\n");
					dir = neighbors.getDir(lastDir);
				} else {//pick random direction
					Serial.print("not poggies\n");
					int index = Random.rand(0, neighbors.getSize()-1);
					StringBuilder sb = new StringBuilder();
					sb.append("Index: ");
					sb.append(index);
					sb.append(" size: ");
					sb.append(neighbors.getSize());
					Serial.print(sb.getString());
					dir = neighbors.getDirs()[index];
					if(dir==null) Serial.print("WARUM SCHMECKT DER BURGER NICHT???");
				}
				if(dir==null) Serial.print("wallah der burger schmeckt nicht");
				
				//carve way with direction
				Serial.print("bing ");
				Serial.print(dir.c1.getPosy());
				Serial.print(' ');
				Serial.print(dir.c1.getPosx());
				Serial.print(' ');
				blockMap[dir.c1.getPosy()][dir.c1.getPosx()].blockedByPath = true;
				Serial.print("bang ");
				blockMap[dir.c2.getPosy()][dir.c2.getPosx()].blockedByPath = true;
				Serial.print("pong\n");
			}
		}
	}
	//http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/
	//TODO: https://github.com/munificent/hauberk/blob/db360d9efa714efb6d937c31953ef849c7394a39/lib/src/content/dungeon.dart#L102
	//need another fucking linked list holy shit
	static class Direction {
		int dir;
		Coordinate c1, c2;
		Direction(int dir, Coordinate c1, Coordinate c2) {
			this.dir = dir;
			this.c1 = c1;
			this.c2 = c2;
		}
		
		boolean isFacingDir(int dir) {
			return this.dir==dir;
		}
	}
	static class DirectionCollection {
		static private class DirectionWrapper {
			private Direction dir;
			private DirectionWrapper next;
			DirectionWrapper(Direction dir) {
				this.dir = dir;
			}
		}
		private DirectionWrapper head = null;
		private int size = 0;
		
		void addDirection(Direction dir) {
			DirectionWrapper dw = new DirectionWrapper(dir);
			if(head!=null) {
				DirectionWrapper i = head;
				while(i.next!=null) i = i.next;
				i.next = dw;
			} else {
				head = dw;
			}
			size++;
		}
		
		Direction getDirAtIndex(int i) {
			if(i>size-1) return null;
			DirectionWrapper retval = head;
			for (int j = 0; j < i; j++) {
				retval = retval.next;
			}
			return retval.dir;
		}
		
		int getSize() {
			return size;
		}
		
		boolean isEmpty() {
			return getSize()==0;
		}
		
		Direction[] getDirs() {
			Direction[] retval = new Direction[size];
			DirectionWrapper dw = head;
			int i = 0;
			while(dw!=null) {
				if(dw.dir==null) MAGIC.inline(0xCC);
				retval[i] = dw.dir;
				dw = dw.next;
				i++;
			}
			return retval;
		}
		
		boolean hasDir(int dir) {
			DirectionWrapper dw = head;
			while(dw!=null) {
				if(dw.dir.isFacingDir(dir)) return true;
				dw = dw.next;
			}
			return false;
		}
		
		Direction getDir(int dir) {
			DirectionWrapper dw = head;
			while(dw!=null) {
				if(dw.dir.isFacingDir(dir)) return dw.dir;
				dw = dw.next;
			}
			return null;
		}
	}
	private static DirectionCollection getUnblockedNeighbors(Coordinate c, CoordInformation[][] blockMap) {
		DirectionCollection dc = new DirectionCollection();
		if(c.getPosy()- 3 >= 0 && !blockMap[c.getPosy()- 2][c.getPosx()].blockedByRoom)
			dc.addDirection(new Direction(DIR_UP, new Coordinate(c.getPosx(), c.getPosy()-1), new Coordinate(c.getPosx(), c.getPosy()-2)));
		
		if(c.getPosy()+ 3 < Resources.MAX_PLAYFIELD_HEIGHT && !blockMap[c.getPosy()+2][c.getPosx()].blockedByRoom)
			dc.addDirection(new Direction(DIR_DOWN, new Coordinate(c.getPosx(), c.getPosy()+1), new Coordinate(c.getPosx(), c.getPosy()+2)));
		
		if(c.getPosx()-3 >= 0 && !blockMap[c.getPosy()][c.getPosx()-2].blockedByRoom)
			dc.addDirection(new Direction(DIR_LEFT, new Coordinate(c.getPosx()-1, c.getPosy()), new Coordinate(c.getPosx()-2, c.getPosy())));
		
		if(c.getPosx()+3 < Resources.MAX_PLAYFIELD_WIDTH && !blockMap[c.getPosy()][c.getPosx()+2].blockedByRoom)
			dc.addDirection(new Direction(DIR_RIGHT, new Coordinate(c.getPosx()+1, c.getPosy()), new Coordinate(c.getPosx()+2, c.getPosy())));
		return dc;
	}*/
	
	
	//private static
}
