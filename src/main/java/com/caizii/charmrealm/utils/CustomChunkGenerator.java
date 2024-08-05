package com.caizii.charmrealm.utils;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class CustomChunkGenerator extends ChunkGenerator {
  public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
    return createChunkData(world);
  }
}
