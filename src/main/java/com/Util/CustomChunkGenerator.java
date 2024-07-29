package com.Util;

import java.util.Random;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class CustomChunkGenerator extends ChunkGenerator {
  public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
    return createChunkData(world);
  }
}
