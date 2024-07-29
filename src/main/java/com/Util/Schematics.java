package com.Util;

import com.SelfHome.Variable;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import java.io.File;
import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.World;

public class Schematics {
  public static void loadSchematic(String name, World world, Location location) {
    boolean noAir = false;
    boolean entities = true;
    Vector position = new Vector(0, 0, 0);
    EditSession editSession = null;
    try {
      editSession = (new EditSessionBuilder(world.getName())).autoQueue(Boolean.valueOf(false)).build();
      System.out.println("·��:" + Variable.worldFinal + name);
      System.out.println("SESSION:" + editSession);
      SchematicFormat.getFormat(new File(String.valueOf(Variable.worldFinal) + name)).load(new File(String.valueOf(Variable.worldFinal) + name)).paste(editSession, position, noAir, entities);
    } catch (MaxChangedBlocksException e) {
      e.printStackTrace();
    } catch (DataException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (editSession != null)
        editSession.flushQueue(); 
    } 
  }
}
