package com.caizii.charmrealm.utils;

import com.boydti.fawe.util.EditSessionBuilder;
import com.caizii.charmrealm.CharmRealm;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class Schematics {
  public static void loadSchematic(String name, World world, Location location) {
    boolean noAir = false;
    boolean entities = true;
    Vector position = new Vector(0, 0, 0);
    EditSession editSession = null;
    try {
      editSession = (new EditSessionBuilder(world.getName())).autoQueue(Boolean.valueOf(false)).build();
      System.out.println("·��:" + CharmRealm.pluginVariable.worldFinal + name);
      System.out.println("SESSION:" + editSession);
      SchematicFormat.getFormat(new File(String.valueOf(CharmRealm.pluginVariable.worldFinal) + name)).load(new File(String.valueOf(CharmRealm.pluginVariable.worldFinal) + name)).paste(editSession, position, noAir, entities);
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
