package world;

import game.physical.Appearance;
import world.blueprint.BlueprintFeature;
import world.blueprint.CrawlerStyle;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public enum Biome implements BlueprintFeature {

  GRASSLAND(8, new Appearance('G', new Color(7, 140, 0), new Color(8, 96, 0)),

      new BiomeTerrain("GRASS", 30, CrawlerStyle.LONG_BLOB,
          new BiomeProp("TREE", 0.02)),
      new BiomeTerrain("DIRT", 3, CrawlerStyle.LUMPY_ROPE,
          new BiomeProp("TREE", 0.01)),
      new BiomeTerrain("ROCK", 1, CrawlerStyle.AMOEBA,
          new BiomeProp("BOULDER", 0.002),
          new BiomeProp("STONE", 0.0001))),

  FOREST(4, new Appearance('F', new Color(28, 82, 0), new Color(3, 33, 0)),

      new BiomeTerrain("GRASS", 30, CrawlerStyle.LONG_BLOB,
          new BiomeProp("TREE", 0.12),
          new BiomeProp("UNDERGROWTH", 0.12)),
      new BiomeTerrain("DIRT", 5, CrawlerStyle.TRAILS,
          new BiomeProp("TREE", 0.03)),
      new BiomeTerrain("ROCK", 1, CrawlerStyle.AMOEBA,
          new BiomeProp("BOULDER", 0.001),
          new BiomeProp("STONE", 0.0001))),

  CRAGS(2, new Appearance('C', new Color(205, 205, 205), new Color(85, 85, 85)),

      new BiomeTerrain("GRASS", 1, CrawlerStyle.AMOEBA,
          new BiomeProp("TREE", 0.004)),
      new BiomeTerrain("DIRT", 3, CrawlerStyle.TRAILS,
          new BiomeProp("TREE", 0.002)),
      new BiomeTerrain("ROCK", 16, CrawlerStyle.LONG_BLOB,
          new BiomeProp("BOULDER", 0.15),
          new BiomeProp("STONE", 0.05))),

  DESERT(1, new Appearance('D', new Color(255, 204, 0), new Color(114, 90, 0)),

      new BiomeTerrain("SAND", 20, CrawlerStyle.TRAILS,
          new BiomeProp("DUNE", 0.6),
          new BiomeProp("CACTUS", 0.005)),
      new BiomeTerrain("SANDSTONE", 1, CrawlerStyle.AMOEBA,
          new BiomeProp("BOULDER_SANDSTONE", 0.003),
          new BiomeProp("CACTUS", 0.002))),

  BADLANDS(11, new Appearance('B', new Color(144, 71, 0), new Color(76, 14, 0)),

      new BiomeTerrain("DIRT", 8, CrawlerStyle.TRAILS),
      new BiomeTerrain("SAND", 1, CrawlerStyle.LONG_BLOB,
          new BiomeProp("CACTUS", 0.001)),
      new BiomeTerrain("SANDSTONE", 6, CrawlerStyle.AMOEBA,
          new BiomeProp("BOULDER_SANDSTONE", 0.05),
          new BiomeProp("CACTUS", 0.004))),

  SWAMP(1, new Appearance('S', new Color(71, 0, 63), new Color(25, 0, 48)),

      new BiomeTerrain("MARSH", 1, CrawlerStyle.LONG_BLOB,
          new BiomeProp("TREE_SWAMP", 0.02),
          new BiomeProp("SHARKWEED", 0.25)),
      new BiomeTerrain("MUCK", 4, CrawlerStyle.TRAILS,
          new BiomeProp("TREE_SWAMP", 0.02),
          new BiomeProp("UNDERGROWTH", 0.1),
          new BiomeProp("OOZE", 0.004)));

  public final Appearance worldMapAppearance;
  final int weight;
  final Set<BiomeTerrain> biomeTerrain;


  Biome(int weight, Appearance worldMapAppearance, BiomeTerrain... biomeTerrain) {
    this.weight = weight;
    this.worldMapAppearance = worldMapAppearance;
    this.biomeTerrain = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(biomeTerrain)));
  }


  public static Set<Biome> getAll() {
    return new HashSet<>(Arrays.asList(values()));
  }


  @Override
  public int getWeight() {
    return weight;
  }


  public Set<BiomeTerrain> getBiomeTerrain() {
    return biomeTerrain;
  }

}