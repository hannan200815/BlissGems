package dev.xoperr.blissgems;

import dev.xoperr.blissgems.a.a;
import dev.xoperr.blissgems.a.b;
import dev.xoperr.blissgems.a.c;
import dev.xoperr.blissgems.a.d;
import dev.xoperr.blissgems.a.e;
import dev.xoperr.blissgems.a.f;
import dev.xoperr.blissgems.a.g;
import dev.xoperr.blissgems.a.h;
import dev.xoperr.blissgems.api.BlissGemsAPI;
import dev.xoperr.blissgems.api.CooldownEntry;
import dev.xoperr.blissgems.api.GemDefinition;
import dev.xoperr.blissgems.commands.FixHeartsCommand;
import dev.xoperr.blissgems.libs.faststats.bukkit.BukkitMetrics;
import dev.xoperr.blissgems.libs.faststats.core.Metrics;
import dev.xoperr.blissgems.libs.faststats.core.data.Metric;
import dev.xoperr.blissgems.listeners.AutoEnchantListener;
import dev.xoperr.blissgems.listeners.ComprehensiveGemProtectionListener;
import dev.xoperr.blissgems.listeners.KillTrackingListener;
import dev.xoperr.blissgems.listeners.PlayerDeathListener;
import dev.xoperr.blissgems.listeners.PlayerJoinListener;
import dev.xoperr.blissgems.listeners.RepairKitListener;
import dev.xoperr.blissgems.listeners.ReviveBeaconListener;
import dev.xoperr.blissgems.listeners.StunListener;
import dev.xoperr.blissgems.listeners.SwapHandAbilityListener;
import dev.xoperr.blissgems.listeners.TeleportListener;
import dev.xoperr.blissgems.listeners.UpgraderListener;
import dev.xoperr.blissgems.listeners.VillagerTradeListener;
import dev.xoperr.blissgems.managers.AbilityBindingManager;
import dev.xoperr.blissgems.managers.AbilityManager;
import dev.xoperr.blissgems.managers.AchievementManager;
import dev.xoperr.blissgems.managers.ClickActivationManager;
import dev.xoperr.blissgems.managers.CooldownDisplayManager;
import dev.xoperr.blissgems.managers.CriticalHitManager;
import dev.xoperr.blissgems.managers.EnergyManager;
import dev.xoperr.blissgems.managers.FlowStateManager;
import dev.xoperr.blissgems.managers.GemManager;
import dev.xoperr.blissgems.managers.GemRegistryImpl;
import dev.xoperr.blissgems.managers.GemRitualManager;
import dev.xoperr.blissgems.managers.PassiveManager;
import dev.xoperr.blissgems.managers.PluginMessagingManager;
import dev.xoperr.blissgems.managers.RecipeManager;
import dev.xoperr.blissgems.managers.RepairKitManager;
import dev.xoperr.blissgems.managers.ReviveBeaconManager;
import dev.xoperr.blissgems.managers.SoulManager;
import dev.xoperr.blissgems.managers.StatsManager;
import dev.xoperr.blissgems.managers.TrustedPlayersManager;
import dev.xoperr.blissgems.utils.ConfigManager;
import dev.xoperr.blissgems.utils.CustomItemManager;
import dev.xoperr.blissgems.utils.GemType;
import java.io.File;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import dev.xoperr.blissgems.BlissGems;
import dev.xoperr.blissgems.api.GemAbilityHandler;
import dev.xoperr.blissgems.utils.AbilityBinding;
import dev.xoperr.blissgems.utils.AbilitySlot;
import dev.xoperr.blissgems.utils.Achievement;
import dev.xoperr.blissgems.utils.EnergyState;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.ItemStack;

import dev.xoperr.blissgems.utils.ParticleUtils;
import java.util.HashMap;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BundleMeta;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;


public class BlissGems extends JavaPlugin implements BlissGemsAPI {
   private ConfigManager configManager;
   private BlissCommand blissCommand;
   private GemRegistryImpl gemRegistry;
   private EnergyManager energyManager;
   private GemManager gemManager;
   private AbilityManager abilityManager;
   private AbilityBindingManager abilityBindingManager;
   private PassiveManager passiveManager;
   private ClickActivationManager clickActivationManager;
   private TrustedPlayersManager trustedPlayersManager;
   private CooldownDisplayManager cooldownDisplayManager;
   private EnhancedGuiManager enhancedGuiManager;
   private StatsManager statsManager;
   private RecipeManager recipeManager;
   private RepairKitManager repairKitManager;
   private ReviveBeaconManager reviveBeaconManager;
   private SoulManager soulManager;
   private FlowStateManager flowStateManager;
   private CriticalHitManager criticalHitManager;
   private PluginMessagingManager pluginMessagingManager;
   private a astraAbilities;
   private b fireAbilities;
   private c fluxAbilities;
   private d lifeAbilities;
   private e puffAbilities;
   private f speedAbilities;
   private g strengthAbilities;
   private h wealthAbilities;
   private dev.xoperr.blissgems.core.b.c protectionManager;
   private dev.xoperr.blissgems.core.b.b particleManager;
   private dev.xoperr.blissgems.core.b.e textManager;
   private dev.xoperr.blissgems.core.b.a autoEnchantManager;
   private dev.xoperr.blissgems.core.b.d regionManager;
   private AchievementManager achievementManager;
   private GemRitualManager gemRitualManager;
   private Metrics metrics;

   public void onEnable() {
      this.saveDefaultConfig();

      try {
         CustomItemManager.initialize(this);
      } catch (Exception var41) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: CustomItemManager ===");
         this.getLogger().severe(var41.getMessage());
         var41.printStackTrace();
      }

      try {
         this.protectionManager = new dev.xoperr.blissgems.core.b.c(this);
      } catch (Exception var40) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ProtectionManager ===");
         this.getLogger().severe(var40.getMessage());
         var40.printStackTrace();
      }

      try {
         this.particleManager = new dev.xoperr.blissgems.core.b.b(this);
      } catch (Exception var39) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ParticleManager ===");
         this.getLogger().severe(var39.getMessage());
         var39.printStackTrace();
      }

      try {
         this.textManager = new dev.xoperr.blissgems.core.b.e(this);
      } catch (Exception var38) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: TextManager ===");
         this.getLogger().severe(var38.getMessage());
         var38.printStackTrace();
      }

      try {
         this.autoEnchantManager = new dev.xoperr.blissgems.core.b.a(this);
      } catch (Exception var37) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: AutoEnchantManager ===");
         this.getLogger().severe(var37.getMessage());
         var37.printStackTrace();
      }

      try {
         this.regionManager = new dev.xoperr.blissgems.core.b.d(this);
      } catch (Exception var36) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: RegionManager ===");
         this.getLogger().severe(var36.getMessage());
         var36.printStackTrace();
      }

      try {
         dev.xoperr.blissgems.core.a.c.a.a(this.protectionManager);
         dev.xoperr.blissgems.core.a.b.b.a(this.particleManager);
         dev.xoperr.blissgems.core.a.e.a.a(this.textManager);
         dev.xoperr.blissgems.core.a.a.a.a(this.autoEnchantManager);
         dev.xoperr.blissgems.core.a.d.a.a(this.regionManager);
      } catch (Exception var35) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: Core APIs ===");
         this.getLogger().severe(var35.getMessage());
         var35.printStackTrace();
      }

      try {
         this.configManager = new ConfigManager(this);
      } catch (Exception var34) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ConfigManager ===");
         this.getLogger().severe(var34.getMessage());
         var34.printStackTrace();
      }

      try {
         this.energyManager = new EnergyManager(this);
      } catch (Exception var33) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: EnergyManager ===");
         this.getLogger().severe(var33.getMessage());
         var33.printStackTrace();
      }

      try {
         this.gemManager = new GemManager(this);
      } catch (Exception var32) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: GemManager ===");
         this.getLogger().severe(var32.getMessage());
         var32.printStackTrace();
      }

      try {
         this.abilityManager = new AbilityManager(this);
      } catch (Exception var31) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: AbilityManager ===");
         this.getLogger().severe(var31.getMessage());
         var31.printStackTrace();
      }

      try {
         this.abilityBindingManager = new AbilityBindingManager(this);
      } catch (Exception var30) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: AbilityBindingManager ===");
         this.getLogger().severe(var30.getMessage());
         var30.printStackTrace();
      }

      try {
         this.passiveManager = new PassiveManager(this);
      } catch (Exception var29) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: PassiveManager ===");
         this.getLogger().severe(var29.getMessage());
         var29.printStackTrace();
      }

      try {
         this.clickActivationManager = new ClickActivationManager(this);
      } catch (Exception var28) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ClickActivationManager ===");
         this.getLogger().severe(var28.getMessage());
         var28.printStackTrace();
      }

      try {
         this.trustedPlayersManager = new TrustedPlayersManager(this);
      } catch (Exception var27) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: TrustedPlayersManager ===");
         this.getLogger().severe(var27.getMessage());
         var27.printStackTrace();
      }

      try {
         this.repairKitManager = new RepairKitManager(this);
      } catch (Exception var26) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: RepairKitManager ===");
         this.getLogger().severe(var26.getMessage());
         var26.printStackTrace();
      }

      try {
         this.reviveBeaconManager = new ReviveBeaconManager(this);
      } catch (Exception var25) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ReviveBeaconManager ===");
         this.getLogger().severe(var25.getMessage());
         var25.printStackTrace();
      }

      try {
         this.soulManager = new SoulManager(this);
      } catch (Exception var24) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: SoulManager ===");
         this.getLogger().severe(var24.getMessage());
         var24.printStackTrace();
      }

      try {
         this.flowStateManager = new FlowStateManager(this);
      } catch (Exception var23) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: FlowStateManager ===");
         this.getLogger().severe(var23.getMessage());
         var23.printStackTrace();
      }

      try {
         this.criticalHitManager = new CriticalHitManager(this);
      } catch (Exception var22) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: CriticalHitManager ===");
         this.getLogger().severe(var22.getMessage());
         var22.printStackTrace();
      }

      try {
         this.pluginMessagingManager = new PluginMessagingManager(this);
      } catch (Exception var21) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: PluginMessagingManager ===");
         this.getLogger().severe(var21.getMessage());
         var21.printStackTrace();
      }

      try {
         this.astraAbilities = new a(this);
      } catch (Exception var20) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: AstraAbilities ===");
         this.getLogger().severe(var20.getMessage());
         var20.printStackTrace();
      }

      try {
         this.fireAbilities = new b(this);
      } catch (Exception var19) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: FireAbilities ===");
         this.getLogger().severe(var19.getMessage());
         var19.printStackTrace();
      }

      try {
         this.fluxAbilities = new c(this);
      } catch (Exception var18) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: FluxAbilities ===");
         this.getLogger().severe(var18.getMessage());
         var18.printStackTrace();
      }

      try {
         this.lifeAbilities = new d(this);
      } catch (Exception var17) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: LifeAbilities ===");
         this.getLogger().severe(var17.getMessage());
         var17.printStackTrace();
      }

      try {
         this.puffAbilities = new e(this);
      } catch (Exception var16) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: PuffAbilities ===");
         this.getLogger().severe(var16.getMessage());
         var16.printStackTrace();
      }

      try {
         this.speedAbilities = new f(this);
      } catch (Exception var15) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: SpeedAbilities ===");
         this.getLogger().severe(var15.getMessage());
         var15.printStackTrace();
      }

      try {
         this.strengthAbilities = new g(this);
      } catch (Exception var14) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: StrengthAbilities ===");
         this.getLogger().severe(var14.getMessage());
         var14.printStackTrace();
      }

      try {
         this.wealthAbilities = new h(this);
      } catch (Exception var13) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: WealthAbilities ===");
         this.getLogger().severe(var13.getMessage());
         var13.printStackTrace();
      }

      try {
         this.gemRegistry = new GemRegistryImpl(this);
         this.registerBuiltInGems();
         this.getServer().getServicesManager().register(BlissGemsAPI.class, this, this, ServicePriority.Normal);
         this.getLogger().info("BlissGems Addon API registered via ServicesManager");
      } catch (Exception var12) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: GemRegistry/API ===");
         this.getLogger().severe(var12.getMessage());
         var12.printStackTrace();
      }

      try {
         this.cooldownDisplayManager = new CooldownDisplayManager(this);
      } catch (Exception var11) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: CooldownDisplayManager ===");
         this.getLogger().severe(var11.getMessage());
         var11.printStackTrace();
      }

      try {
         this.statsManager = new StatsManager(this);
      } catch (Exception var10) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: StatsManager ===");
         this.getLogger().severe(var10.getMessage());
         var10.printStackTrace();
      }

      try {
         this.achievementManager = new AchievementManager(this);
      } catch (Exception var9) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: AchievementManager ===");
         this.getLogger().severe(var9.getMessage());
         var9.printStackTrace();
      }

      try {
         this.enhancedGuiManager = new EnhancedGuiManager(this);
      } catch (Exception var8) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: EnhancedGuiManager ===");
         this.getLogger().severe(var8.getMessage());
         var8.printStackTrace();
      }

      try {
         this.recipeManager = new RecipeManager(this);
         this.gemRitualManager = new GemRitualManager(this);
      } catch (Exception var7) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: RecipeManager/GemRitualManager ===");
         this.getLogger().severe(var7.getMessage());
         var7.printStackTrace();
      }

      try {
         if (this.recipeManager != null) {
            this.recipeManager.registerRecipes();
         }
      } catch (Exception var6) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: Recipe Registration ===");
         this.getLogger().severe(var6.getMessage());
         var6.printStackTrace();
      }

      try {
         this.checkAutoStartSmp();
      } catch (Exception var5) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: SMP Auto-Start Check ===");
         this.getLogger().severe(var5.getMessage());
         var5.printStackTrace();
      }

      try {
         this.registerListeners();
      } catch (Exception var4) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: Listener Registration ===");
         this.getLogger().severe(var4.getMessage());
         var4.printStackTrace();
      }

      try {
         this.registerCommands();
      } catch (Exception var3) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: Command Registration ===");
         this.getLogger().severe(var3.getMessage());
         var3.printStackTrace();
      }

      if (this.getConfig().getBoolean("send-anonymous-metrics", true)) {
         try {
            this.metrics = ((BukkitMetrics.Factory)((BukkitMetrics.Factory)BukkitMetrics.factory().token("33b82f6ed2f61ee4be22345da22fbf24")).addMetric(Metric.number("active_gem_players", () -> {
               int var1 = 0;

               for(Player var3 : this.getServer().getOnlinePlayers()) {
                  if (this.gemManager != null && this.gemManager.hasGemInOffhand(var3)) {
                     ++var1;
                  }
               }

               return var1;
            }))).create((Plugin)this);
            this.metrics.ready();
         } catch (Exception var2) {
            this.getLogger().warning("FastStats metrics failed to initialize: " + var2.getMessage());
         }
      }

      this.getLogger().info("BlissGems has been enabled!");
      this.getLogger().info("Version: " + this.getDescription().getVersion());
      this.getLogger().info("Using custom item system with vanilla Minecraft items");
   }

   public void onDisable() {
      if (this.particleManager != null) {
         this.particleManager.c();
      }

      if (this.textManager != null) {
         this.textManager.a();
      }

      if (this.autoEnchantManager != null) {
         this.autoEnchantManager.b();
      }

      if (this.cooldownDisplayManager != null) {
         this.cooldownDisplayManager.stop();
      }

      if (this.repairKitManager != null) {
         this.repairKitManager.cleanup();
      }

      if (this.reviveBeaconManager != null) {
         this.reviveBeaconManager.cleanup();
      }

      if (this.pluginMessagingManager != null) {
         this.pluginMessagingManager.shutdown();
      }

      if (this.recipeManager != null) {
         this.recipeManager.unregisterRecipes();
      }

      for(Player var2 : Bukkit.getOnlinePlayers()) {
         if (this.astraAbilities != null) {
            this.astraAbilities.cleanup(var2);
         }

         if (this.fireAbilities != null) {
            this.fireAbilities.cleanup(var2);
         }

         if (this.fluxAbilities != null) {
            this.fluxAbilities.cleanup(var2);
         }

         if (this.strengthAbilities != null) {
            this.strengthAbilities.cleanup(var2);
         }

         if (this.lifeAbilities != null) {
            this.lifeAbilities.cleanup(var2);
         }
      }

      if (this.metrics != null) {
         this.metrics.shutdown();
      }

      this.energyManager.saveAll();
      if (this.wealthAbilities != null) {
         this.wealthAbilities.a();
      }

      if (this.abilityManager != null) {
         this.abilityManager.saveAllCooldowns();
      }

      if (this.achievementManager != null) {
         this.achievementManager.saveAll();
      }

      this.getLogger().info("BlissGems has been disabled!");
   }

   private void checkAutoStartSmp() {
      if (this.configManager != null && !this.configManager.isSmpStarted()) {
         File var1 = new File(this.getDataFolder(), "playerdata");
         if (var1.exists() && var1.isDirectory()) {
            int var2 = this.configManager.getSmpAutoStartThreshold();
            int var3 = 0;
            File[] var4 = var1.listFiles((var0, var1x) -> var1x.endsWith(".yml"));
            if (var4 != null) {
               for(File var8 : var4) {
                  YamlConfiguration var9 = YamlConfiguration.loadConfiguration(var8);
                  if (((FileConfiguration)var9).getBoolean("received-first-gem", false)) {
                     ++var3;
                     if (var3 >= var2) {
                        this.configManager.setSmpStarted(true);
                        this.getLogger().info("SMP auto-started! Found " + var3 + " players with gems (threshold: " + var2 + ").");
                        return;
                     }
                  }
               }

            }
         }
      }
   }

   private void registerListeners() {
      this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
      this.getServer().getPluginManager().registerEvents(new ComprehensiveGemProtectionListener(this), this);
      this.getServer().getPluginManager().registerEvents(new GemInteractListener(this), this);
      this.getServer().getPluginManager().registerEvents(new UpgraderListener(this), this);
      this.getServer().getPluginManager().registerEvents(new PassiveListener(this), this);
      this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
      this.getServer().getPluginManager().registerEvents(new AutoEnchantListener(this), this);
      this.getServer().getPluginManager().registerEvents(new StunListener(this), this);
      this.getServer().getPluginManager().registerEvents(new RepairKitListener(this), this);
      this.getServer().getPluginManager().registerEvents(new ReviveBeaconListener(this), this);
      this.getServer().getPluginManager().registerEvents(new KillTrackingListener(this), this);
      this.getServer().getPluginManager().registerEvents(new TeleportListener(this), this);
      this.getServer().getPluginManager().registerEvents(new VillagerTradeListener(this), this);
      this.getServer().getPluginManager().registerEvents(new SwapHandAbilityListener(this), this);
      this.getServer().getPluginManager().registerEvents(this.enhancedGuiManager, this);
   }

   private void registerCommands() {
      this.blissCommand = new BlissCommand(this);
      this.getCommand("bliss").setExecutor(this.blissCommand);
      this.getCommand("bliss").setTabCompleter(this.blissCommand);
      FixHeartsCommand var1 = new FixHeartsCommand(this);
      if (this.getCommand("fixhearts") != null) {
         this.getCommand("fixhearts").setExecutor(var1);
         this.getCommand("fixhearts").setTabCompleter(var1);
      }

   }

   public BlissCommand getBlissCommand() {
      return this.blissCommand;
   }

   public ConfigManager getConfigManager() {
      return this.configManager;
   }

   public EnergyManager getEnergyManager() {
      return this.energyManager;
   }

   public GemManager getGemManager() {
      return this.gemManager;
   }

   public AbilityManager getAbilityManager() {
      return this.abilityManager;
   }

   public AbilityBindingManager getAbilityBindingManager() {
      return this.abilityBindingManager;
   }

   public PassiveManager getPassiveManager() {
      return this.passiveManager;
   }

   public RecipeManager getRecipeManager() {
      return this.recipeManager;
   }

   public GemRitualManager getGemRitualManager() {
      return this.gemRitualManager;
   }

   public a getAstraAbilities() {
      return this.astraAbilities;
   }

   public b getFireAbilities() {
      return this.fireAbilities;
   }

   public c getFluxAbilities() {
      return this.fluxAbilities;
   }

   public d getLifeAbilities() {
      return this.lifeAbilities;
   }

   public e getPuffAbilities() {
      return this.puffAbilities;
   }

   public f getSpeedAbilities() {
      return this.speedAbilities;
   }

   public g getStrengthAbilities() {
      return this.strengthAbilities;
   }

   public h getWealthAbilities() {
      return this.wealthAbilities;
   }

   public RepairKitManager getRepairKitManager() {
      return this.repairKitManager;
   }

   public ReviveBeaconManager getReviveBeaconManager() {
      return this.reviveBeaconManager;
   }

   public SoulManager getSoulManager() {
      return this.soulManager;
   }

   public FlowStateManager getFlowStateManager() {
      return this.flowStateManager;
   }

   public CriticalHitManager getCriticalHitManager() {
      return this.criticalHitManager;
   }

   public ClickActivationManager getClickActivationManager() {
      return this.clickActivationManager;
   }

   public TrustedPlayersManager getTrustedPlayersManager() {
      return this.trustedPlayersManager;
   }

   public EnhancedGuiManager getEnhancedGuiManager() {
      return this.enhancedGuiManager;
   }

   public StatsManager getStatsManager() {
      return this.statsManager;
   }

   public AchievementManager getAchievementManager() {
      return this.achievementManager;
   }

   public PluginMessagingManager getPluginMessagingManager() {
      return this.pluginMessagingManager;
   }

   public dev.xoperr.blissgems.core.b.c getProtectionManager() {
      return this.protectionManager;
   }

   public dev.xoperr.blissgems.core.b.b getParticleManager() {
      return this.particleManager;
   }

   public dev.xoperr.blissgems.core.b.e getTextManager() {
      return this.textManager;
   }

   public dev.xoperr.blissgems.core.b.a getAutoEnchantManager() {
      return this.autoEnchantManager;
   }

   public dev.xoperr.blissgems.core.b.d getRegionManager() {
      return this.regionManager;
   }

   public GemRegistryImpl getGemRegistry() {
      return this.gemRegistry;
   }

   public boolean playerHasGem(Player var1, String var2) {
      GemManager.ActiveGem var3 = this.gemManager.getActiveGem(var1);
      return var3 == null ? false : var2.equals(var3.getGemId());
   }

   private void registerBuiltInGems() {
      for(GemType var4 : GemType.values()) {
         GemDefinition var5 = (new GemDefinition.Builder(var4.getId())).displayName(var4.getDisplayName()).description(var4.getDescription()).color(var4.getColor()).plugin("BlissGems").maxTier(2).build();
         this.gemRegistry.registerGem(var5);
      }

      if (this.astraAbilities != null) {
         this.gemRegistry.registerAbilities("astra", this.astraAbilities);
      }

      if (this.fireAbilities != null) {
         this.gemRegistry.registerAbilities("fire", this.fireAbilities);
      }

      if (this.fluxAbilities != null) {
         this.gemRegistry.registerAbilities("flux", this.fluxAbilities);
      }

      if (this.lifeAbilities != null) {
         this.gemRegistry.registerAbilities("life", this.lifeAbilities);
      }

      if (this.puffAbilities != null) {
         this.gemRegistry.registerAbilities("puff", this.puffAbilities);
      }

      if (this.speedAbilities != null) {
         this.gemRegistry.registerAbilities("speed", this.speedAbilities);
      }

      if (this.strengthAbilities != null) {
         this.gemRegistry.registerAbilities("strength", this.strengthAbilities);
      }

      if (this.wealthAbilities != null) {
         this.gemRegistry.registerAbilities("wealth", this.wealthAbilities);
      }

      if (this.passiveManager != null) {
         this.passiveManager.registerBuiltInHandlers(this.gemRegistry);
      }

      this.gemRegistry.registerCooldowns("astra", List.of(new CooldownEntry("astra-daggers", "Daggers"), new CooldownEntry("astra-projection", "Projection")));
      this.gemRegistry.registerCooldowns("fire", List.of(new CooldownEntry("fire-fireball", "Fireball"), new CooldownEntry("fire-campfire", "Campfire"), new CooldownEntry("fire-crisp", "Crisp"), new CooldownEntry("fire-meteor-shower", "Meteor")));
      this.gemRegistry.registerCooldowns("flux", List.of(new CooldownEntry("flux-beam", "Beam"), new CooldownEntry("flux-ground", "Ground"), new CooldownEntry("flux-flashbang", "Flash"), new CooldownEntry("flux-kinetic-burst", "Kinetic")));
      this.gemRegistry.registerCooldowns("life", List.of(new CooldownEntry("life-drainer", "Drainer"), new CooldownEntry("life-circle-of-life", "Circle"), new CooldownEntry("life-vitality-vortex", "Vortex"), new CooldownEntry("life-heart-lock", "Lock")));
      this.gemRegistry.registerCooldowns("puff", List.of(new CooldownEntry("puff-dash", "Dash"), new CooldownEntry("puff-breezy-bash", "Bash"), new CooldownEntry("puff-group-bash", "Group")));
      this.gemRegistry.registerCooldowns("speed", List.of(new CooldownEntry("speed-blur", "Blur"), new CooldownEntry("speed-storm", "Storm"), new CooldownEntry("speed-terminal", "Terminal")));
      this.gemRegistry.registerCooldowns("strength", List.of(new CooldownEntry("strength-nullify", "Nullify"), new CooldownEntry("strength-frailer", "Frailer"), new CooldownEntry("strength-shadow-stalker", "Stalker")));
      this.gemRegistry.registerCooldowns("wealth", List.of(new CooldownEntry("wealth-unfortunate", "Unfortunate"), new CooldownEntry("wealth-rich-rush", "Rush"), new CooldownEntry("wealth-item-lock", "Lock"), new CooldownEntry("wealth-amplification", "Amplify")));
   }


    // ===== Merged from separate source file: BlissCommand.java =====

    // ===== Merged from separate source file: BlissCommand.java =====
    public static class BlissCommand implements CommandExecutor, TabCompleter {
       private final BlissGems plugin;

       public BlissCommand(BlissGems var1) {
          this.plugin = var1;
       }

       public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
          if (var4.length == 0) {
             if (var1 instanceof Player) {
                this.plugin.getEnhancedGuiManager().openMainMenu((Player)var1);
             } else {
                this.sendHelp(var1);
             }

             return true;
          } else {
             switch (var4[0].toLowerCase()) {
                case "give":
                   this.handleGive(var1, var4);
                   break;
                case "reroll":
                   this.handleReroll(var1, var4);
                   break;
                case "giveitem":
                   this.handleGiveItem(var1, var4);
                   break;
                case "energy":
                   this.handleEnergy(var1, var4);
                   break;
                case "withdraw":
                   this.handleWithdraw(var1, var4);
                   break;
                case "info":
                   this.handleInfo(var1, var4);
                   break;
                case "reload":
                   this.handleReload(var1, var4);
                   break;
                case "pockets":
                   this.handlePockets(var1, var4);
                   break;
                case "amplify":
                   this.handleAmplify(var1, var4);
                   break;
                case "toggle_click":
                   this.handleToggleClick(var1, var4);
                   break;
                case "ability:main":
                   this.handleAbilityMain(var1, var4);
                   break;
                case "ability:secondary":
                   this.handleAbilitySecondary(var1, var4);
                   break;
                case "ability:tertiary":
                   this.handleAbilityTertiary(var1, var4);
                   break;
                case "ability:quaternary":
                   this.handleAbilityQuaternary(var1, var4);
                   break;
                case "trust":
                   this.handleTrust(var1, var4);
                   break;
                case "untrust":
                   this.handleUntrust(var1, var4);
                   break;
                case "trusted":
                   this.handleTrustedList(var1, var4);
                   break;
                case "bannable":
                   this.handleBannable(var1, var4);
                   break;
                case "autosmelt":
                   this.handleAutoSmelt(var1, var4);
                   break;
                case "stats":
                   this.handleStats(var1, var4);
                   break;
                case "release":
                   this.handleReleaseSouls(var1, var4);
                   break;
                case "souls":
                   this.handleSoulsInfo(var1, var4);
                   break;
                case "achievements":
                   this.handleAchievements(var1, var4);
                   break;
                case "normalise":
                case "normalize":
                   this.handleNormalise(var1, var4);
                   break;
                case "smp":
                   this.handleSmp(var1, var4);
                   break;
                case "clearcds":
                   this.handleClearCooldowns(var1, var4);
                   break;
                case "ability":
                   this.handleAbilityBindingsList(var1, var4);
                   break;
                case "set_ability":
                   this.handleSetAbility(var1, var4);
                   break;
                default:
                   this.sendHelp(var1);
             }

             return true;
          }
       }

       private void handleGive(CommandSender var1, String[] var2) {
          if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else if (var2.length < 3) {
             var1.sendMessage("§cUsage: /bliss give <player> <gem_type> [tier]");
          } else {
             Player var3 = Bukkit.getPlayer(var2[1]);
             if (var3 == null) {
                var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("player-not-found"));
             } else {
                String var4 = var2[2].toLowerCase();
                GemType var5 = null;
                String var6 = null;
                String var7 = null;

                for(GemType var11 : GemType.values()) {
                   if (var11.getId().equalsIgnoreCase(var4) || var11.getDisplayName().equalsIgnoreCase(var4)) {
                      var5 = var11;
                      var6 = var11.getId();
                      var7 = var11.getDisplayName();
                      break;
                   }
                }

                if (var5 == null) {
                   GemRegistryImpl var13 = this.plugin.getGemRegistry();
                   if (var13 != null) {
                      GemDefinition var15 = var13.getGem(var4);
                      if (var15 != null) {
                         var6 = var15.getId();
                         var7 = var15.getDisplayName();
                      }
                   }
                }

                if (var6 == null) {
                   var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("invalid-gem-type"));
                } else {
                   int var14 = 1;
                   if (var2.length >= 4) {
                      try {
                         var14 = Integer.parseInt(var2[3]);
                         if (var14 < 1 || var14 > 2) {
                            var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("invalid-tier"));
                            return;
                         }
                      } catch (NumberFormatException var12) {
                         var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("invalid-tier"));
                         return;
                      }
                   }

                   for(int var16 = 0; var16 < var3.getInventory().getSize(); ++var16) {
                      ItemStack var17 = var3.getInventory().getItem(var16);
                      if (var17 != null) {
                         String var18 = CustomItemManager.getIdByItem(var17);
                         if (this.plugin.getGemManager().isAnyGem(var18)) {
                            var3.getInventory().setItem(var16, (ItemStack)null);
                         }
                      }
                   }

                   if (this.plugin.getGemManager().giveGem(var3, var6, var14)) {
                      var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("gem-given", "player", var3.getName(), "gem", var7, "tier", var14));
                   } else {
                      var1.sendMessage("§cFailed to give gem!");
                   }

                }
             }
          }
       }

       private void handleReroll(CommandSender var1, String[] var2) {
          if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else if (var2.length < 2) {
             var1.sendMessage("§cUsage: /bliss reroll <player> [tier]");
          } else {
             Player var3 = Bukkit.getPlayer(var2[1]);
             if (var3 == null) {
                var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("player-not-found"));
             } else {
                ArrayList var4 = new ArrayList();

                for(GemType var8 : GemType.values()) {
                   if (this.plugin.getConfig().getBoolean("gems.enabled." + var8.getId(), true)) {
                      var4.add(var8);
                   }
                }

                if (var4.isEmpty()) {
                   var1.sendMessage("§cNo gems are enabled in the config!");
                } else {
                   GemType var11 = (GemType)var4.get((new Random()).nextInt(var4.size()));
                   int var12 = 1;
                   if (var2.length >= 3) {
                      try {
                         var12 = Integer.parseInt(var2[2]);
                         if (var12 < 1 || var12 > 2) {
                            var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("invalid-tier"));
                            return;
                         }
                      } catch (NumberFormatException var10) {
                         var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("invalid-tier"));
                         return;
                      }
                   }

                   for(int var13 = 0; var13 < var3.getInventory().getSize(); ++var13) {
                      ItemStack var14 = var3.getInventory().getItem(var13);
                      if (var14 != null) {
                         String var9 = CustomItemManager.getIdByItem(var14);
                         if (this.plugin.getGemManager().isAnyGem(var9)) {
                            var3.getInventory().setItem(var13, (ItemStack)null);
                         }
                      }
                   }

                   var1.sendMessage("§d§lInitiating gem ritual for " + var3.getName() + "...");
                   var3.sendMessage("§d§l§nGEM REROLL RITUAL");
                   var3.sendMessage("§7§oThe ancient powers are choosing your fate...");
                   this.plugin.getGemRitualManager().performGemRitual(var3, var11, false, var12);
                   this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                      if (this.plugin.getGemManager().giveGem(var3, var11, var12)) {
                         String var5 = this.plugin.getConfigManager().getFormattedMessage("gem-rerolled", "player", var3.getName(), "gem", var11.getDisplayName(), "tier", var12);
                         if (var5 != null && !var5.isEmpty()) {
                            var1.sendMessage(var5);
                         } else {
                            String var10001 = var3.getName();
                            var1.sendMessage("§aRerolled " + var10001 + "'s gem to " + var11.getColor() + var11.getDisplayName() + " §a(Tier " + var12 + ")!");
                         }

                         String var6 = this.plugin.getConfigManager().getFormattedMessage("gem-rerolled-received", "gem", var11.getDisplayName(), "tier", var12);
                         if (var6 != null && !var6.isEmpty()) {
                            var3.sendMessage(var6);
                         } else {
                            String var7 = var11.getColor();
                            var3.sendMessage("§d§l» §fYour gem has been chosen: " + var7 + "§l" + var11.getDisplayName() + " §f(Tier " + var12 + ")§d§l «");
                         }
                      } else {
                         var1.sendMessage("§cFailed to reroll gem!");
                      }

                   }, 20L);
                }
             }
          }
       }

       private void handleGiveItem(CommandSender var1, String[] var2) {
          if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else if (var2.length < 3) {
             var1.sendMessage("§cUsage: /bliss giveitem <player> <item_id> [amount]");
             var1.sendMessage("§7Available items:");
             var1.sendMessage("§b  - energy_bottle");
             var1.sendMessage("§b  - repair_kit");
             var1.sendMessage("§b  - gem_trader");
             var1.sendMessage("§b  - gem_fragment");
             var1.sendMessage("§b  - gem_upgrader §7(universal - works for all gems)");
          } else {
             Player var3 = Bukkit.getPlayer(var2[1]);
             if (var3 == null) {
                var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("player-not-found"));
             } else {
                String var4 = var2[2].toLowerCase();
                int var5 = 1;
                if (var2.length >= 4) {
                   try {
                      var5 = Integer.parseInt(var2[3]);
                      if (var5 < 1 || var5 > 64) {
                         var1.sendMessage("§cAmount must be between 1 and 64!");
                         return;
                      }
                   } catch (NumberFormatException var8) {
                      var1.sendMessage("§cInvalid amount!");
                      return;
                   }
                }

                ItemStack var6 = CustomItemManager.getItemById(var4);
                if (var6 == null) {
                   var1.sendMessage("§cInvalid item ID: " + var4);
                   var1.sendMessage("§7Available items: energy_bottle, repair_kit, gem_trader, gem_fragment, gem_upgrader");
                } else {
                   var6.setAmount(var5);
                   var3.getInventory().addItem(new ItemStack[]{var6});
                   String var7 = var6.getItemMeta() != null ? var6.getItemMeta().getDisplayName() : var4;
                   var1.sendMessage("§aGave " + var5 + "x " + var7 + " §ato " + var3.getName() + "!");
                   var3.sendMessage("§aYou received " + var5 + "x " + var7 + "§a!");
                }
             }
          }
       }

       private void handleEnergy(CommandSender var1, String[] var2) {
          if (var2.length == 1) {
             if (!(var1 instanceof Player)) {
                var1.sendMessage("§cOnly players can check their energy!");
             } else {
                Player var10 = (Player)var1;
                int var11 = this.plugin.getEnergyManager().getEnergy(var10);
                EnergyState var12 = EnergyState.fromEnergy(var11);
                String var6 = this.getEnergyBar(var11);
                this.plugin.getConfigManager().sendFormattedMessage(var10, "energy-info-header");
                this.plugin.getConfigManager().sendFormattedMessage(var10, "energy-info-line1", "energyBar", var6, "energy", var11);
                this.plugin.getConfigManager().sendFormattedMessage(var10, "energy-info-line2", "state", var12.getDisplayName());
             }
          } else if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else if (var2.length < 4) {
             var1.sendMessage("§cUsage: /bliss energy <player> <set/add/remove> <amount>");
          } else {
             Player var4 = Bukkit.getPlayer(var2[1]);
             if (var4 == null) {
                var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("player-not-found"));
             } else {
                String var5 = var2[2].toLowerCase();

                int var3;
                try {
                   var3 = Integer.parseInt(var2[3]);
                } catch (NumberFormatException var9) {
                   var1.sendMessage("§cInvalid amount!");
                   return;
                }

                switch (var5) {
                   case "set":
                      this.plugin.getEnergyManager().setEnergy(var4, var3);
                      var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("energy-set", "player", var4.getName(), "amount", var3));
                      break;
                   case "add":
                      this.plugin.getEnergyManager().addEnergy(var4, var3);
                      var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("energy-added", "player", var4.getName(), "amount", var3));
                      break;
                   case "remove":
                      int var8 = this.plugin.getEnergyManager().getEnergy(var4);
                      if (var8 <= 0) {
                         var1.sendMessage("§c" + var4.getName() + " already has 0 energy!");
                      } else {
                         this.plugin.getEnergyManager().removeEnergy(var4, var3);
                         var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("energy-removed", "player", var4.getName(), "amount", var3));
                      }
                      break;
                   default:
                      var1.sendMessage("§cUsage: /bliss energy <player> <set/add/remove> <amount>");
                }

             }
          }
       }

       private String getEnergyBar(int var1) {
          StringBuilder var2 = new StringBuilder();

          for(int var3 = 1; var3 <= 10; ++var3) {
             if (var3 <= var1) {
                var2.append("§a█");
             } else {
                var2.append("§7█");
             }
          }

          return var2.toString();
       }

       private void handleWithdraw(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             int var4 = this.plugin.getEnergyManager().getEnergy(var3);
             if (var4 <= 1) {
                this.plugin.getConfigManager().sendFormattedMessage(var3, "not-enough-energy");
             } else {
                this.plugin.getEnergyManager().removeEnergy(var3, 1);
                ItemStack var5 = CustomItemManager.getItemById("energy_bottle");
                if (var5 != null) {
                   var3.getInventory().addItem(new ItemStack[]{var5});
                   this.plugin.getConfigManager().sendFormattedMessage(var3, "energy-withdrawn");
                } else {
                   this.plugin.getConfigManager().sendFormattedMessage(var3, "energy-bottle-failed");
                }

             }
          }
       }

       private void handleInfo(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else if (!this.plugin.getGemManager().hasActiveGem(var3)) {
             this.plugin.getConfigManager().sendFormattedMessage(var3, "no-active-gem");
          } else {
             GemType var4 = this.plugin.getGemManager().getGemType(var3);
             int var5 = this.plugin.getGemManager().getGemTier(var3);
             int var6 = this.plugin.getEnergyManager().getEnergy(var3);
             EnergyState var7 = this.plugin.getEnergyManager().getEnergyState(var3);
             this.plugin.getConfigManager().sendFormattedMessage(var3, "gem-info", "gem", var4.getDisplayName(), "tier", var5, "energy", var6, "state", var7.getDisplayName());
          }
       }

       private void handleReload(CommandSender var1, String[] var2) {
          if (!var1.hasPermission("blissgems.admin")) {
             this.plugin.getConfigManager().sendFormattedMessage(var1, "no-permission");
          } else {
             this.plugin.getConfigManager().reload();
             this.plugin.getConfigManager().sendFormattedMessage(var1, "config-reloaded");
          }
       }

       private void handlePockets(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             GemType var4 = this.plugin.getGemManager().getGemType(var3);
             if (var4 != GemType.WEALTH) {
                this.plugin.getConfigManager().sendFormattedMessage(var3, "requires-wealth-gem-pockets");
             } else {
                int var5 = this.plugin.getGemManager().getGemTier(var3);
                if (var5 < 2) {
                   this.plugin.getConfigManager().sendFormattedMessage(var3, "requires-wealth-t2-pockets");
                } else {
                   this.plugin.getWealthAbilities().a(var3);
                }
             }
          }
       }

       private void handleAmplify(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             GemType var4 = this.plugin.getGemManager().getGemType(var3);
             if (var4 != GemType.WEALTH) {
                var3.sendMessage("§cYou need a Wealth gem to use Amplification!");
             } else {
                int var5 = this.plugin.getGemManager().getGemTier(var3);
                if (var5 < 2) {
                   var3.sendMessage("§cAmplification requires Tier 2 Wealth gem!");
                } else {
                   this.plugin.getWealthAbilities().e(var3);
                }
             }
          }
       }

       private void handleToggleClick(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             boolean var4 = this.plugin.getClickActivationManager().toggleClickActivation(var3);
             if (var4) {
                this.plugin.getConfigManager().sendFormattedMessage(var3, "click-activation-enabled");
             } else {
                this.plugin.getConfigManager().sendFormattedMessage(var3, "click-activation-disabled");
             }

          }
       }

       private String findGemInHand(Player var1) {
          ItemStack var2 = var1.getInventory().getItemInMainHand();
          ItemStack var3 = var1.getInventory().getItemInOffHand();
          GemRegistryImpl var4 = this.plugin.getGemRegistry();
          String var5 = CustomItemManager.getIdByItem(var2);
          if (var5 == null || !GemType.isGem(var5) && (var4 == null || !var4.isRegisteredGem(var5))) {
             var5 = CustomItemManager.getIdByItem(var3);
             return var5 == null || !GemType.isGem(var5) && (var4 == null || !var4.isRegisteredGem(var5)) ? null : var5;
          } else {
             return var5;
          }
       }

       private void handleAbilityMain(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             String var4 = this.findGemInHand(var3);
             if (var4 == null) {
                this.plugin.getConfigManager().sendFormattedMessage(var3, "must-hold-gem");
             } else {
                int var5 = this.plugin.getEnergyManager().getEnergy(var3);
                if (var5 <= 0) {
                   this.plugin.getConfigManager().sendFormattedMessage(var3, "no-energy");
                } else {
                   GemRegistryImpl var6 = this.plugin.getGemRegistry();
                   int var7 = var6 != null ? var6.tierFromItemId(var4) : (var4.endsWith("_gem_t2") ? 2 : 1);
                   GemType var8 = GemType.fromOraxenId(var4);
                   if (var8 != null) {
                      switch (var8) {
                         case ASTRA -> this.plugin.getAstraAbilities().g(var3);
                         case FIRE -> this.plugin.getFireAbilities().d(var3);
                         case FLUX -> this.plugin.getFluxAbilities().onPrimary(var3, var7);
                         case LIFE -> this.plugin.getLifeAbilities().a(var3);
                         case PUFF -> this.plugin.getPuffAbilities().c(var3);
                         case SPEED -> this.plugin.getSpeedAbilities().a(var3, var7);
                         case STRENGTH -> this.plugin.getStrengthAbilities().a(var3);
                         case WEALTH -> this.plugin.getWealthAbilities().b(var3);
                      }

                   } else {
                      if (var6 != null) {
                         String var9 = var6.gemIdFromItemId(var4);
                         GemAbilityHandler var10 = var9 != null ? var6.getAbilityHandler(var9) : null;
                         if (var10 != null) {
                            var10.onPrimary(var3, var7);
                         }
                      }

                   }
                }
             }
          }
       }

       private void handleAbilitySecondary(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             String var4 = this.findGemInHand(var3);
             if (var4 == null) {
                this.plugin.getConfigManager().sendFormattedMessage(var3, "must-hold-gem");
             } else {
                GemRegistryImpl var5 = this.plugin.getGemRegistry();
                int var6 = var5 != null ? var5.tierFromItemId(var4) : (var4.endsWith("_gem_t2") ? 2 : 1);
                if (var6 < 2) {
                   String var11 = this.plugin.getConfigManager().getFormattedMessage("requires-tier2");
                   if (var11 != null && !var11.isEmpty()) {
                      var3.sendMessage(var11);
                   }

                } else {
                   int var7 = this.plugin.getEnergyManager().getEnergy(var3);
                   if (var7 <= 0) {
                      String var12 = this.plugin.getConfigManager().getFormattedMessage("no-energy");
                      if (var12 != null && !var12.isEmpty()) {
                         var3.sendMessage(var12);
                      }

                   } else {
                      GemType var8 = GemType.fromOraxenId(var4);
                      if (var8 != null) {
                         switch (var8) {
                            case ASTRA -> this.plugin.getAstraAbilities().h(var3);
                            case FIRE -> this.plugin.getFireAbilities().f(var3);
                            case FLUX -> this.plugin.getFluxAbilities().e(var3);
                            case LIFE -> this.plugin.getLifeAbilities().b(var3);
                            case PUFF -> this.plugin.getPuffAbilities().d(var3);
                            case SPEED -> this.plugin.getSpeedAbilities().d(var3);
                            case STRENGTH -> this.plugin.getStrengthAbilities().b(var3);
                            case WEALTH -> this.plugin.getWealthAbilities().d(var3);
                         }

                      } else {
                         if (var5 != null) {
                            String var9 = var5.gemIdFromItemId(var4);
                            GemAbilityHandler var10 = var9 != null ? var5.getAbilityHandler(var9) : null;
                            if (var10 != null) {
                               var10.onSecondary(var3, var6);
                            }
                         }

                      }
                   }
                }
             }
          }
       }

       public void triggerPrimary(Player var1) {
          this.handleAbilityMain(var1, new String[0]);
       }

       public void triggerSecondary(Player var1) {
          this.handleAbilitySecondary(var1, new String[0]);
       }

       public void triggerTertiary(Player var1) {
          this.handleAbilityTertiary(var1, new String[0]);
       }

       public void triggerQuaternary(Player var1) {
          this.handleAbilityQuaternary(var1, new String[0]);
       }

       public boolean triggerSlot(Player var1, AbilitySlot var2) {
          if (var2 == null) {
             return false;
          } else {
             switch (var2) {
                case PRIMARY:
                   this.triggerPrimary(var1);
                   return true;
                case SECONDARY:
                   this.triggerSecondary(var1);
                   return true;
                case TERTIARY:
                   this.triggerTertiary(var1);
                   return true;
                case QUATERNARY:
                   this.triggerQuaternary(var1);
                   return true;
                default:
                   return false;
             }
          }
       }

       private void handleAbilityTertiary(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             String var4 = this.findGemInHand(var3);
             if (var4 == null) {
                String var11 = this.plugin.getConfigManager().getFormattedMessage("must-hold-gem");
                if (var11 != null && !var11.isEmpty()) {
                   var3.sendMessage(var11);
                }

             } else {
                GemRegistryImpl var5 = this.plugin.getGemRegistry();
                int var6 = var5 != null ? var5.tierFromItemId(var4) : (var4.endsWith("_gem_t2") ? 2 : 1);
                if (var6 < 2) {
                   String var12 = this.plugin.getConfigManager().getFormattedMessage("requires-tier2");
                   if (var12 != null && !var12.isEmpty()) {
                      var3.sendMessage(var12);
                   }

                } else {
                   int var7 = this.plugin.getEnergyManager().getEnergy(var3);
                   if (var7 <= 0) {
                      String var13 = this.plugin.getConfigManager().getFormattedMessage("no-energy");
                      if (var13 != null && !var13.isEmpty()) {
                         var3.sendMessage(var13);
                      }

                   } else {
                      GemType var8 = GemType.fromOraxenId(var4);
                      if (var8 != null) {
                         switch (var8) {
                            case ASTRA -> this.plugin.getAstraAbilities().a(var3);
                            case FIRE -> this.plugin.getFireAbilities().h(var3);
                            case FLUX -> this.plugin.getFluxAbilities().f(var3);
                            case LIFE -> this.plugin.getLifeAbilities().c(var3);
                            case PUFF -> this.plugin.getPuffAbilities().e(var3);
                            case SPEED -> this.plugin.getSpeedAbilities().a(var3);
                            case STRENGTH -> this.plugin.getStrengthAbilities().c(var3);
                            case WEALTH -> this.plugin.getWealthAbilities().c(var3);
                            default -> var3.sendMessage("§c§oNo tertiary ability for your gem type!");
                         }

                      } else {
                         if (var5 != null) {
                            String var9 = var5.gemIdFromItemId(var4);
                            GemAbilityHandler var10 = var9 != null ? var5.getAbilityHandler(var9) : null;
                            if (var10 != null) {
                               var10.onTertiary(var3, var6);
                            }
                         }

                      }
                   }
                }
             }
          }
       }

       private void handleAbilityQuaternary(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             String var4 = this.findGemInHand(var3);
             if (var4 == null) {
                String var11 = this.plugin.getConfigManager().getFormattedMessage("must-hold-gem");
                if (var11 != null && !var11.isEmpty()) {
                   var3.sendMessage(var11);
                }

             } else {
                GemRegistryImpl var5 = this.plugin.getGemRegistry();
                int var6 = var5 != null ? var5.tierFromItemId(var4) : (var4.endsWith("_gem_t2") ? 2 : 1);
                if (var6 < 2) {
                   String var12 = this.plugin.getConfigManager().getFormattedMessage("requires-tier2");
                   if (var12 != null && !var12.isEmpty()) {
                      var3.sendMessage(var12);
                   }

                } else {
                   int var7 = this.plugin.getEnergyManager().getEnergy(var3);
                   if (var7 <= 0) {
                      String var13 = this.plugin.getConfigManager().getFormattedMessage("no-energy");
                      if (var13 != null && !var13.isEmpty()) {
                         var3.sendMessage(var13);
                      }

                   } else {
                      GemType var8 = GemType.fromOraxenId(var4);
                      if (var8 != null) {
                         switch (var8) {
                            case ASTRA:
                               this.plugin.getAstraAbilities().b(var3);
                               break;
                            case FIRE:
                               this.plugin.getFireAbilities().i(var3);
                               break;
                            case FLUX:
                               this.plugin.getFluxAbilities().g(var3);
                               break;
                            case LIFE:
                               this.plugin.getLifeAbilities().d(var3);
                               break;
                            case PUFF:
                            case SPEED:
                            case STRENGTH:
                            default:
                               var3.sendMessage("§c§oNo quaternary ability for your gem type!");
                               break;
                            case WEALTH:
                               this.plugin.getWealthAbilities().e(var3);
                         }

                      } else {
                         if (var5 != null) {
                            String var9 = var5.gemIdFromItemId(var4);
                            GemAbilityHandler var10 = var9 != null ? var5.getAbilityHandler(var9) : null;
                            if (var10 != null) {
                               var10.onQuaternary(var3, var6);
                            }
                         }

                      }
                   }
                }
             }
          }
       }

       private void handleAbilityBindingsList(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             AbilityBindingManager var4 = this.plugin.getAbilityBindingManager();
             if (var4 == null) {
                var3.sendMessage("§cBinding system unavailable.");
             } else {
                EnumMap var5 = var4.getAll(var3);
                var3.sendMessage("§d§l⚡ Your Ability Bindings");

                for(AbilitySlot var9 : AbilitySlot.values()) {
                   AbilityBinding var10 = null;

                   for(Map.Entry var12 : var5.entrySet()) {
                      if (var12.getValue() == var9) {
                         var10 = (AbilityBinding)var12.getKey();
                         break;
                      }
                   }

                   String var18 = var10 != null ? "§f" + var10.getDisplayName() : "§8unbound";
                   String var10001 = var9.getDisplayName();
                   var3.sendMessage("§7" + var10001 + " §8→ " + var18);
                }

                var3.sendMessage("");
                var3.sendMessage("§d§l⚡ Available Inputs");

                for(AbilityBinding var16 : AbilityBinding.values()) {
                   AbilitySlot var17 = (AbilitySlot)var5.get(var16);
                   String var19 = var17 != null ? " §8(§7" + var17.getDisplayName() + "§8)" : "";
                   String var20 = var16.getId();
                   var3.sendMessage("§f• §7" + var20 + " §8- §f" + var16.getDisplayName() + var19);
                }

                var3.sendMessage("");
                var3.sendMessage("§7Change with §f/bliss set_ability <slot> <input>");
                var3.sendMessage("§7Unbind a slot with §f/bliss set_ability <slot> none");
                var3.sendMessage("§7Reset defaults with §f/bliss set_ability reset");
             }
          }
       }

       private void handleSetAbility(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             AbilityBindingManager var4 = this.plugin.getAbilityBindingManager();
             if (var4 == null) {
                var3.sendMessage("§cBinding system unavailable.");
             } else if (var2.length >= 2 && var2[1].equalsIgnoreCase("reset")) {
                var4.resetToDefaults(var3);
                var3.sendMessage("§aAbility bindings reset to defaults.");
             } else if (var2.length < 3) {
                var3.sendMessage("§cUsage: §f/bliss set_ability <slot> <input>");
                var3.sendMessage("§7Slots: §fprimary, secondary, tertiary, quaternary");
                var3.sendMessage("§7Inputs: §fright_click, shift_right_click, left_click, shift_left_click, swap_hand, shift_swap_hand, none");
                var3.sendMessage("§7See current bindings with §f/bliss ability");
             } else {
                AbilitySlot var5 = AbilitySlot.fromId(var2[1]);
                if (var5 == null) {
                   var3.sendMessage("§cUnknown slot: §f" + var2[1] + "§c. Valid: primary, secondary, tertiary, quaternary.");
                } else {
                   String var6 = var2[2];
                   if (!var6.equalsIgnoreCase("none") && !var6.equalsIgnoreCase("unbind")) {
                      AbilityBinding var10 = AbilityBinding.fromId(var6);
                      if (var10 == null) {
                         var3.sendMessage("§cUnknown input: §f" + var6 + "§c. See §f/bliss ability§c for the list.");
                      } else {
                         var4.setBinding(var3, var10, var5);
                         String var10001 = var10.getDisplayName();
                         var3.sendMessage("§aBound §f" + var10001 + "§a → §l" + var5.getDisplayName() + "§a.");
                      }
                   } else {
                      EnumMap var7 = var4.getAll(var3);

                      for(Map.Entry var9 : var7.entrySet()) {
                         if (var9.getValue() == var5) {
                            var4.unbind(var3, (AbilityBinding)var9.getKey());
                         }
                      }

                      var3.sendMessage("§aUnbound §l" + var5.getDisplayName() + "§a.");
                   }
                }
             }
          }
       }

       private void handleTrust(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else if (var2.length < 2) {
             var1.sendMessage("§cUsage: /bliss trust <player>");
          } else {
             Player var3 = (Player)var1;
             Player var4 = Bukkit.getPlayer(var2[1]);
             if (var4 == null) {
                var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("player-not-found"));
             } else if (var3.getUniqueId().equals(var4.getUniqueId())) {
                var3.sendMessage("§cYou already trust yourself!");
             } else {
                this.plugin.getTrustedPlayersManager().addTrustedPlayer(var3, var4);
                var3.sendMessage("§aYou now trust §l" + var4.getName() + "§r§a! Your gem abilities will not harm them.");
                var4.sendMessage("§a" + var3.getName() + " §anow trusts you! Their gem abilities will not harm you.");
             }
          }
       }

       private void handleUntrust(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else if (var2.length < 2) {
             var1.sendMessage("§cUsage: /bliss untrust <player>");
          } else {
             Player var3 = (Player)var1;
             Player var4 = Bukkit.getPlayer(var2[1]);
             if (var4 == null) {
                var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("player-not-found"));
             } else {
                boolean var5 = this.plugin.getTrustedPlayersManager().removeTrustedPlayer(var3, var4);
                if (var5) {
                   var3.sendMessage("§cYou no longer trust §l" + var4.getName() + "§r§c! Your gem abilities can now harm them.");
                   var4.sendMessage("§c" + var3.getName() + " §cno longer trusts you! Their gem abilities can now harm you.");
                } else {
                   var3.sendMessage("§cYou were not trusting " + var4.getName() + "!");
                }

             }
          }
       }

       private void handleTrustedList(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             Set var4 = this.plugin.getTrustedPlayersManager().getTrustedPlayers(var3);
             if (var4.isEmpty()) {
                var3.sendMessage("§7You have no trusted players. Use §b/bliss trust <player>§7 to add someone.");
             } else {
                var3.sendMessage("§5§lTrusted Players (" + var4.size() + "):");

                for(UUID var6 : var4) {
                   OfflinePlayer var7 = Bukkit.getOfflinePlayer(var6);
                   String var8 = var7.getName() != null ? var7.getName() : var6.toString();
                   String var9 = var7.isOnline() ? "§a[Online]" : "§7[Offline]";
                   var3.sendMessage("§8 - §b" + var8 + " " + var9);
                }

             }
          }
       }

       private void handleBannable(CommandSender var1, String[] var2) {
          if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else if (var2.length < 2) {
             var1.sendMessage("§cUsage: /bliss bannable <true/false>");
             var1.sendMessage("§7Current status: " + (this.plugin.getConfigManager().isBanOnZeroEnergyEnabled() ? "§aEnabled" : "§cDisabled"));
          } else {
             String var3 = var2[1].toLowerCase();
             boolean var4;
             if (!var3.equals("true") && !var3.equals("on") && !var3.equals("yes") && !var3.equals("1")) {
                if (!var3.equals("false") && !var3.equals("off") && !var3.equals("no") && !var3.equals("0")) {
                   var1.sendMessage("§cInvalid value! Use true or false.");
                   return;
                }

                var4 = false;
             } else {
                var4 = true;
             }

             this.plugin.getConfigManager().setBanOnZeroEnergy(var4);
             if (var4) {
                String var5 = this.plugin.getConfigManager().getFormattedMessage("ban-enabled");
                if (var5 != null && !var5.isEmpty()) {
                   var1.sendMessage(var5);
                } else {
                   var1.sendMessage("§aBan-on-zero-energy has been enabled!");
                }
             } else {
                String var6 = this.plugin.getConfigManager().getFormattedMessage("ban-disabled");
                if (var6 != null && !var6.isEmpty()) {
                   var1.sendMessage(var6);
                } else {
                   var1.sendMessage("§cBan-on-zero-energy has been disabled!");
                }
             }

          }
       }

       private void handleAutoSmelt(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else if (!var3.hasPermission("blissgems.autosmelt")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else {
             GemType var4 = this.plugin.getGemManager().getGemType(var3);
             int var5 = this.plugin.getGemManager().getGemTier(var3);
             if (var4 == GemType.WEALTH && var5 >= 2) {
                boolean var6 = this.plugin.getWealthAbilities().f(var3);
                this.plugin.getWealthAbilities().a(var3, !var6);
                if (!var6) {
                   var3.sendMessage("§a§lAuto-Smelt enabled! Ores will now be automatically smelted when mined.");
                } else {
                   var3.sendMessage("§c§lAuto-Smelt disabled!");
                }

             } else {
                var3.sendMessage("§c§lYou need Wealth Gem Tier 2 to use auto-smelt!");
             }
          }
       }

       private void handleStats(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             a var4 = new a(this.plugin);
             var4.a(var3, var2.length > 1 ? (String[])Arrays.copyOfRange(var2, 1, var2.length) : new String[0]);
          }
       }

       private void handleReleaseSouls(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else if (!this.plugin.getGemManager().hasGemType(var3, GemType.ASTRA)) {
             var3.sendMessage("§c§oOnly Astra gem holders can release captured souls!");
          } else if (!this.plugin.getEnergyManager().arePassivesActive(var3)) {
             var3.sendMessage("§c§oYour gem energy is too low to release souls!");
          } else {
             this.plugin.getSoulManager().releaseAllSouls(var3);
          }
       }

       private void handleSoulsInfo(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else if (!this.plugin.getGemManager().hasGemType(var3, GemType.ASTRA)) {
             var3.sendMessage("§c§oOnly Astra gem holders can view captured souls!");
          } else {
             List var4 = this.plugin.getSoulManager().getCapturedSouls(var3);
             int var5 = var4.size();
             byte var6 = 2;
             var3.sendMessage("§d§lCaptured Souls (" + var5 + "/" + var6 + "):");
             if (var5 == 0) {
                var3.sendMessage("§7  No souls captured. Sneak + hit a mob to capture it.");
             } else {
                for(int var7 = 0; var7 < var4.size(); ++var7) {
                   var3.sendMessage("§d  " + (var7 + 1) + ". §f" + ((SoulManager.CapturedMob)var4.get(var7)).getDisplayName());
                }

                var3.sendMessage("§7  Use §d/bliss release§7 to release all captured souls.");
             }

          }
       }

       private void handleNormalise(CommandSender var1, String[] var2) {
          if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else {
             int var3 = 0;

             for(Player var5 : Bukkit.getOnlinePlayers()) {
                AttributeInstance var6 = var5.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                if (var6 != null) {
                   double var7 = var6.getBaseValue();
                   var6.setBaseValue((double)1024.0F);
                   var6.setBaseValue(var7);
                }

                ++var3;
             }

             var1.sendMessage("§a§lAttack cooldown reset for " + var3 + " online player(s)!");

             for(Player var10 : Bukkit.getOnlinePlayers()) {
                var10.sendMessage("§e§oAttack cooldowns have been normalized by an admin.");
             }

          }
       }

       private void handleSmp(CommandSender var1, String[] var2) {
          if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else if (var2.length >= 2 && var2[1].equalsIgnoreCase("start")) {
             if (this.plugin.getConfigManager().isSmpStarted()) {
                this.plugin.getConfigManager().sendFormattedMessage(var1, "smp-already-started");
             } else {
                this.plugin.getConfigManager().setSmpStarted(true);
                this.plugin.getConfigManager().sendFormattedMessage(var1, "smp-started");

                for(Player var4 : Bukkit.getOnlinePlayers()) {
                   if (!this.hasReceivedFirstGem(var4)) {
                      GemType var5 = this.getRandomEnabledGem();
                      if (var5 != null) {
                         var4.sendMessage("");
                         var4.sendMessage("§d§l§m                                                  ");
                         var4.sendMessage("§d§lWELCOME TO BLISSGEMS!");
                         var4.sendMessage("");
                         var4.sendMessage("§7§oThe ancient gem ritual begins...");
                         var4.sendMessage("§7§oYour destiny is being forged...");
                         var4.sendMessage("§d§l§m                                                  ");
                         var4.sendMessage("");
                         this.plugin.getGemRitualManager().performGemRitual(var4, var5, true, 1);
                         this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                            if (var4.isOnline() && this.plugin.getGemManager().giveGem(var4, (GemType)var5, 1)) {
                               this.markFirstGemReceived(var4);
                               String var3 = this.plugin.getConfigManager().getFormattedMessage("first-gem-received", "gem", var5.getDisplayName());
                               if (var3 != null && !var3.isEmpty()) {
                                  var4.sendMessage(var3);
                               } else {
                                  var4.sendMessage("");
                                  String var10001 = var5.getColor();
                                  var4.sendMessage("§d§l» §fYour gem has been chosen: " + var10001 + "§l" + var5.getDisplayName() + "§d§l «");
                                  var4.sendMessage("");
                               }

                               Logger var10000 = this.plugin.getLogger();
                               String var4x = var4.getName();
                               var10000.info("SMP Start: Gave " + var4x + " their first gem: " + var5.getDisplayName());
                            }

                         }, 20L);
                      }
                   }
                }

             }
          } else {
             var1.sendMessage("§cUsage: /bliss smp start");
          }
       }

       private boolean hasReceivedFirstGem(Player var1) {
          File var2 = new File(this.plugin.getDataFolder(), "playerdata");
          File var3 = new File(var2, String.valueOf(var1.getUniqueId()) + ".yml");
          if (!var3.exists()) {
             return false;
          } else {
             YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
             return ((FileConfiguration)var4).getBoolean("received-first-gem", false);
          }
       }

       private void markFirstGemReceived(Player var1) {
          File var2 = new File(this.plugin.getDataFolder(), "playerdata");
          if (!var2.exists()) {
             var2.mkdirs();
          }

          File var3 = new File(var2, String.valueOf(var1.getUniqueId()) + ".yml");
          YamlConfiguration var4;
          if (var3.exists()) {
             var4 = YamlConfiguration.loadConfiguration(var3);
          } else {
             var4 = new YamlConfiguration();
          }

          ((FileConfiguration)var4).set("received-first-gem", true);
          if (!((FileConfiguration)var4).contains("energy")) {
             ((FileConfiguration)var4).set("energy", this.plugin.getConfigManager().getStartingEnergy());
          }

          try {
             ((FileConfiguration)var4).save(var3);
          } catch (IOException var6) {
             Logger var10000 = this.plugin.getLogger();
             String var10001 = var1.getName();
             var10000.warning("Failed to save first gem status for " + var10001 + ": " + var6.getMessage());
          }

       }

       private GemType getRandomEnabledGem() {
          ArrayList var1 = new ArrayList();

          for(GemType var5 : GemType.values()) {
             if (this.plugin.getConfigManager().isGemEnabled(var5)) {
                var1.add(var5);
             }
          }

          if (var1.isEmpty()) {
             return null;
          } else {
             return (GemType)var1.get((new Random()).nextInt(var1.size()));
          }
       }

       private void handleClearCooldowns(CommandSender var1, String[] var2) {
          if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("no-permission"));
          } else if (var2.length < 2) {
             var1.sendMessage("§cUsage: /bliss clearcds <player|all>");
          } else if (!var2[1].equalsIgnoreCase("all")) {
             Player var6 = Bukkit.getPlayer(var2[1]);
             if (var6 == null) {
                var1.sendMessage(this.plugin.getConfigManager().getFormattedMessage("player-not-found"));
             } else {
                this.plugin.getAbilityManager().clearCooldowns(var6);
                var1.sendMessage("§aCleared all ability cooldowns for §l" + var6.getName() + "§a!");
             }
          } else {
             int var3 = 0;

             for(Player var5 : Bukkit.getOnlinePlayers()) {
                this.plugin.getAbilityManager().clearCooldowns(var5);
                ++var3;
             }

             var1.sendMessage("§a§lCleared all ability cooldowns for " + var3 + " online player(s)!");
          }
       }

       private void handleAchievements(CommandSender var1, String[] var2) {
          if (!(var1 instanceof Player var3)) {
             var1.sendMessage("§cOnly players can use this command!");
          } else {
             Set var4 = this.plugin.getAchievementManager().getUnlocked(var3);
             int var5 = Achievement.values().length;
             int var6 = var4.size();
             var3.sendMessage("§6§l⭐ Achievements (" + var6 + "/" + var5 + ")");
             var3.sendMessage("");

             for(Achievement var10 : Achievement.values()) {
                boolean var11 = var4.contains(var10);
                int var12 = this.plugin.getAchievementManager().getProgress(var3, var10);
                int var13 = var10.getTargetProgress();
                if (var11) {
                   String var10001 = var10.getDisplayName();
                   var3.sendMessage("§a✔ §e" + var10001 + " §7- " + var10.getDescription());
                } else if (var13 > 1 && var12 > 0) {
                   String var15 = var10.getDisplayName();
                   var3.sendMessage("§8✘ §7" + var15 + " §8- " + var10.getDescription() + " §e(" + var12 + "/" + var13 + ")");
                } else {
                   String var14 = var10.getDisplayName();
                   var3.sendMessage("§8✘ §7" + var14 + " §8- " + var10.getDescription());
                }
             }

          }
       }

       private void sendHelp(CommandSender var1) {
          var1.sendMessage("§5§lBlissGems Commands:");
          var1.sendMessage("§7/bliss give <player> <gem_type> [tier] §8- Give a gem");
          var1.sendMessage("§7/bliss reroll <player> [tier] §8- Give a random gem");
          var1.sendMessage("§7/bliss giveitem <player> <item_id> [amount] §8- Give special items");
          var1.sendMessage("§7/bliss energy <player> <set/add/remove> <amount> §8- Manage energy");
          var1.sendMessage("§7/bliss withdraw §8- Extract energy into bottle");
          var1.sendMessage("§7/bliss info §8- Show your gem info");
          var1.sendMessage("§7/bliss pockets §8- Open personal inventory (Wealth T2)");
          var1.sendMessage("§7/bliss amplify §8- Amplify potion effects (Wealth T2)");
          var1.sendMessage("§7/bliss autosmelt §8- Toggle auto-smelting (Wealth T2)");
          var1.sendMessage("§7/bliss toggle_click §8- Toggle click activation on/off");
          var1.sendMessage("§7/bliss ability:main §8- Trigger primary ability");
          var1.sendMessage("§7/bliss ability:secondary §8- Trigger secondary ability (T2)");
          var1.sendMessage("§7/bliss trust <player> §8- Trust player (prevent friendly fire)");
          var1.sendMessage("§7/bliss untrust <player> §8- Untrust player");
          var1.sendMessage("§7/bliss trusted §8- List trusted players");
          var1.sendMessage("§7/bliss souls §8- View captured souls (Astra)");
          var1.sendMessage("§7/bliss release §8- Release captured souls (Astra)");
          var1.sendMessage("§7/bliss achievements §8- View your achievements");
          var1.sendMessage("§7/bliss stats [top|me|gems] §8- View server stats");
          var1.sendMessage("§7/bliss bannable <true/false> §8- Toggle ban on 0 energy (Admin)");
          var1.sendMessage("§7/bliss smp start §8- Start the SMP and distribute gems (Admin)");
          var1.sendMessage("§7/bliss normalise §8- Reset attack cooldowns for all players (Admin)");
          var1.sendMessage("§7/bliss clearcds <player|all> §8- Clear ability cooldowns (Admin)");
          var1.sendMessage("§7/bliss reload §8- Reload config");
       }

       public List<String> onTabComplete(CommandSender var1, Command var2, String var3, String[] var4) {
          ArrayList var5 = new ArrayList();
          if (var4.length == 1) {
             var5.addAll(Arrays.asList("give", "reroll", "giveitem", "energy", "withdraw", "info", "pockets", "amplify", "autosmelt", "reload", "toggle_click", "ability:main", "ability:secondary", "ability:tertiary", "ability:quaternary", "trust", "untrust", "trusted", "stats", "achievements", "bannable", "souls", "release", "normalise", "normalize", "smp", "clearcds"));
          } else if (var4.length == 2) {
             if (var4[0].equalsIgnoreCase("clearcds")) {
                List var6 = (List)Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
                var6.add(0, "all");
                return var6;
             }

             if (var4[0].equalsIgnoreCase("give") || var4[0].equalsIgnoreCase("reroll") || var4[0].equalsIgnoreCase("giveitem") || var4[0].equalsIgnoreCase("energy") || var4[0].equalsIgnoreCase("trust") || var4[0].equalsIgnoreCase("untrust")) {
                return (List)Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
             }

             if (var4[0].equalsIgnoreCase("stats")) {
                return Arrays.asList("top", "me", "gems");
             }

             if (var4[0].equalsIgnoreCase("bannable")) {
                return Arrays.asList("true", "false");
             }

             if (var4[0].equalsIgnoreCase("smp")) {
                return Arrays.asList("start");
             }
          } else if (var4.length == 3) {
             if (var4[0].equalsIgnoreCase("give")) {
                List var11 = (List)Arrays.stream(GemType.values()).map(GemType::getId).collect(Collectors.toList());
                GemRegistryImpl var7 = this.plugin.getGemRegistry();
                if (var7 != null) {
                   for(GemDefinition var9 : var7.getAllGems()) {
                      if (!var11.contains(var9.getId())) {
                         var11.add(var9.getId());
                      }
                   }
                }

                return var11;
             }

             if (var4[0].equalsIgnoreCase("giveitem")) {
                ArrayList var10 = new ArrayList();
                var10.add("energy_bottle");
                var10.add("repair_kit");
                var10.add("gem_trader");
                var10.add("gem_fragment");
                var10.add("gem_upgrader");
                return var10;
             }

             if (var4[0].equalsIgnoreCase("energy")) {
                return Arrays.asList("set", "add", "remove");
             }
          } else if (var4.length == 3) {
             if (var4[0].equalsIgnoreCase("reroll")) {
                return Arrays.asList("1", "2");
             }
          } else if (var4.length == 4) {
             if (var4[0].equalsIgnoreCase("give")) {
                return Arrays.asList("1", "2");
             }

             if (var4[0].equalsIgnoreCase("giveitem")) {
                return Arrays.asList("1", "8", "16", "32", "64");
             }
          }

          return (List)var5.stream().filter((var1x) -> var1x.toLowerCase().startsWith(var4[var4.length - 1].toLowerCase())).collect(Collectors.toList());
       }
    }

    // ===== Merged from separate source file: PassiveListener.java =====
    public static class PassiveListener implements Listener {
       private final BlissGems plugin;
       private final Map<UUID, Integer> jumpsRemaining = new HashMap();

       public PassiveListener(BlissGems var1) {
          this.plugin = var1;
       }

       private boolean isHoldingPuffGem(Player var1) {
          ItemStack var2 = var1.getInventory().getItemInMainHand();
          if (var2 != null) {
             String var3 = CustomItemManager.getIdByItem(var2);
             if (var3 != null && var3.contains("puff_gem")) {
                return true;
             }
          }

          return false;
       }

       @EventHandler
       public void onEntityDamage(EntityDamageByEntityEvent var1) {
          Entity var2 = var1.getEntity();
          if (var2 instanceof Player var3) {
             if (this.plugin.getGemManager().hasGemTypeInOffhand(var3, GemType.ASTRA)) {
                if (this.canUsePassives(var3)) {
                   int var4 = this.plugin.getGemManager().getTierFromOffhand(var3);
                   double var5 = this.plugin.getConfigManager().getPhaseChance(var4);
                   if (Math.random() < var5) {
                      var1.setCancelled(true);
                      var3.sendMessage("§d§oYou phased through the attack!");
                      if (this.plugin.getAchievementManager() != null && var3.getHealth() <= var1.getDamage()) {
                         this.plugin.getAchievementManager().unlock(var3, Achievement.SAVED_BY_THE_DICE);
                      }
                   }

                }
             }
          }
       }

       @EventHandler
       public void onPlayerDamageEntity(EntityDamageByEntityEvent var1) {
          Entity var2 = var1.getDamager();
          if (var2 instanceof Player var3) {
             Entity var4 = var1.getEntity();
             if (var4 instanceof LivingEntity var5) {
                if (this.plugin.getGemManager().hasGemTypeInOffhand(var3, GemType.LIFE)) {
                   if (this.canUsePassives(var3)) {
                      if (Tag.ENTITY_TYPES_SENSITIVE_TO_SMITE.isTagged(var5.getType())) {
                         int var6 = this.plugin.getGemManager().getTierFromOffhand(var3);
                         double var7 = this.plugin.getConfigManager().getUndeadDamageMultiplier(var6);
                         var1.setDamage(var1.getDamage() * var7);
                      }

                   }
                }
             }
          }
       }

       @EventHandler
       public void onPuffGemHitPlayer(EntityDamageByEntityEvent var1) {
          Entity var2 = var1.getDamager();
          if (var2 instanceof Player var3) {
             Entity var4 = var1.getEntity();
             if (var4 instanceof Player var5) {
                ItemStack var6 = var3.getInventory().getItemInMainHand();
                if (var6 != null) {
                   String var7 = CustomItemManager.getIdByItem(var6);
                   if (var7 != null && var7.contains("puff_gem")) {
                      if (this.canUsePassives(var3)) {
                         int var8 = GemType.getTierFromOraxenId(var7);
                         double var9 = this.plugin.getConfigManager().getLaunchVelocity(var8);
                         Vector var11 = new Vector((double)0.0F, var9, (double)0.0F);
                         var5.setVelocity(var11);
                         var5.getWorld().playSound(var5.getLocation(), Sound.ENTITY_BREEZE_WIND_BURST, 1.0F, 0.8F);
                         var5.getWorld().spawnParticle(Particle.CLOUD, var5.getLocation(), 30, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
                         var5.sendMessage("§b§oYou've been launched into the sky!");
                         var3.sendMessage("§b§oYou launched " + var5.getName() + " into the sky!");
                         this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                            if (var5.isOnline()) {
                               Vector var1 = new Vector((double)0.0F, (double)-3.5F, (double)0.0F);
                               var5.setVelocity(var1);
                               var5.getWorld().playSound(var5.getLocation(), Sound.ENTITY_BREEZE_LAND, 1.0F, 0.5F);
                               var5.sendMessage("§c§oYou're being slammed down!");
                            }
                         }, 60L);
                      }
                   }
                }
             }
          }
       }

       @EventHandler
       public void onFallDamage(EntityDamageEvent var1) {
          Entity var2 = var1.getEntity();
          if (var2 instanceof Player var3) {
             if (var1.getCause() == DamageCause.FALL) {
                if (this.plugin.getPuffAbilities().a(var3)) {
                   var1.setCancelled(true);
                   this.plugin.getPuffAbilities().b(var3);
                } else if (this.plugin.getGemManager().hasGemTypeInOffhand(var3, GemType.PUFF) || this.isHoldingPuffGem(var3)) {
                   if (this.canUsePassives(var3)) {
                      var1.setCancelled(true);
                      if (this.plugin.getAchievementManager() != null) {
                         int var4 = (int)var1.getDamage();
                         this.plugin.getAchievementManager().addProgress(var3, Achievement.ALMOST_FELL_FROM_GRACE, var4);
                      }

                   }
                }
             }
          }
       }

       @EventHandler
       public void onPlayerMove(PlayerMoveEvent var1) {
          Player var2 = var1.getPlayer();
          if (var2.getGameMode() != GameMode.CREATIVE && var2.getGameMode() != GameMode.SPECTATOR) {
             boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.PUFF) || this.isHoldingPuffGem(var2);
             boolean var4 = var3 && this.canUsePassives(var2);
             if (var4 && var2.isOnGround()) {
                this.jumpsRemaining.put(var2.getUniqueId(), 2);
                if (!var2.getAllowFlight()) {
                   var2.setAllowFlight(true);
                }
             } else if (!var4 && var2.getAllowFlight() && var2.getGameMode() == GameMode.SURVIVAL) {
                var2.setAllowFlight(false);
                this.jumpsRemaining.remove(var2.getUniqueId());
             }

          }
       }

       @EventHandler
       public void onTripleJumpFlight(PlayerToggleFlightEvent var1) {
          Player var2 = var1.getPlayer();
          if (var2.getGameMode() != GameMode.CREATIVE && var2.getGameMode() != GameMode.SPECTATOR) {
             boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.PUFF) || this.isHoldingPuffGem(var2);
             if (var3) {
                if (this.canUsePassives(var2)) {
                   UUID var4 = var2.getUniqueId();
                   if (!c.a(var4) && !f.a(var4)) {
                      int var5 = (Integer)this.jumpsRemaining.getOrDefault(var4, 0);
                      if (var5 <= 0) {
                         var1.setCancelled(true);
                      } else {
                         var1.setCancelled(true);
                         var2.setAllowFlight(false);
                         this.jumpsRemaining.put(var4, var5 - 1);
                         int var6 = this.plugin.getGemManager().getTierFromOffhand(var2);
                         if (var6 == 0) {
                            ItemStack var7 = var2.getInventory().getItemInMainHand();
                            String var8 = CustomItemManager.getIdByItem(var7);
                            if (var8 != null) {
                               var6 = GemType.getTierFromOraxenId(var8);
                            }
                         }

                         if (var6 == 0) {
                            var6 = 1;
                         }

                         double var10 = this.plugin.getConfigManager().getDoubleJumpVelocity(var6);
                         Vector var9 = var2.getVelocity();
                         var9.setY(var10);
                         var2.setVelocity(var9);
                         var2.getWorld().spawnParticle(Particle.CLOUD, var2.getLocation(), 20, 0.3, 0.1, 0.3, 0.05);
                         var2.playSound(var2.getLocation(), Sound.ENTITY_BREEZE_SHOOT, 0.5F, 1.5F);
                         if (var5 - 1 > 0) {
                            this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                               if (var2.isOnline() && !var2.isOnGround() && var2.getGameMode() != GameMode.CREATIVE && var2.getGameMode() != GameMode.SPECTATOR) {
                                  var2.setAllowFlight(true);
                               }

                            }, 2L);
                         }

                      }
                   } else {
                      var1.setCancelled(true);
                   }
                }
             }
          }
       }

       @EventHandler
       public void onFarmlandTrample(PlayerInteractEvent var1) {
          if (var1.getAction() == Action.PHYSICAL) {
             if (var1.getClickedBlock() != null && var1.getClickedBlock().getType() == Material.FARMLAND) {
                Player var2 = var1.getPlayer();
                boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.PUFF) || this.isHoldingPuffGem(var2);
                if (var3) {
                   if (this.canUsePassives(var2)) {
                      var1.setCancelled(true);
                   }
                }
             }
          }
       }

       @EventHandler
       public void onCrispBlockBreak(BlockBreakEvent var1) {
          if (this.plugin.getFireAbilities() != null && this.plugin.getFireAbilities().a(var1.getBlock().getLocation())) {
             var1.setCancelled(true);
             var1.getPlayer().sendMessage("§c§oThis block is scorched and cannot be broken!");
          }

       }

       @EventHandler
       public void onBlockBreak(BlockBreakEvent var1) {
          Player var2 = var1.getPlayer();
          boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FIRE);
          boolean var4 = false;
          if (this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.WEALTH)) {
             int var5 = this.plugin.getGemManager().getGemTier(var2);
             if (var5 >= 2 && this.plugin.getWealthAbilities().f(var2)) {
                var4 = true;
             }
          }

          if (var3 || var4) {
             if (this.canUsePassives(var2)) {
                ItemStack var8 = var2.getInventory().getItemInMainHand();
                if (var8 == null || !var8.hasItemMeta() || !var8.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                   Material var6 = var1.getBlock().getType();
                   ItemStack var7 = this.getSmeltedItem(var6);
                   if (var7 != null) {
                      var1.setDropItems(false);
                      var1.getBlock().getWorld().dropItemNaturally(var1.getBlock().getLocation(), var7);
                   }

                }
             }
          }
       }

       private ItemStack getSmeltedItem(Material var1) {
          ItemStack var10000;
          switch (var1) {
             case IRON_ORE:
             case DEEPSLATE_IRON_ORE:
                var10000 = new ItemStack(Material.IRON_INGOT);
                break;
             case GOLD_ORE:
             case DEEPSLATE_GOLD_ORE:
             case NETHER_GOLD_ORE:
                var10000 = new ItemStack(Material.GOLD_INGOT);
                break;
             case COPPER_ORE:
             case DEEPSLATE_COPPER_ORE:
                var10000 = new ItemStack(Material.COPPER_INGOT);
                break;
             case ANCIENT_DEBRIS:
                var10000 = new ItemStack(Material.NETHERITE_SCRAP);
                break;
             case SAND:
                var10000 = new ItemStack(Material.GLASS);
                break;
             case COBBLESTONE:
                var10000 = new ItemStack(Material.STONE);
                break;
             case RAW_IRON_BLOCK:
                var10000 = new ItemStack(Material.IRON_BLOCK);
                break;
             case RAW_GOLD_BLOCK:
                var10000 = new ItemStack(Material.GOLD_BLOCK);
                break;
             case RAW_COPPER_BLOCK:
                var10000 = new ItemStack(Material.COPPER_BLOCK);
                break;
             default:
                var10000 = null;
          }

          return var10000;
       }

       @EventHandler
       public void onFoodLevelChange(FoodLevelChangeEvent var1) {
          HumanEntity var2 = var1.getEntity();
          if (var2 instanceof Player var3) {
             if (this.plugin.getGemManager().hasGemTypeInOffhand(var3, GemType.LIFE)) {
                if (this.canUsePassives(var3)) {
                   int var4 = var3.getFoodLevel();
                   int var5 = var1.getFoodLevel();
                   if (var5 > var4) {
                      int var6 = var5 - var4;
                      int var7 = this.plugin.getGemManager().getTierFromOffhand(var3);
                      float var8 = (float)var6 * (float)this.plugin.getConfigManager().getSaturationMultiplier(var7);
                      var3.setSaturation(var3.getSaturation() + var8);
                   }

                }
             }
          }
       }

       @EventHandler
       public void onProjectileHit(ProjectileHitEvent var1) {
          Projectile var2 = var1.getEntity();
          if (var2 instanceof Arrow) {
             ProjectileSource var3 = var2.getShooter();
             if (var3 instanceof Player) {
                Player var4 = (Player)var3;
                if ((this.plugin.getGemManager().hasGemTypeInOffhand(var4, GemType.FLUX) || this.isHoldingFluxGem(var4))) {
                   if (this.canUsePassives(var4)) {
                      Entity var5 = var1.getHitEntity();
                      if (var5 != null && var5 instanceof LivingEntity) {
                         double var6 = this.plugin.getConfig().getDouble("gems.passives.flux.shocking-chance", 0.15);
                         if (!(Math.random() > var6)) {
                            LivingEntity var8 = (LivingEntity)var5;
                            int var9 = this.getFluxTier(var4);
                            double var10 = this.plugin.getConfigManager().getShockingArrowDamage(var9);
                            var8.damage(var10, var4);
                            int var12 = this.plugin.getConfig().getInt("abilities.durations.flux-shocking-stun", 1) * 20;
                            var8.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, var12, 255, false, false));
                            var8.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, var12, 255, false, false));
                            var8.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var8.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 60, 0.7, 0.7, 0.7);
                            var8.getWorld().spawnParticle(Particle.ENCHANTED_HIT, var8.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F);
                            var8.getWorld().spawnParticle(Particle.CRIT, var8.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 20, (double)0.5F, (double)0.5F, (double)0.5F);
                            var8.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, var8.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 15, 0.3, 0.3, 0.3);
                            var8.getWorld().playSound(var8.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.5F, 1.5F);
                            var4.sendMessage("§b§oShocking Arrow hit!");
                         }
                      }
                   }
                }
             }
          }
       }

       @EventHandler
       public void onSculkShriekerActivate(BlockReceiveGameEvent var1) {
          Entity var2 = var1.getEntity();
          if (var2 instanceof Player var3) {
             if (this.plugin.getGemManager().hasGemTypeInOffhand(var3, GemType.PUFF)) {
                if (this.canUsePassives(var3)) {
                   int var4 = this.plugin.getGemManager().getTierFromOffhand(var3);
                   if (this.plugin.getConfigManager().isSculkImmunity(var4)) {
                      Material var5 = var1.getBlock().getType();
                      if (var5 == Material.SCULK_SHRIEKER || var5 == Material.SCULK_SENSOR || var5 == Material.CALIBRATED_SCULK_SENSOR) {
                         var1.setCancelled(true);
                      }

                   }
                }
             }
          }
       }

       @EventHandler
       public void onPlayerItemConsume(PlayerItemConsumeEvent var1) {
          Player var2 = var1.getPlayer();
          ItemStack var3 = var1.getItem();
          if (var3.getType() == Material.GOLDEN_APPLE || var3.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
             if (this.plugin.getGemManager().hasGemType(var2, GemType.LIFE)) {
                if (this.canUsePassives(var2)) {
                   int var4 = this.plugin.getGemManager().getGemTier(var2);
                   int var5 = this.plugin.getConfigManager().getGoldenAppleAbsorptionLevel(var4);
                   this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                      var2.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, var5, true, true));
                      var2.sendMessage("§a§oLife Gem enhanced your golden apple!");
                   }, 1L);
                }
             }
          }
       }

       @EventHandler
       public void onFireballExplosion(EntityDamageByEntityEvent var1) {
          if (var1.getEntity() instanceof Player) {
             Player var2 = (Player)var1.getEntity();
             Entity var3 = var1.getDamager();
             if (var3 instanceof Fireball) {
                Fireball var4 = (Fireball)var3;
                if (var4.getShooter() instanceof Player) {
                   Player var5 = (Player)var4.getShooter();
                   if (var5.getUniqueId().equals(var2.getUniqueId())) {
                      var1.setCancelled(true);
                   }
                }
             }

          }
       }

       @EventHandler
       public void onEntityDeath(EntityDeathEvent var1) {
          LivingEntity var2 = var1.getEntity();
          Player var3 = var2.getKiller();
          if (var3 != null) {
             boolean var4 = this.plugin.getGemManager().hasGemTypeInOffhand(var3, GemType.ASTRA) || this.isHoldingAstraGem(var3);
             if (var4) {
                if (this.plugin.getEnergyManager().arePassivesActive(var3)) {
                   this.plugin.getSoulManager().absorbSoul(var3, var2);
                }
             }
          }
       }

       @EventHandler
       public void onAstraSoulCapture(EntityDamageByEntityEvent var1) {
          if (var1.getDamager() instanceof Player) {
             Player var2 = (Player)var1.getDamager();
             if (var2.isSneaking()) {
                Entity var3 = var1.getEntity();
                if (var3 instanceof LivingEntity && !(var3 instanceof Player)) {
                   boolean var4 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.ASTRA) || this.isHoldingAstraGem(var2);
                   if (var4) {
                      if (this.canUsePassives(var2)) {
                         LivingEntity var5 = (LivingEntity)var3;
                         if (this.plugin.getSoulManager().captureMob(var2, var5)) {
                            var1.setCancelled(true);
                         }

                      }
                   }
                }
             }
          }
       }

       private boolean isHoldingAstraGem(Player var1) {
          ItemStack var2 = var1.getInventory().getItemInMainHand();
          if (var2 != null) {
             String var3 = CustomItemManager.getIdByItem(var2);
             if (var3 != null && var3.contains("astra_gem")) {
                return true;
             }
          }

          return false;
       }

       private boolean isHoldingFluxGem(Player var1) {
          ItemStack main = var1.getInventory().getItemInMainHand();
          ItemStack off = var1.getInventory().getItemInOffHand();
          String mainId = CustomItemManager.getIdByItem(main);
          String offId = CustomItemManager.getIdByItem(off);
          return (mainId != null && mainId.contains("flux_gem"))
              || (offId != null && offId.contains("flux_gem"));
       }

       private int getFluxTier(Player player) {
          ItemStack main = player.getInventory().getItemInMainHand();
          String mainId = CustomItemManager.getIdByItem(main);
          if (mainId != null && mainId.contains("flux_gem")) {
             return mainId.endsWith("_gem_t2") ? 2 : 1;
          }
          ItemStack off = player.getInventory().getItemInOffHand();
          String offId = CustomItemManager.getIdByItem(off);
          if (offId != null && offId.contains("flux_gem")) {
             return offId.endsWith("_gem_t2") ? 2 : 1;
          }
          return 1;
       }

       @EventHandler
       public void onFluxConduction(PlayerInteractEvent var1) {
          if (var1.getAction() == Action.LEFT_CLICK_AIR || var1.getAction() == Action.LEFT_CLICK_BLOCK) {
             Player var2 = var1.getPlayer();
             if (var2.isSneaking()) {
                boolean var3 = (this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX) || this.isHoldingFluxGem(var2)) || this.isHoldingFluxGem(var2);
                if (var3) {
                   if (this.canUsePassives(var2)) {
                      String var4 = "flux-conduction";
                      if (this.plugin.getAbilityManager().canUseAbility(var2, var4)) {
                         int var5 = this.plugin.getConfig().getInt("abilities.flux-conduction.range", 10);
                         Location var6 = var2.getLocation();
                         Block var7 = null;
                         double var8 = Double.MAX_VALUE;

                         for(int var10 = -var5; var10 <= var5; ++var10) {
                            for(int var11 = -var5; var11 <= var5; ++var11) {
                               for(int var12 = -var5; var12 <= var5; ++var12) {
                                  Block var13 = var6.getBlock().getRelative(var10, var11, var12);
                                  if (var13.getType().name().contains("COPPER") && !var13.getType().name().contains("COPPERHEAD")) {
                                     double var14 = var13.getLocation().distanceSquared(var6);
                                     if (var14 < var8) {
                                        var8 = var14;
                                        var7 = var13;
                                     }
                                  }
                               }
                            }
                         }

                         if (var7 != null) {
                            Location var16 = var2.getLocation().clone();
                            Location var17 = var7.getLocation().add((double)0.5F, (double)1.0F, (double)0.5F);
                            var17.setYaw(var2.getLocation().getYaw());
                            var17.setPitch(var2.getLocation().getPitch());
                            if (this.plugin.getAchievementManager() != null) {
                               int var18 = (int)var16.distance(var17);
                               this.plugin.getAchievementManager().addProgress(var2, Achievement.ZIP_AWAY, var18);
                            }

                            var2.teleport(var17);
                            Particle.DustOptions var19 = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 1.2F);
                            var16.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var16.add((double)0.0F, (double)1.0F, (double)0.0F), 50, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
                            var16.getWorld().spawnParticle(Particle.DUST, var16, 40, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var19, true);
                            var17.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var17.clone().add((double)0.0F, (double)0.5F, (double)0.0F), 50, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
                            var17.getWorld().spawnParticle(Particle.DUST, var17.clone().add((double)0.0F, (double)0.5F, (double)0.0F), 40, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var19, true);
                            var2.playSound(var16, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.5F);
                            var2.playSound(var17, Sound.BLOCK_COPPER_BULB_TURN_ON, 1.0F, 1.2F);
                            var2.sendMessage("§b⚡ §oConducted to copper!");
                            this.plugin.getAbilityManager().useAbility(var2, var4);
                         }
                      }
                   }
                }
             }
          }
       }

       @EventHandler
       public void onChargedCreeperDamage(EntityDamageByEntityEvent var1) {
          if (var1.getEntity() instanceof Player) {
             if (var1.getDamager() instanceof Creeper) {
                Creeper var2 = (Creeper)var1.getDamager();
                if (var2.isPowered()) {
                   Player var3 = (Player)var1.getEntity();
                   boolean var4 = (this.plugin.getGemManager().hasGemTypeInOffhand(var3, GemType.FLUX) || this.isHoldingFluxGem(var3)) || this.isHoldingFluxGem(var3);
                   if (var4) {
                      if (this.canUsePassives(var3)) {
                         double var5 = this.plugin.getConfig().getDouble("gems.passives.flux.charged-creeper-reduction", (double)1.0F);
                         var1.setDamage(var1.getDamage() * ((double)1.0F - var5));
                         Particle.DustOptions var7 = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 1.2F);
                         var3.getWorld().spawnParticle(Particle.DUST, var3.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 50, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var7, true);
                         var3.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var3.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
                         var3.sendMessage("§b⚡ §oFlux gem absorbed the charged creeper blast!");
                      }
                   }
                }
             }
          }
       }

       private boolean isHoldingStrengthGem(Player var1) {
          ItemStack var2 = var1.getInventory().getItemInMainHand();
          if (var2 != null) {
             String var3 = CustomItemManager.getIdByItem(var2);
             if (var3 != null && var3.contains("strength_gem")) {
                return true;
             }
          }

          return false;
       }

       @EventHandler
       public void onStrengthBloodthorns(EntityDamageByEntityEvent var1) {
          if (var1.getDamager() instanceof Player) {
             Player var2 = (Player)var1.getDamager();
             if (var1.getEntity() instanceof LivingEntity) {
                boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.STRENGTH) || this.isHoldingStrengthGem(var2);
                if (var3) {
                   if (this.canUsePassives(var2)) {
                      int var4 = this.plugin.getGemManager().getTierFromOffhand(var2);
                      if (var4 == 0) {
                         ItemStack var5 = var2.getInventory().getItemInMainHand();
                         String var6 = CustomItemManager.getIdByItem(var5);
                         if (var6 != null) {
                            var4 = GemType.getTierFromOraxenId(var6);
                         }
                      }

                      if (var4 == 0) {
                         var4 = 1;
                      }

                      double var18 = this.plugin.getConfigManager().getBloodthornsMaxBonusDamage(var4);
                      double var7 = var2.getHealth();
                      double var9 = var2.getMaxHealth();
                      double var11 = var7 / var9;
                      double var13 = var18 * ((double)1.0F - var11);
                      if (var13 > 0.1) {
                         var1.setDamage(var1.getDamage() + var13);
                         LivingEntity var15 = (LivingEntity)var1.getEntity();
                         Particle.DustOptions var16 = new Particle.DustOptions(ParticleUtils.STRENGTH_RED, 1.0F);
                         int var17 = Math.min((int)(var13 * (double)3.0F), 30);
                         var15.getWorld().spawnParticle(Particle.DUST, var15.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), var17, 0.3, 0.3, 0.3, (double)0.0F, var16, true);
                         var15.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, var15.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), Math.min((int)(var13 * (double)2.0F), 15), 0.3, 0.3, 0.3);
                      }

                   }
                }
             }
          }
       }

       private boolean isHoldingWealthGem(Player var1) {
          ItemStack var2 = var1.getInventory().getItemInMainHand();
          if (var2 != null) {
             String var3 = CustomItemManager.getIdByItem(var2);
             if (var3 != null && var3.contains("wealth_gem")) {
                return true;
             }
          }

          return false;
       }

       @EventHandler
       public void onWealthDurabilityChip(EntityDamageByEntityEvent var1) {
          if (var1.getDamager() instanceof Player) {
             Player var2 = (Player)var1.getDamager();
             if (var1.getEntity() instanceof LivingEntity) {
                LivingEntity var3 = (LivingEntity)var1.getEntity();
                boolean var4 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.WEALTH) || this.isHoldingWealthGem(var2);
                if (var4) {
                   if (this.canUsePassives(var2)) {
                      int var5 = this.plugin.getGemManager().getTierFromOffhand(var2);
                      if (var5 == 0) {
                         ItemStack var6 = var2.getInventory().getItemInMainHand();
                         String var7 = CustomItemManager.getIdByItem(var6);
                         if (var7 != null) {
                            var5 = GemType.getTierFromOraxenId(var7);
                         }
                      }

                      if (var5 == 0) {
                         var5 = 1;
                      }

                      String var13 = var5 == 2 ? "tier2" : "tier1";
                      int var14 = this.plugin.getConfig().getInt("passives.wealth." + var13 + ".durability-chip-extra-damage", 1);
                      if (var3 instanceof Player) {
                         Player var8 = (Player)var3;
                         ItemStack[] var9 = var8.getInventory().getArmorContents();
                         boolean var10 = false;

                         for(int var11 = 0; var11 < var9.length; ++var11) {
                            if (var9[var11] != null && !var9[var11].getType().isAir() && var9[var11].getItemMeta() instanceof Damageable) {
                               Damageable var12 = (Damageable)var9[var11].getItemMeta();
                               var12.setDamage(var12.getDamage() + var14);
                               var9[var11].setItemMeta(var12);
                               var10 = true;
                            }
                         }

                         if (var10) {
                            var8.getInventory().setArmorContents(var9);
                            Particle.DustOptions var15 = new Particle.DustOptions(ParticleUtils.WEALTH_GREEN, 1.0F);
                            var8.getWorld().spawnParticle(Particle.DUST, var8.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 10, 0.3, 0.3, 0.3, (double)0.0F, var15, true);
                         }

                      }
                   }
                }
             }
          }
       }

       @EventHandler
       public void onWealthArmorMend(EntityDamageByEntityEvent var1) {
          if (var1.getDamager() instanceof Player) {
             Player var2 = (Player)var1.getDamager();
             if (var1.getEntity() instanceof LivingEntity) {
                if (var1.getEntity() != var2) {
                   boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.WEALTH) || this.isHoldingWealthGem(var2);
                   if (var3) {
                      if (this.canUsePassives(var2)) {
                         int var4 = this.plugin.getGemManager().getTierFromOffhand(var2);
                         if (var4 == 0) {
                            ItemStack var5 = var2.getInventory().getItemInMainHand();
                            String var6 = CustomItemManager.getIdByItem(var5);
                            if (var6 != null) {
                               var4 = GemType.getTierFromOraxenId(var6);
                            }
                         }

                         if (var4 == 0) {
                            var4 = 1;
                         }

                         String var12 = var4 == 2 ? "tier2" : "tier1";
                         int var13 = this.plugin.getConfig().getInt("passives.wealth." + var12 + ".armor-mend-amount", 2);
                         ItemStack[] var7 = var2.getInventory().getArmorContents();
                         boolean var8 = false;

                         for(int var9 = 0; var9 < var7.length; ++var9) {
                            if (var7[var9] != null && !var7[var9].getType().isAir() && var7[var9].getItemMeta() instanceof Damageable) {
                               Damageable var10 = (Damageable)var7[var9].getItemMeta();
                               int var11 = var10.getDamage();
                               if (var11 > 0) {
                                  var10.setDamage(Math.max(0, var11 - var13));
                                  var7[var9].setItemMeta(var10);
                                  var8 = true;
                               }
                            }
                         }

                         if (var8) {
                            var2.getInventory().setArmorContents(var7);
                            Particle.DustOptions var14 = new Particle.DustOptions(ParticleUtils.WEALTH_GREEN, 0.8F);
                            var2.getWorld().spawnParticle(Particle.DUST, var2.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 8, 0.3, 0.3, 0.3, (double)0.0F, var14, true);
                         }

                      }
                   }
                }
             }
          }
       }

       @EventHandler
       public void onWealthDoubleDebris(FurnaceExtractEvent var1) {
          Player var2 = var1.getPlayer();
          boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.WEALTH) || this.isHoldingWealthGem(var2);
          if (var3) {
             if (this.canUsePassives(var2)) {
                int var4 = this.plugin.getGemManager().getTierFromOffhand(var2);
                if (var4 == 0) {
                   ItemStack var5 = var2.getInventory().getItemInMainHand();
                   String var6 = CustomItemManager.getIdByItem(var5);
                   if (var6 != null) {
                      var4 = GemType.getTierFromOraxenId(var6);
                   }
                }

                if (var4 == 0) {
                   var4 = 1;
                }

                String var8 = var4 == 2 ? "tier2" : "tier1";
                boolean var9 = this.plugin.getConfig().getBoolean("passives.wealth." + var8 + ".double-debris", true);
                if (var9) {
                   if (var1.getItemType() == Material.NETHERITE_SCRAP) {
                      var2.getWorld().dropItemNaturally(var2.getLocation(), new ItemStack(Material.NETHERITE_SCRAP, 1));
                      Particle.DustOptions var7 = new Particle.DustOptions(ParticleUtils.WEALTH_GREEN, 1.2F);
                      var2.getWorld().spawnParticle(Particle.DUST, var2.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 20, 0.3, 0.3, 0.3, (double)0.0F, var7, true);
                      var2.playSound(var2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.2F);
                      var2.sendMessage("§a§oDouble Debris! Extra netherite scrap!");
                   }
                }
             }
          }
       }

       @EventHandler
       public void onUnfortunateAttack(EntityDamageByEntityEvent var1) {
          if (var1.getDamager() instanceof Player) {
             Player var2 = (Player)var1.getDamager();
             double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.attack-fail-chance", (double)0.75F);
             if (h.a(var2.getUniqueId(), var3)) {
                var1.setCancelled(true);
                var2.sendMessage("§c§oYou can't do that while Unfortunate!");
             }

          }
       }

       @EventHandler
       public void onUnfortunateBlockPlace(BlockPlaceEvent var1) {
          Player var2 = var1.getPlayer();
          double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.block-place-fail-chance", (double)1.0F);
          if (h.a(var2.getUniqueId(), var3)) {
             var1.setCancelled(true);
             var2.sendMessage("§c§oYou can't do that while Unfortunate!");
          }

       }

       @EventHandler
       public void onUnfortunateEat(PlayerItemConsumeEvent var1) {
          Player var2 = var1.getPlayer();
          double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.eat-fail-chance", (double)1.0F);
          if (h.a(var2.getUniqueId(), var3)) {
             var1.setCancelled(true);
             var2.sendMessage("§c§oYou can't do that while Unfortunate!");
          }

       }

       @EventHandler
       public void onUnfortunateBlockBreak(BlockBreakEvent var1) {
          Player var2 = var1.getPlayer();
          double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.block-break-fail-chance", (double)0.5F);
          if (h.a(var2.getUniqueId(), var3)) {
             var1.setCancelled(true);
             var2.sendMessage("§c§oYou can't do that while Unfortunate!");
          }

       }

       @EventHandler
       public void onUnfortunateSprint(PlayerToggleSprintEvent var1) {
          if (var1.isSprinting()) {
             Player var2 = var1.getPlayer();
             double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.sprint-fail-chance", (double)0.5F);
             if (h.a(var2.getUniqueId(), var3)) {
                var1.setCancelled(true);
                var2.sendMessage("§c§oYou can't do that while Unfortunate!");
             }

          }
       }

       @EventHandler
       public void onUnfortunateJump(PlayerMoveEvent var1) {
          Player var2 = var1.getPlayer();
          if (var1.getFrom().getY() < var1.getTo().getY() && var2.isOnGround()) {
             double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.jump-fail-chance", 0.3);
             if (h.a(var2.getUniqueId(), var3)) {
                var1.setTo(var1.getFrom());
                var2.sendMessage("§c§oYou can't do that while Unfortunate!");
             }
          }

       }

       @EventHandler
       public void onUnfortunateDropItem(PlayerDropItemEvent var1) {
          Player var2 = var1.getPlayer();
          double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.drop-item-fail-chance", 0.8);
          if (h.a(var2.getUniqueId(), var3)) {
             var1.setCancelled(true);
             var2.sendMessage("§c§oYou can't do that while Unfortunate!");
          }

       }

       @EventHandler
       public void onUnfortunatePickupItem(EntityPickupItemEvent var1) {
          if (var1.getEntity() instanceof Player) {
             Player var2 = (Player)var1.getEntity();
             double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.pickup-item-fail-chance", 0.7);
             if (h.a(var2.getUniqueId(), var3)) {
                var1.setCancelled(true);
                var2.sendMessage("§c§oYou can't do that while Unfortunate!");
             }

          }
       }

       @EventHandler
       public void onUnfortunateInteract(PlayerInteractEvent var1) {
          Player var2 = var1.getPlayer();
          double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.interact-fail-chance", 0.6);
          if (h.a(var2.getUniqueId(), var3)) {
             var1.setCancelled(true);
             var2.sendMessage("§c§oYou can't do that while Unfortunate!");
          }

       }

       @EventHandler
       public void onUnfortunateCraft(CraftItemEvent var1) {
          if (var1.getWhoClicked() instanceof Player) {
             Player var2 = (Player)var1.getWhoClicked();
             double var3 = this.plugin.getConfig().getDouble("passives.unfortunate.craft-fail-chance", 0.9);
             if (h.a(var2.getUniqueId(), var3)) {
                var1.setCancelled(true);
                var2.sendMessage("§c§oYou can't do that while Unfortunate!");
             }

          }
       }

       @EventHandler
       public void onItemLockUse(PlayerInteractEvent var1) {
          Player var2 = var1.getPlayer();
          if (h.c(var2.getUniqueId())) {
             ItemStack var3 = var2.getInventory().getItemInMainHand();
             ItemStack var4 = h.d(var2.getUniqueId());
             if (var4 != null && var3 != null) {
                if (var3.isSimilar(var4)) {
                   var1.setCancelled(true);
                   var2.sendMessage("§c§oThis item is locked!");
                }

             }
          }
       }

       @EventHandler
       public void onItemLockSwitch(PlayerItemHeldEvent var1) {
          Player var2 = var1.getPlayer();
          if (h.c(var2.getUniqueId())) {
             ItemStack var3 = var2.getInventory().getItem(var1.getNewSlot());
             ItemStack var4 = h.d(var2.getUniqueId());
             if (var4 != null && var3 != null) {
                if (var3.isSimilar(var4)) {
                   var2.sendMessage("§c§oWarning: This item is locked!");
                }

             }
          }
       }

       @EventHandler
       public void onStrengthChadHit(EntityDamageByEntityEvent var1) {
          if (var1.getDamager() instanceof Player) {
             Player var2 = (Player)var1.getDamager();
             if (var1.getEntity() instanceof LivingEntity) {
                boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.STRENGTH) || this.isHoldingStrengthGem(var2);
                if (var3) {
                   if (this.canUsePassives(var2)) {
                      int var4 = this.plugin.getGemManager().getTierFromOffhand(var2);
                      if (var4 == 0) {
                         ItemStack var5 = var2.getInventory().getItemInMainHand();
                         String var6 = CustomItemManager.getIdByItem(var5);
                         if (var6 != null) {
                            var4 = GemType.getTierFromOraxenId(var6);
                         }
                      }

                      if (var4 >= 2) {
                         boolean var10 = this.plugin.getCriticalHitManager().registerHit(var2);
                         if (var10) {
                            double var11 = this.plugin.getConfig().getDouble("abilities.strength-chad.bonus-damage", (double)7.0F);
                            var1.setDamage(var1.getDamage() + var11);
                            LivingEntity var8 = (LivingEntity)var1.getEntity();
                            Particle.DustOptions var9 = new Particle.DustOptions(ParticleUtils.STRENGTH_RED, 2.0F);
                            var8.getWorld().spawnParticle(Particle.DUST, var8.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 50, 0.8, 0.8, 0.8, (double)0.0F, var9, true);
                            var8.getWorld().spawnParticle(Particle.CRIT, var8.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F, 0.3);
                            var8.getWorld().spawnParticle(Particle.SWEEP_ATTACK, var8.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 10, (double)0.5F, (double)0.5F, (double)0.5F);
                            var8.getWorld().playSound(var8.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0F, 0.7F);
                            var2.sendMessage("§c§l⚔ CHAD STRENGTH! §c+3.5 hearts bonus damage!");
                         }

                      }
                   }
                }
             }
          }
       }

       @EventHandler
       public void onRichRushBlockBreak(BlockBreakEvent var1) {
          Player var2 = var1.getPlayer();
          if (h.a(var2.getUniqueId())) {
             if (!var1.isCancelled()) {
                Material var3 = var1.getBlock().getType();
                if (this.isOreBlock(var3)) {
                   for(ItemStack var6 : new ArrayList(var1.getBlock().getDrops(var2.getInventory().getItemInMainHand(), var2))) {
                      var1.getBlock().getWorld().dropItemNaturally(var1.getBlock().getLocation(), var6.clone());
                   }

                }
             }
          }
       }

       private boolean isOreBlock(Material var1) {
          return var1 == Material.COAL_ORE || var1 == Material.DEEPSLATE_COAL_ORE || var1 == Material.IRON_ORE || var1 == Material.DEEPSLATE_IRON_ORE || var1 == Material.GOLD_ORE || var1 == Material.DEEPSLATE_GOLD_ORE || var1 == Material.NETHER_GOLD_ORE || var1 == Material.COPPER_ORE || var1 == Material.DEEPSLATE_COPPER_ORE || var1 == Material.DIAMOND_ORE || var1 == Material.DEEPSLATE_DIAMOND_ORE || var1 == Material.EMERALD_ORE || var1 == Material.DEEPSLATE_EMERALD_ORE || var1 == Material.LAPIS_ORE || var1 == Material.DEEPSLATE_LAPIS_ORE || var1 == Material.REDSTONE_ORE || var1 == Material.DEEPSLATE_REDSTONE_ORE || var1 == Material.NETHER_QUARTZ_ORE || var1 == Material.ANCIENT_DEBRIS;
       }

       @EventHandler
       public void onRichRushEntityDeath(EntityDeathEvent var1) {
          Player var2 = var1.getEntity().getKiller();
          if (var2 != null) {
             if (h.a(var2.getUniqueId())) {
                EntityType var3 = var1.getEntity().getType();
                if (var3 != EntityType.DONKEY && var3 != EntityType.MULE && var3 != EntityType.LLAMA && var3 != EntityType.TRADER_LLAMA) {
                   for(ItemStack var6 : new ArrayList(var1.getDrops())) {
                      if (this.isNaturalMobDrop(var6)) {
                         var1.getDrops().add(var6.clone());
                      }
                   }

                }
             }
          }
       }

       private boolean isNaturalMobDrop(ItemStack var1) {
          if (var1 != null && !var1.getType().isAir()) {
             Material var2 = var1.getType();
             if (!var2.name().contains("HELMET") && !var2.name().contains("CHESTPLATE") && !var2.name().contains("LEGGINGS") && !var2.name().contains("BOOTS") && !var2.name().contains("SWORD") && !var2.name().contains("AXE") && !var2.name().contains("PICKAXE") && !var2.name().contains("SHOVEL") && !var2.name().contains("HOE") && !var2.name().contains("BOW") && !var2.name().contains("CROSSBOW") && !var2.name().contains("TRIDENT") && !var2.name().contains("SHIELD")) {
                if (!var2.name().contains("SHULKER") && !var2.name().contains("CHEST") && !var2.name().contains("BARREL") && !var2.name().contains("BUNDLE") && var2 != Material.SADDLE && !var2.name().contains("HORSE_ARMOR")) {
                   if (var1.hasItemMeta()) {
                      ItemMeta var3 = var1.getItemMeta();
                      if (var3.hasDisplayName() || var3.hasLore()) {
                         return false;
                      }
                   }

                   if (var2 != Material.ROTTEN_FLESH && var2 != Material.BONE && var2 != Material.SPIDER_EYE && var2 != Material.STRING && var2 != Material.GUNPOWDER && var2 != Material.ENDER_PEARL && var2 != Material.BLAZE_ROD && var2 != Material.GHAST_TEAR && var2 != Material.MAGMA_CREAM && var2 != Material.SLIME_BALL && var2 != Material.PRISMARINE_SHARD && var2 != Material.PRISMARINE_CRYSTALS && var2 != Material.RABBIT_HIDE && var2 != Material.RABBIT_FOOT && var2 != Material.PHANTOM_MEMBRANE && var2 != Material.NAUTILUS_SHELL) {
                      if (var2 != Material.BEEF && var2 != Material.PORKCHOP && var2 != Material.MUTTON && var2 != Material.CHICKEN && var2 != Material.RABBIT && var2 != Material.COD && var2 != Material.SALMON && var2 != Material.TROPICAL_FISH && var2 != Material.PUFFERFISH) {
                         if (var2 != Material.COOKED_BEEF && var2 != Material.COOKED_PORKCHOP && var2 != Material.COOKED_MUTTON && var2 != Material.COOKED_CHICKEN && var2 != Material.COOKED_RABBIT && var2 != Material.COOKED_COD && var2 != Material.COOKED_SALMON) {
                            if (var2 != Material.LEATHER && var2 != Material.FEATHER && var2 != Material.INK_SAC && var2 != Material.GLOW_INK_SAC) {
                               if (var2 != Material.NETHER_STAR && var2 != Material.WITHER_SKELETON_SKULL && var2 != Material.SKELETON_SKULL && var2 != Material.ZOMBIE_HEAD && var2 != Material.CREEPER_HEAD && var2 != Material.DRAGON_HEAD) {
                                  if (var2 != Material.TOTEM_OF_UNDYING && var2 != Material.ELYTRA && var2 != Material.DRAGON_BREATH) {
                                     if (var2 != Material.ARROW && var2 != Material.EGG && var2 != Material.SNOWBALL) {
                                        if (var2 != Material.ECHO_SHARD && var2 != Material.SCULK_CATALYST) {
                                           if (var2 == Material.SNIFFER_EGG) {
                                              return true;
                                           } else if (var2 != Material.OCHRE_FROGLIGHT && var2 != Material.PEARLESCENT_FROGLIGHT && var2 != Material.VERDANT_FROGLIGHT) {
                                              return false;
                                           } else {
                                              return true;
                                           }
                                        } else {
                                           return true;
                                        }
                                     } else {
                                        return true;
                                     }
                                  } else {
                                     return true;
                                  }
                               } else {
                                  return true;
                               }
                            } else {
                               return true;
                            }
                         } else {
                            return true;
                         }
                      } else {
                         return true;
                      }
                   } else {
                      return true;
                   }
                } else {
                   return false;
                }
             } else {
                return false;
             }
          } else {
             return false;
          }
       }

       @EventHandler
       public void onFluxFlowBlockBreak(BlockBreakEvent var1) {
          Player var2 = var1.getPlayer();
          if ((this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX) || this.isHoldingFluxGem(var2))) {
             if (this.canUsePassives(var2)) {
                this.plugin.getFlowStateManager().registerAction(var2, FlowStateManager.ActionType.BLOCK_BREAK);
             }
          }
       }

       @EventHandler
       public void onFluxFlowAttack(EntityDamageByEntityEvent var1) {
          if (var1.getDamager() instanceof Player) {
             Player var2 = (Player)var1.getDamager();
             if ((this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX) || this.isHoldingFluxGem(var2))) {
                if (this.canUsePassives(var2)) {
                   this.plugin.getFlowStateManager().registerAction(var2, FlowStateManager.ActionType.ATTACK);
                }
             }
          }
       }

       @EventHandler
       public void onFluxFlowArrowShoot(ProjectileLaunchEvent var1) {
          Projectile var2 = var1.getEntity();
          if (var2 instanceof Arrow) {
             ProjectileSource var3 = var2.getShooter();
             if (var3 instanceof Player) {
                Player var4 = (Player)var3;
                if ((this.plugin.getGemManager().hasGemTypeInOffhand(var4, GemType.FLUX) || this.isHoldingFluxGem(var4))) {
                   if (this.canUsePassives(var4)) {
                      this.plugin.getFlowStateManager().registerAction(var4, FlowStateManager.ActionType.ARROW_SHOOT);
                   }
                }
             }
          }
       }

       @EventHandler
       public void onFluxFlowSprint(PlayerToggleSprintEvent var1) {
          Player var2 = var1.getPlayer();
          if (var1.isSprinting()) {
             if ((this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX) || this.isHoldingFluxGem(var2))) {
                if (this.canUsePassives(var2)) {
                   this.plugin.getFlowStateManager().registerAction(var2, FlowStateManager.ActionType.SPRINT);
                }
             }
          }
       }

       @EventHandler
       public void onFluxFlowJump(PlayerMoveEvent var1) {
          Player var2 = var1.getPlayer();
          if (!(var1.getTo().getY() <= var1.getFrom().getY())) {
             if (var2.isOnGround()) {
                if ((this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX) || this.isHoldingFluxGem(var2))) {
                   if (this.canUsePassives(var2)) {
                      this.plugin.getFlowStateManager().registerAction(var2, FlowStateManager.ActionType.JUMP);
                   }
                }
             }
          }
       }

       private boolean canUsePassives(Player var1) {
          if (!this.plugin.getEnergyManager().arePassivesActive(var1)) {
             return false;
          } else {
             return this.plugin.getRegionManager() == null || !this.plugin.getRegionManager().a(var1);
          }
       }
    }

    // ===== Merged from separate source file: GemInteractListener.java =====
    public static class GemInteractListener implements Listener {
       private final BlissGems plugin;
       private final Map<UUID, Long> traderCooldowns;
       private final Map<UUID, Long> clickDisabledMessageCooldowns = new HashMap();
       private static final long CLICK_DISABLED_MSG_INTERVAL = 30000L;

       public GemInteractListener(BlissGems var1) {
          this.plugin = var1;
          this.traderCooldowns = new HashMap();
       }

       @EventHandler
       public void onPlayerInteract(PlayerInteractEvent var1) {
          Action var2 = var1.getAction();
          boolean var3 = var2 == Action.RIGHT_CLICK_AIR || var2 == Action.RIGHT_CLICK_BLOCK;
          boolean var4 = var2 == Action.LEFT_CLICK_AIR || var2 == Action.LEFT_CLICK_BLOCK;
          if (var3 || var4) {
             if (var4) {
                this.handleLeftClickAbility(var1);
             } else {
                Player var5 = var1.getPlayer();
                ItemStack var6 = var1.getItem();
                if (var6 != null) {
                   ItemStack var7 = var5.getInventory().getItemInMainHand();
                   ItemStack var8 = var5.getInventory().getItemInOffHand();
                   String var9 = CustomItemManager.getIdByItem(var7);
                   String var10 = CustomItemManager.getIdByItem(var8);
                   boolean var11 = var9 != null && GemType.isGem(var9);
                   boolean var12 = var10 != null && GemType.isGem(var10);
                   boolean var13 = var7 != null && var7.getType() == Material.BUNDLE;
                   boolean var14 = var8 != null && var8.getType() == Material.BUNDLE;
                   if ((!var11 || !var14) && (!var12 || !var13) && (!var11 || var6 == null || var6.getType() != Material.BUNDLE) && (!var12 || var6 == null || var6.getType() != Material.BUNDLE)) {
                      if (var6 == null || var6.getType() != Material.BUNDLE || !var11 && !var12) {
                         if (var6 != null && var6.getType() == Material.BUNDLE && this.bundleContainsGem(var6)) {
                            var1.setCancelled(true);
                            var5.sendMessage("§c§lThis bundle contains a gem! Remove the gem first before using it.");
                         } else {
                            String var19 = CustomItemManager.getIdByItem(var6);
                            if (var19 != null) {
                               switch (var19) {
                                  case "energy_bottle" -> this.handleEnergyBottle(var5, var6, var1);
                                  case "gem_trader" -> this.handleGemTrader(var5, var6, var1);
                               }

                               if (var19.endsWith("_gem_t1") || var19.endsWith("_gem_t2")) {
                                  this.handleGemAbility(var5, var19, var1);
                               }

                            }
                         }
                      } else {
                         var1.setCancelled(true);
                         String var18 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-bundle");
                         if (var18 != null && !var18.isEmpty()) {
                            var5.sendMessage(var18);
                         }

                      }
                   } else {
                      var1.setCancelled(true);
                      String var15 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-bundle");
                      if (var15 != null && !var15.isEmpty()) {
                         var5.sendMessage(var15);
                      }

                   }
                }
             }
          }
       }

       private void handleGemAbility(Player var1, String var2, PlayerInteractEvent var3) {
          var3.setCancelled(true);
          AbilityBinding var4 = AbilityBinding.rightClick(var1.isSneaking());
          this.dispatchBoundAbility(var1, var4);
       }

       private void handleLeftClickAbility(PlayerInteractEvent var1) {
          ItemStack var2 = var1.getItem();
          if (var2 != null) {
             String var3 = CustomItemManager.getIdByItem(var2);
             if (var3 != null && (var3.endsWith("_gem_t1") || var3.endsWith("_gem_t2"))) {
                Player var4 = var1.getPlayer();
                AbilityBinding var5 = AbilityBinding.leftClick(var4.isSneaking());
                AbilitySlot var6 = this.plugin.getAbilityBindingManager() != null ? this.plugin.getAbilityBindingManager().getSlot(var4, var5) : null;
                if (var6 != null) {
                   var1.setCancelled(true);
                   if (this.gateAbility(var4)) {
                      this.plugin.getBlissCommand().triggerSlot(var4, var6);
                   }
                }
             }
          }
       }

       private void dispatchBoundAbility(Player var1, AbilityBinding var2) {
          if (this.gateAbility(var1)) {
             AbilitySlot var3 = this.plugin.getAbilityBindingManager() != null ? this.plugin.getAbilityBindingManager().getSlot(var1, var2) : null;
             if (var3 != null) {
                this.plugin.getBlissCommand().triggerSlot(var1, var3);
             } else {
                String var4 = CustomItemManager.getIdByItem(var1.getInventory().getItemInMainHand());
                if (var4 == null || !var4.endsWith("_gem_t1") && !var4.endsWith("_gem_t2")) {
                   var4 = CustomItemManager.getIdByItem(var1.getInventory().getItemInOffHand());
                }

                if (var4 != null) {
                   int var5 = var4.endsWith("_gem_t2") ? 2 : 1;
                   GemType var6 = GemType.fromOraxenId(var4);
                   if (var6 != null) {
                      switch (var6) {
                         case ASTRA:
                            this.plugin.getAstraAbilities().a(var1, var5);
                            return;
                         case FIRE:
                            this.plugin.getFireAbilities().a(var1, var5);
                            return;
                         case FLUX:
                            this.plugin.getFluxAbilities().a(var1, var5);
                            return;
                         case LIFE:
                            this.plugin.getLifeAbilities().a(var1, var5);
                            return;
                         case PUFF:
                            this.plugin.getPuffAbilities().a(var1, var5);
                            return;
                         case SPEED:
                            this.plugin.getSpeedAbilities().a(var1, var5);
                            return;
                         case STRENGTH:
                            this.plugin.getStrengthAbilities().a(var1, var5);
                            return;
                         case WEALTH:
                            this.plugin.getWealthAbilities().a(var1, var5);
                            return;
                      }
                   }

                }
             }
          }
       }

       private boolean gateAbility(Player var1) {
          if (this.plugin.getClickActivationManager().isClickActivationEnabled(var1)) {
             int var6 = this.plugin.getEnergyManager().getEnergy(var1);
             if (var6 <= 0) {
                String var3 = this.plugin.getConfigManager().getFormattedMessage("no-energy");
                if (var3 != null && !var3.isEmpty()) {
                   var1.sendMessage(var3);
                }

                return false;
             } else {
                return true;
             }
          } else {
             long var2 = System.currentTimeMillis();
             Long var4 = (Long)this.clickDisabledMessageCooldowns.get(var1.getUniqueId());
             if (var4 == null || var2 - var4 >= 30000L) {
                this.clickDisabledMessageCooldowns.put(var1.getUniqueId(), var2);
                String var5 = this.plugin.getConfigManager().getFormattedMessage("click-activation-disabled");
                if (var5 != null && !var5.isEmpty()) {
                   var1.sendMessage(var5);
                }
             }

             return false;
          }
       }

       private void handleEnergyBottle(Player var1, ItemStack var2, PlayerInteractEvent var3) {
          var3.setCancelled(true);
          int var4 = this.plugin.getEnergyManager().getEnergy(var1);
          int var5 = this.plugin.getConfigManager().getMaxEnergy();
          if (var4 < var5) {
             this.plugin.getEnergyManager().addEnergy(var1, 1);
             if (var2.getAmount() > 1) {
                var2.setAmount(var2.getAmount() - 1);
             } else if (var3.getHand() == EquipmentSlot.OFF_HAND) {
                var1.getInventory().setItemInOffHand((ItemStack)null);
             } else {
                var1.getInventory().setItemInMainHand((ItemStack)null);
             }

             if (this.plugin.getConfigManager().isEnergyBottleDropEnabled()) {
                var1.playSound(var1.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                var1.spawnParticle(Particle.HEART, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 20, (double)0.5F, (double)0.5F, (double)0.5F);
             }

             String var7 = this.plugin.getConfigManager().getFormattedMessage("energy-bottle-consumed");
             if (var7 != null && !var7.isEmpty()) {
                var1.sendMessage(var7);
             }

          } else {
             String var6 = this.plugin.getConfigManager().getFormattedMessage("energy-already-max");
             if (var6 != null && !var6.isEmpty()) {
                var1.sendMessage(var6);
             } else {
                var1.sendMessage("§c§oYou already have maximum energy!");
             }

          }
       }

       private void handleGemTrader(Player var1, ItemStack var2, PlayerInteractEvent var3) {
          var3.setCancelled(true);
          long var6 = System.currentTimeMillis();
          Long var8 = (Long)this.traderCooldowns.get(var1.getUniqueId());
          int var9 = this.plugin.getConfigManager().getTraderCooldown();
          long var4;
          if (var8 != null && (var4 = var8 + (long)var9 * 1000L - var6) > 0L) {
             int var17 = (int)Math.ceil((double)var4 / (double)1000.0F);
             String var19 = this.plugin.getConfigManager().getFormattedMessage("trade-cooldown", "seconds", var17);
             if (var19 != null && !var19.isEmpty()) {
                var1.sendMessage(var19);
             }

          } else if (!this.plugin.getGemManager().hasActiveGem(var1)) {
             String var16 = this.plugin.getConfigManager().getFormattedMessage("no-gem");
             if (var16 != null && !var16.isEmpty()) {
                var1.sendMessage(var16);
             }

          } else {
             GemType var10 = this.plugin.getGemManager().getGemType(var1);
             if (var10 == null) {
                String var18 = this.plugin.getConfigManager().getFormattedMessage("no-gem");
                if (var18 != null && !var18.isEmpty()) {
                   var1.sendMessage(var18);
                }

             } else {
                ArrayList var11 = new ArrayList();

                for(GemType var15 : GemType.values()) {
                   if (var15 != var10 && this.plugin.getConfigManager().isGemEnabled(var15)) {
                      var11.add(var15);
                   }
                }

                if (var11.isEmpty()) {
                   var1.sendMessage(String.valueOf(ChatColor.RED) + "No other gem types available!");
                } else {
                   GemType var20 = (GemType)var11.get((int)(Math.random() * (double)var11.size()));
                   if (this.plugin.getGemManager().replaceGemType(var1, var20)) {
                      if (var2.getAmount() > 1) {
                         var2.setAmount(var2.getAmount() - 1);
                      } else {
                         var1.getInventory().setItemInMainHand((ItemStack)null);
                      }

                      this.traderCooldowns.put(var1.getUniqueId(), var6);
                      if (this.plugin.getAchievementManager() != null) {
                         this.plugin.getAchievementManager().unlock(var1, Achievement.TIME_FOR_A_CHANGE);
                      }

                      if (this.plugin.getConfigManager().shouldPlayTradeEffects()) {
                         var1.playSound(var1.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                         var1.spawnParticle(Particle.PORTAL, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F);
                      }

                      String var21 = this.plugin.getConfigManager().getFormattedMessage("trade-success", "gem", var20.getDisplayName());
                      if (var21 != null && !var21.isEmpty()) {
                         var1.sendMessage(var21);
                      }
                   } else {
                      var1.sendMessage(String.valueOf(ChatColor.RED) + "Failed to trade gem!");
                   }

                }
             }
          }
       }

       @EventHandler
       public void onInventoryClick(InventoryClickEvent var1) {
          HumanEntity var2 = var1.getWhoClicked();
          if (var2 instanceof Player var3) {
             ItemStack var4 = var1.getCurrentItem();
             ItemStack var5 = var1.getCursor();
             ItemStack var6 = null;
             if (var1.getClick().toString().contains("NUMBER_KEY")) {
                int var7 = var1.getHotbarButton();
                if (var7 >= 0 && var7 < 9) {
                   var6 = var3.getInventory().getItem(var7);
                }
             }

             PlayerInventory var16 = var3.getInventory();
             boolean var8 = var16.firstEmpty() == -1;
             if (var8 && var1.getClickedInventory() == var16) {
                String var9 = var4 != null ? CustomItemManager.getIdByItem(var4) : null;
                if (var9 != null && GemType.isGem(var9)) {
                   var1.setCancelled(true);
                   String var28 = this.plugin.getConfigManager().getFormattedMessage("cannot-move-gem-full-inventory");
                   if (var28 != null && !var28.isEmpty()) {
                      var3.sendMessage(var28);
                   }

                   return;
                }

                if (var5 != null && var5.getType() != Material.AIR && var4 != null) {
                   String var10 = CustomItemManager.getIdByItem(var4);
                   if (var10 != null && GemType.isGem(var10)) {
                      var1.setCancelled(true);
                      var3.sendMessage("§c§lYou cannot swap items with your gem when inventory is full!");
                      return;
                   }
                }
             }

             if (var1.getView().getTopInventory().getType() == InventoryType.CRAFTING) {
                if (var1.getClickedInventory() == var1.getView().getTopInventory()) {
                   int var17 = var1.getRawSlot();
                   if (var17 >= 1 && var17 <= 4) {
                      if (var5 != null && var5.getType() != Material.AIR) {
                         String var20 = CustomItemManager.getIdByItem(var5);
                         if (var20 != null && GemType.isGem(var20)) {
                            var1.setCancelled(true);
                            return;
                         }
                      }

                      if (var6 != null) {
                         String var21 = CustomItemManager.getIdByItem(var6);
                         if (var21 != null && GemType.isGem(var21)) {
                            var1.setCancelled(true);
                            return;
                         }
                      }
                   }
                }

                if (var1.isShiftClick() && var1.getClickedInventory() == var3.getInventory() && var4 != null) {
                   String var18 = CustomItemManager.getIdByItem(var4);
                   if (var18 != null && GemType.isGem(var18)) {
                      var1.setCancelled(true);
                      return;
                   }
                }
             }

             boolean var19 = var1.getView().getTopInventory() != null && var1.getView().getTopInventory().getHolder() != var3;
             if (var19) {
                if (var1.getClick().toString().equals("SWAP_OFFHAND")) {
                   ItemStack var22 = var3.getInventory().getItemInOffHand();
                   if (var22 != null) {
                      String var11 = CustomItemManager.getIdByItem(var22);
                      if (var11 != null && GemType.isGem(var11)) {
                         var1.setCancelled(true);
                         String var39 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-container");
                         if (var39 != null && !var39.isEmpty()) {
                            var3.sendMessage(var39);
                         }

                         return;
                      }
                   }
                }

                if (var1.getClickedInventory() != var3.getInventory() && var5 != null) {
                   String var23 = CustomItemManager.getIdByItem(var5);
                   if (var23 != null && GemType.isGem(var23)) {
                      var1.setCancelled(true);
                      String var33 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-container");
                      if (var33 != null && !var33.isEmpty()) {
                         var3.sendMessage(var33);
                      }

                      return;
                   }
                }

                if (var1.getClick().isShiftClick() && var1.getClickedInventory() == var3.getInventory() && var4 != null) {
                   String var24 = CustomItemManager.getIdByItem(var4);
                   if (var24 != null && GemType.isGem(var24)) {
                      var1.setCancelled(true);
                      String var32 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-container");
                      if (var32 != null && !var32.isEmpty()) {
                         var3.sendMessage(var32);
                      }

                      return;
                   }
                }

                if (var1.getClickedInventory() != var3.getInventory() && var6 != null) {
                   String var25 = CustomItemManager.getIdByItem(var6);
                   if (var25 != null && GemType.isGem(var25)) {
                      var1.setCancelled(true);
                      String var31 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-container");
                      if (var31 != null && !var31.isEmpty()) {
                         var3.sendMessage(var31);
                      }

                      return;
                   }
                }

                if (var1.getClick().toString().equals("DOUBLE_CLICK") && var5 != null) {
                   String var26 = CustomItemManager.getIdByItem(var5);
                   if (var26 != null && GemType.isGem(var26)) {
                      var1.setCancelled(true);
                      String var30 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-container");
                      if (var30 != null && !var30.isEmpty()) {
                         var3.sendMessage(var30);
                      }

                      return;
                   }
                }
             }

             boolean var27 = false;
             boolean var29 = false;
             if (var4 != null) {
                if (var4.getType() == Material.BUNDLE) {
                   var27 = true;
                   String var12 = var5 != null ? CustomItemManager.getIdByItem(var5) : null;
                   if (var12 != null && GemType.isGem(var12)) {
                      var1.setCancelled(true);
                      String var42 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-bundle");
                      if (var42 != null && !var42.isEmpty()) {
                         var3.sendMessage(var42);
                      }

                      return;
                   }
                }

                String var34 = CustomItemManager.getIdByItem(var4);
                if (var34 != null && GemType.isGem(var34)) {
                   var29 = true;
                   if (var5 != null && var5.getType() == Material.BUNDLE) {
                      var1.setCancelled(true);
                      String var41 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-bundle");
                      if (var41 != null && !var41.isEmpty()) {
                         var3.sendMessage(var41);
                      }

                      return;
                   }
                }
             }

             if (var5 != null) {
                if (var5.getType() == Material.BUNDLE) {
                   var27 = true;
                }

                String var35 = CustomItemManager.getIdByItem(var5);
                if (var35 != null && GemType.isGem(var35)) {
                   var29 = true;
                }
             }

             if (var6 != null) {
                if (var6.getType() == Material.BUNDLE && var29) {
                   var1.setCancelled(true);
                   String var38 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-bundle");
                   if (var38 != null && !var38.isEmpty()) {
                      var3.sendMessage(var38);
                   }

                   return;
                }

                String var36 = CustomItemManager.getIdByItem(var6);
                if (var36 != null && GemType.isGem(var36) && var27) {
                   var1.setCancelled(true);
                   String var40 = this.plugin.getConfigManager().getFormattedMessage("cannot-store-gem-bundle");
                   if (var40 != null && !var40.isEmpty()) {
                      var3.sendMessage(var40);
                   }

                   return;
                }
             }

             if (var5 != null && var1.getClickedInventory() == var3.getInventory()) {
                String var37 = CustomItemManager.getIdByItem(var5);
                if (var37 != null && GemType.isGem(var37)) {
                   int var13 = this.countGemsInInventory(var3);
                   if (var13 > 0) {
                      boolean var14 = false;
                      if (var4 != null) {
                         String var15 = CustomItemManager.getIdByItem(var4);
                         if (var15 != null && GemType.isGem(var15)) {
                            var14 = true;
                         }
                      }

                      if (!var14) {
                         var1.setCancelled(true);
                         String var43 = this.plugin.getConfigManager().getFormattedMessage("already-have-gem");
                         if (var43 != null && !var43.isEmpty()) {
                            var3.sendMessage(var43);
                         }

                         return;
                      }
                   }
                }
             }

             Bukkit.getScheduler().runTaskLater(this.plugin, () -> this.plugin.getGemManager().updateActiveGem(var3), 1L);
          }

       }

       @EventHandler(
          priority = EventPriority.HIGHEST
       )
       public void onEntityPickupItem(EntityPickupItemEvent var1) {
          if (var1.getEntity() instanceof Player) {
             Player var2 = (Player)var1.getEntity();
             Item var3 = var1.getItem();
             ItemStack var4 = var3.getItemStack();
             String var5 = CustomItemManager.getIdByItem(var4);
             if (var5 != null && GemType.isGem(var5) && this.countGemsInInventory(var2) > 0) {
                PlayerInventory var6 = var2.getInventory();
                if (var6.firstEmpty() == -1) {
                   var1.setCancelled(true);
                   return;
                }
             }

             if (var5 != null && GemType.isGem(var5)) {
                int var8 = this.countGemsInInventory(var2);
                if (var8 > 0) {
                   var1.setCancelled(true);
                   String var7 = this.plugin.getConfigManager().getFormattedMessage("already-have-gem");
                   if (var7 != null && !var7.isEmpty()) {
                      var2.sendMessage(var7);
                   }
                }

             }
          }
       }

       @EventHandler(
          priority = EventPriority.HIGHEST
       )
       public void onInventoryDrag(InventoryDragEvent var1) {
          if (var1.getWhoClicked() instanceof Player) {
             Player var2 = (Player)var1.getWhoClicked();
             ItemStack var3 = var1.getOldCursor();
             if (var3 != null) {
                String var4 = CustomItemManager.getIdByItem(var3);
                if (var4 != null && GemType.isGem(var4) && var1.getInventory().getType() != InventoryType.PLAYER) {
                   for(int var6 : var1.getRawSlots()) {
                      if (var6 < var1.getView().getTopInventory().getSize()) {
                         var1.setCancelled(true);
                         var2.sendMessage("§c§lYou cannot move gems to other containers!");
                         return;
                      }
                   }
                }
             }

             if (this.countGemsInInventory(var2) > 0) {
                PlayerInventory var7 = var2.getInventory();
                if (var7.firstEmpty() == -1) {
                   for(int var9 : var1.getRawSlots()) {
                      if (var9 >= var1.getView().getTopInventory().getSize()) {
                         var1.setCancelled(true);
                         var2.sendMessage("§c§lYour inventory is full! Cannot move items while carrying a gem.");
                         return;
                      }
                   }
                }
             }

          }
       }

       private int countGemsInInventory(Player var1) {
          int var2 = 0;

          for(ItemStack var6 : var1.getInventory().getContents()) {
             if (var6 != null) {
                String var7 = CustomItemManager.getIdByItem(var6);
                if (var7 != null && GemType.isGem(var7)) {
                   ++var2;
                } else if (var6.getType() == Material.BUNDLE && this.bundleContainsGem(var6)) {
                   ++var2;
                }
             }
          }

          return var2;
       }

       private boolean bundleContainsGem(ItemStack var1) {
          if (var1 != null && var1.getType() == Material.BUNDLE) {
             if (var1.getItemMeta() instanceof BundleMeta) {
                BundleMeta var2 = (BundleMeta)var1.getItemMeta();
                if (var2.hasItems()) {
                   for(ItemStack var4 : var2.getItems()) {
                      if (var4 != null) {
                         String var5 = CustomItemManager.getIdByItem(var4);
                         if (var5 != null && GemType.isGem(var5)) {
                            return true;
                         }
                      }
                   }
                }
             }

             return false;
          } else {
             return false;
          }
       }

       @EventHandler
       public void onPlayerInteractEntity(PlayerInteractEntityEvent var1) {
          Player var2 = var1.getPlayer();
          ItemStack var3 = var2.getInventory().getItemInMainHand();
          ItemStack var4 = var2.getInventory().getItemInOffHand();
          if (var1.getRightClicked() instanceof ItemFrame || var1.getRightClicked() instanceof Allay) {
             String var5 = CustomItemManager.getIdByItem(var3);
             String var6 = CustomItemManager.getIdByItem(var4);
             if (var5 != null && GemType.isGem(var5) || var6 != null && GemType.isGem(var6)) {
                var1.setCancelled(true);
                String var7 = this.plugin.getConfigManager().getFormattedMessage("cannot-place-gem-itemframe");
                if (var7 != null && !var7.isEmpty()) {
                   var2.sendMessage(var7);
                }
             }
          }

       }

       @EventHandler(
          priority = EventPriority.HIGHEST
       )
       public void onInventoryClose(InventoryCloseEvent var1) {
          if (var1.getPlayer() instanceof Player) {
             Player var2 = (Player)var1.getPlayer();
             if (var1.getView().getTitle().equals("§6§lPockets")) {
                this.plugin.getWealthAbilities().f(var2.getUniqueId());
             }

             if (var1.getView().getTopInventory().getType() == InventoryType.CRAFTING) {
                for(int var3 = 1; var3 <= 4; ++var3) {
                   ItemStack var4 = var1.getView().getTopInventory().getItem(var3);
                   if (var4 != null && !var4.getType().isAir()) {
                      String var5 = CustomItemManager.getIdByItem(var4);
                      if (var5 != null && GemType.isGem(var5)) {
                         var1.getView().getTopInventory().setItem(var3, (ItemStack)null);
                         this.forceGemIntoInventory(var2, var4);
                      }
                   }
                }
             }

             ItemStack var6 = var2.getItemOnCursor();
             if (var6 != null && var6.getType() != Material.AIR) {
                String var7 = CustomItemManager.getIdByItem(var6);
                if (var7 != null && GemType.isGem(var7)) {
                   var2.setItemOnCursor((ItemStack)null);
                   this.forceGemIntoInventory(var2, var6);
                }
             }

             this.enforceOneGemOnly(var2);
          }
       }

       private void enforceOneGemOnly(Player var1) {
          PlayerInventory var2 = var1.getInventory();
          boolean var3 = false;
          ArrayList var4 = new ArrayList();

          for(int var5 = 0; var5 < var2.getSize(); ++var5) {
             ItemStack var6 = var2.getItem(var5);
             if (var6 != null && var6.getType() != Material.AIR) {
                String var7 = CustomItemManager.getIdByItem(var6);
                if (var7 != null && GemType.isGem(var7)) {
                   if (!var3) {
                      var3 = true;
                   } else {
                      var4.add(var6.clone());
                      var2.setItem(var5, (ItemStack)null);
                   }
                } else if (var6.getType() == Material.BUNDLE) {
                   List var8 = this.extractGemsFromBundle(var6, var5, var2);
                   if (!var8.isEmpty()) {
                      for(ItemStack var10 : var8) {
                         if (!var3) {
                            var3 = true;
                            this.forceGemIntoInventory(var1, var10);
                         } else {
                            var4.add(var10);
                         }
                      }
                   }
                }
             }
          }

          if (!var4.isEmpty()) {
             for(ItemStack var12 : var4) {
                var1.getWorld().dropItemNaturally(var1.getLocation(), var12);
             }

             var1.sendMessage("§c§lYou can only have one gem! Extra gems have been dropped.");
          }

       }

       private List<ItemStack> extractGemsFromBundle(ItemStack var1, int var2, PlayerInventory var3) {
          ArrayList var4 = new ArrayList();
          if (var1 != null && var1.getType() == Material.BUNDLE) {
             if (var1.getItemMeta() instanceof BundleMeta) {
                BundleMeta var5 = (BundleMeta)var1.getItemMeta();
                if (var5.hasItems()) {
                   ArrayList var6 = new ArrayList();

                   for(ItemStack var8 : var5.getItems()) {
                      if (var8 != null) {
                         String var9 = CustomItemManager.getIdByItem(var8);
                         if (var9 != null && GemType.isGem(var9)) {
                            var4.add(var8.clone());
                         } else {
                            var6.add(var8);
                         }
                      }
                   }

                   if (!var4.isEmpty()) {
                      var5.setItems(var6);
                      var1.setItemMeta(var5);
                      var3.setItem(var2, var1);
                   }
                }
             }

             return var4;
          } else {
             return var4;
          }
       }

       private void forceGemIntoInventory(Player var1, ItemStack var2) {
          PlayerInventory var3 = var1.getInventory();
          int var4 = var3.firstEmpty();
          if (var4 != -1) {
             var3.setItem(var4, var2);
          } else {
             for(int var5 = 9; var5 < 36; ++var5) {
                ItemStack var6 = var3.getItem(var5);
                if (var6 != null && var6.getType() != Material.AIR) {
                   String var7 = CustomItemManager.getIdByItem(var6);
                   if (var7 == null || !GemType.isGem(var7)) {
                      ItemStack var8 = var6.clone();
                      var3.setItem(var5, var2);
                      var1.getWorld().dropItemNaturally(var1.getLocation(), var8);
                      String var9 = this.plugin.getConfigManager().getFormattedMessage("gem-forced-into-inventory");
                      if (var9 != null && !var9.isEmpty()) {
                         var1.sendMessage(var9);
                      }

                      return;
                   }
                }
             }

             for(int var10 = 0; var10 < 9; ++var10) {
                ItemStack var12 = var3.getItem(var10);
                if (var12 != null && var12.getType() != Material.AIR) {
                   String var13 = CustomItemManager.getIdByItem(var12);
                   if (var13 == null || !GemType.isGem(var13)) {
                      ItemStack var14 = var12.clone();
                      var3.setItem(var10, var2);
                      var1.getWorld().dropItemNaturally(var1.getLocation(), var14);
                      String var15 = this.plugin.getConfigManager().getFormattedMessage("gem-forced-into-inventory");
                      if (var15 != null && !var15.isEmpty()) {
                         var1.sendMessage(var15);
                      }

                      return;
                   }
                }
             }

             var3.setItem(0, var2);
             String var11 = this.plugin.getConfigManager().getFormattedMessage("gem-forced-into-hotbar");
             if (var11 != null && !var11.isEmpty()) {
                var1.sendMessage(var11);
             }

          }
       }

       public void triggerAbilityViaCommand(Player var1, boolean var2) {
          ItemStack var3 = var1.getInventory().getItemInMainHand();
          ItemStack var4 = var1.getInventory().getItemInOffHand();
          String var5 = CustomItemManager.getIdByItem(var3);
          boolean var6 = var5 != null && (GemType.isGem(var5) || this.plugin.getGemRegistry() != null && this.plugin.getGemRegistry().isRegisteredGem(var5));
          if (!var6) {
             var5 = CustomItemManager.getIdByItem(var4);
             var6 = var5 != null && (GemType.isGem(var5) || this.plugin.getGemRegistry() != null && this.plugin.getGemRegistry().isRegisteredGem(var5));
             if (!var6) {
                var1.sendMessage("§c§lYou must be holding a gem to use this command!");
                return;
             }
          }

          int var7 = this.plugin.getEnergyManager().getEnergy(var1);
          if (var7 <= 0) {
             String var14 = this.plugin.getConfigManager().getFormattedMessage("no-energy");
             if (var14 != null && !var14.isEmpty()) {
                var1.sendMessage(var14);
             }

          } else {
             GemRegistryImpl var8 = this.plugin.getGemRegistry();
             String var9 = var8 != null ? var8.gemIdFromItemId(var5) : null;
             int var10 = var8 != null ? var8.tierFromItemId(var5) : (var5.endsWith("_gem_t2") ? 2 : 1);
             if (var2 && var10 < 2) {
                var1.sendMessage("§c§lSecondary abilities require Tier 2 gem!");
             } else {
                GemType var11 = GemType.fromOraxenId(var5);
                if (var11 != null) {
                   boolean var15 = var1.isSneaking();
                   if (var2 && !var15) {
                      var1.sendMessage("§e§oShift + use ability or right-click for secondary ability!");
                   } else {
                      switch (var11) {
                         case ASTRA -> this.plugin.getAstraAbilities().a(var1, var10);
                         case FIRE -> this.plugin.getFireAbilities().a(var1, var10);
                         case FLUX -> this.plugin.getFluxAbilities().a(var1, var10);
                         case LIFE -> this.plugin.getLifeAbilities().a(var1, var10);
                         case PUFF -> this.plugin.getPuffAbilities().a(var1, var10);
                         case SPEED -> this.plugin.getSpeedAbilities().a(var1, var10);
                         case STRENGTH -> this.plugin.getStrengthAbilities().a(var1, var10);
                         case WEALTH -> this.plugin.getWealthAbilities().a(var1, var10);
                      }

                   }
                } else {
                   if (var9 != null && var8 != null) {
                      GemAbilityHandler var12 = var8.getAbilityHandler(var9);
                      if (var12 != null) {
                         if (var2) {
                            var12.onSecondary(var1, var10);
                         } else {
                            var12.onPrimary(var1, var10);
                         }
                      }
                   }

                }
             }
          }
       }
    }

    // ===== Merged from separate source file: EnhancedGuiManager.java =====
    public static class EnhancedGuiManager implements Listener {
       private final BlissGems plugin;
       private final Map<UUID, Integer> playerPage = new HashMap();
       private final Map<UUID, Integer> playerSubPage = new HashMap();
       private static final String PLAYER_GUI_TITLE = "§5§lBlissGems Menu";
       private static final String ADMIN_GUI_TITLE = "§c§l[ADMIN] BlissGems Control";
       private static final String ADMIN_GEMS_TITLE = "§9§l[ADMIN] Enabled Gems";
       private static final String ADMIN_SETTINGS_TITLE = "§6§l[ADMIN] Settings";
       private static final String ADMIN_GEMOPS_TITLE = "§d§l[ADMIN] Gem Operations";
       private static final String ADMIN_ENERGY_TITLE = "§c§l[ADMIN] Energy Control";
       private static final String ADMIN_PLAYERS_TITLE = "§a§l[ADMIN] Player Management";
       private static final String ADMIN_CONFIG_EDITOR_TITLE = "§e§l[ADMIN] Config Editor";

       public EnhancedGuiManager(BlissGems var1) {
          this.plugin = var1;
       }

       public void openMainMenu(Player var1) {
          if (var1.hasPermission("blissgems.admin")) {
             this.openAdminDashboard(var1, 0);
          } else {
             this.openPlayerDashboard(var1);
          }

       }

       private void openPlayerDashboard(Player var1) {
          Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, "§5§lBlissGems Menu");
          var2.setItem(11, this.createGemInfoItem(var1));
          var2.setItem(13, this.createEnergyInfoItem(var1));
          if (var1.hasPermission("blissgems.admin")) {
             var2.setItem(15, this.createRerollItem(var1));
          }

          var2.setItem(20, this.createAbilitiesItem(var1));
          var2.setItem(22, this.createPassivesItem(var1));
          var2.setItem(24, this.createCooldownsItem(var1));
          var2.setItem(29, this.createTrustedItem(var1));
          var2.setItem(31, this.createStatsItem(var1));
          var2.setItem(33, this.createSettingsItem(var1));
          var1.openInventory(var2);
          this.playerPage.put(var1.getUniqueId(), 0);
       }

       private void openAdminDashboard(Player var1, int var2) {
          Inventory var3 = Bukkit.createInventory((InventoryHolder)null, 54, "§c§l[ADMIN] BlissGems Control");
          var3.setItem(10, this.createPlayersControlItem());
          var3.setItem(12, this.createEnabledGemsItem());
          var3.setItem(14, this.createSettingsControlItem());
          var3.setItem(16, this.createGemOpsItem());
          var3.setItem(19, this.createEnergyControlItem());
          var3.setItem(21, this.createReloadConfigItem());
          var3.setItem(23, this.createConfigEditorItem());
          var1.openInventory(var3);
       }

       private ItemStack createGemInfoItem(Player var1) {
          GemType var2 = this.plugin.getGemManager().getGemType(var1);
          int var3 = this.plugin.getGemManager().getGemTier(var1);
          ArrayList var5 = new ArrayList();
          ItemStack var4;
          if (var2 != null) {
             var4 = new ItemStack(Material.ECHO_SHARD);
             ItemMeta var6 = var4.getItemMeta();
             var6.setDisplayName("§d§lYour Gem");
             var5.add("§7Type: §f" + var2.getDisplayName());
             var5.add("§7Tier: §f" + var3);
             var5.add("");
             var5.add("§7Description:");
             String[] var7 = var2.getDescription().split("\n");

             for(String var11 : var7) {
                var5.add("§8" + var11);
             }

             var6.setLore(var5);
             var4.setItemMeta(var6);
          } else {
             var4 = new ItemStack(Material.BARRIER);
             ItemMeta var12 = var4.getItemMeta();
             var12.setDisplayName("§c§lNo Gem");
             var5.add("§7You don't have a gem equipped!");
             var12.setLore(var5);
             var4.setItemMeta(var12);
          }

          return var4;
       }

       private ItemStack createEnergyInfoItem(Player var1) {
          int var2 = this.plugin.getEnergyManager().getEnergy(var1);
          EnergyState var3 = this.plugin.getEnergyManager().getEnergyState(var1);
          ItemStack var4 = new ItemStack(Material.NETHER_STAR);
          ItemMeta var5 = var4.getItemMeta();
          var5.setDisplayName("§b§lEnergy Status");
          ArrayList var6 = new ArrayList();
          var6.add("§7Energy: §f" + var2 + "§8/§f10");
          var6.add("§7State: §f" + var3.getDisplayName());
          var6.add("");
          StringBuilder var7 = new StringBuilder("§8[");

          for(int var8 = 0; var8 < 10; ++var8) {
             if (var8 < var2) {
                var7.append("§a■");
             } else {
                var7.append("§7■");
             }
          }

          var7.append("§8]");
          var6.add(var7.toString());
          var6.add("");
          if (var2 == 0) {
             var6.add("§c✘ Abilities disabled");
             var6.add("§c✘ Passives disabled");
          } else if (var2 == 1) {
             var6.add("§a✔ Abilities enabled");
             var6.add("§c✘ Passives disabled");
          } else {
             var6.add("§a✔ Abilities enabled");
             var6.add("§a✔ Passives enabled");
          }

          var5.setLore(var6);
          var4.setItemMeta(var5);
          return var4;
       }

       private ItemStack createRerollItem(Player var1) {
          ItemStack var2 = new ItemStack(Material.TRIPWIRE_HOOK);
          ItemMeta var3 = var2.getItemMeta();
          var3.setDisplayName("§e§l\ud83c\udfb0 SPIN FOR NEW GEM");
          ArrayList var4 = new ArrayList();
          var4.add("§7Click to reroll your gem!");
          var4.add("§7Cost: 2 Energy");
          var4.add("§7You get random gem (no repeats)");
          var3.setLore(var4);
          var2.setItemMeta(var3);
          return var2;
       }

       private ItemStack createAbilitiesItem(Player var1) {
          ItemStack var2 = new ItemStack(Material.BLAZE_ROD);
          ItemMeta var3 = var2.getItemMeta();
          var3.setDisplayName("§c§lAbilities");
          ArrayList var4 = new ArrayList();
          GemType var5 = this.plugin.getGemManager().getGemType(var1);
          if (var5 != null) {
             var4.add("§7T1: §f" + var5.getDisplayName());
             var4.add("§7T2: §fAvailable");
          } else {
             var4.add("§7No gem equipped");
          }

          var4.add("§8Click for details");
          var3.setLore(var4);
          var2.setItemMeta(var3);
          return var2;
       }

       private ItemStack createPassivesItem(Player var1) {
          ItemStack var2 = new ItemStack(Material.AMETHYST_SHARD);
          ItemMeta var3 = var2.getItemMeta();
          var3.setDisplayName("§6§lPassive Effects");
          ArrayList var4 = new ArrayList();
          GemType var5 = this.plugin.getGemManager().getGemType(var1);
          if (var5 != null) {
             var4.add("§7Gem: §f" + var5.getDisplayName());
             var4.add("§7Status: §aActive");
          } else {
             var4.add("§7No passives");
          }

          var3.setLore(var4);
          var2.setItemMeta(var3);
          return var2;
       }

       private ItemStack createCooldownsItem(Player var1) {
          ItemStack var2 = new ItemStack(Material.REPEATER);
          ItemMeta var3 = var2.getItemMeta();
          var3.setDisplayName("§3§lCooldowns");
          ArrayList var4 = new ArrayList();
          var4.add("§7Track your ability");
          var4.add("§7cooldown timers");
          var4.add("§8Click for details");
          var3.setLore(var4);
          var2.setItemMeta(var3);
          return var2;
       }

       private ItemStack createTrustedItem(Player var1) {
          ItemStack var2 = new ItemStack(Material.EMERALD);
          ItemMeta var3 = var2.getItemMeta();
          var3.setDisplayName("§2§lTrusted Players");
          int var4 = this.plugin.getTrustedPlayersManager().getTrustedPlayers(var1).size();
          ArrayList var5 = new ArrayList();
          var5.add("§7Count: §f" + var4);
          var5.add("§8Use /bliss trust <player>");
          var3.setLore(var5);
          var2.setItemMeta(var3);
          return var2;
       }

       private ItemStack createStatsItem(Player var1) {
          ItemStack var2 = new ItemStack(Material.GOLDEN_APPLE);
          ItemMeta var3 = var2.getItemMeta();
          var3.setDisplayName("§6§lYour Stats");
          int var4 = this.plugin.getStatsManager().getKills(var1);
          ArrayList var5 = new ArrayList();
          var5.add("§7Kills: §f" + var4);
          var5.add("§8Type /bliss stats");
          var3.setLore(var5);
          var2.setItemMeta(var3);
          return var2;
       }

       private ItemStack createSettingsItem(Player var1) {
          ItemStack var2 = new ItemStack(Material.COMPARATOR);
          ItemMeta var3 = var2.getItemMeta();
          var3.setDisplayName("§e§lSettings");
          ArrayList var4 = new ArrayList();
          boolean var5 = this.plugin.getClickActivationManager().isClickActivationEnabled(var1);
          var4.add("§7Click Activation: " + (var5 ? "§aEnabled" : "§cDisabled"));
          var4.add("§8Use /bliss toggle_click");
          var3.setLore(var4);
          var2.setItemMeta(var3);
          return var2;
       }

       private ItemStack createPlayersControlItem() {
          ItemStack var1 = new ItemStack(Material.PLAYER_HEAD);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName("§a§l[Admin] Players");
          ArrayList var3 = new ArrayList();
          var3.add("§7Manage player gems & energy");
          var3.add("§7(Click for player list)");
          var2.setLore(var3);
          var1.setItemMeta(var2);
          return var1;
       }

       private ItemStack createEnabledGemsItem() {
          ItemStack var1 = new ItemStack(Material.DIAMOND);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName("§9§l[Admin] Enabled Gems");
          ArrayList var3 = new ArrayList();
          var3.add("§7Toggle gems on/off");
          var3.add("§7(Click to see status)");
          var2.setLore(var3);
          var1.setItemMeta(var2);
          return var1;
       }

       private ItemStack createSettingsControlItem() {
          ItemStack var1 = new ItemStack(Material.REPEATING_COMMAND_BLOCK);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName("§6§l[Admin] Settings");
          ArrayList var3 = new ArrayList();
          var3.add("§7Max Energy: 10");
          var3.add("§7Starting Energy: 5");
          var3.add("§7(Click to adjust)");
          var2.setLore(var3);
          var1.setItemMeta(var2);
          return var1;
       }

       private ItemStack createGemOpsItem() {
          ItemStack var1 = new ItemStack(Material.SHULKER_BOX);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName("§d§l[Admin] Gem Ops");
          ArrayList var3 = new ArrayList();
          var3.add("§7Give items:");
          var3.add("§7- Energy Bottles");
          var3.add("§7- Reroll Tokens");
          var2.setLore(var3);
          var1.setItemMeta(var2);
          return var1;
       }

       private ItemStack createEnergyControlItem() {
          ItemStack var1 = new ItemStack(Material.REDSTONE_BLOCK);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName("§c§l[Admin] Energy Control");
          ArrayList var3 = new ArrayList();
          var3.add("§7Global energy settings");
          var3.add("§7Scale all players by 1.5x");
          var3.add("§7Or set all to X energy");
          var2.setLore(var3);
          var1.setItemMeta(var2);
          return var1;
       }

       private ItemStack createReloadConfigItem() {
          ItemStack var1 = new ItemStack(Material.LEVER);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName("§b§l[Admin] Reload Config");
          ArrayList var3 = new ArrayList();
          var3.add("§7Reload config.yml");
          var3.add("§7All changes apply");
          var2.setLore(var3);
          var1.setItemMeta(var2);
          return var1;
       }

       private ItemStack createConfigEditorItem() {
          ItemStack var1 = new ItemStack(Material.WRITABLE_BOOK);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName("§e§l[Admin] Config Editor");
          ArrayList var3 = new ArrayList();
          var3.add("§7Edit config values");
          var3.add("§7in-game!");
          var3.add("§aClick to open editor");
          var2.setLore(var3);
          var1.setItemMeta(var2);
          return var1;
       }

       private void openEnabledGemsMenu(Player var1) {
          Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, "§9§l[ADMIN] Enabled Gems");
          var2.setItem(10, this.createGemToggleItem(GemType.FIRE));
          var2.setItem(11, this.createGemToggleItem(GemType.SPEED));
          var2.setItem(12, this.createGemToggleItem(GemType.WEALTH));
          var2.setItem(13, this.createGemToggleItem(GemType.ASTRA));
          var2.setItem(14, this.createGemToggleItem(GemType.PUFF));
          var2.setItem(15, this.createGemToggleItem(GemType.FLUX));
          var2.setItem(16, this.createGemToggleItem(GemType.LIFE));
          var2.setItem(19, this.createGemToggleItem(GemType.STRENGTH));
          var2.setItem(49, this.createBackButton());
          var1.openInventory(var2);
       }

       private void openSettingsControl(Player var1) {
          Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, "§6§l[ADMIN] Settings");
          int var3 = this.plugin.getConfig().getInt("energy.max-energy", 10);
          int var4 = this.plugin.getConfig().getInt("energy.starting-energy", 10);
          int var5 = this.plugin.getConfig().getInt("energy.gain-on-kill", 1);
          int var6 = this.plugin.getConfig().getInt("energy.loss-on-death", 1);
          var2.setItem(11, this.createSettingItem("Max Energy", var3, "Maximum energy capacity"));
          var2.setItem(13, this.createSettingItem("Starting Energy", var4, "Energy for new gems"));
          var2.setItem(15, this.createSettingItem("Gain on Kill", var5, "Energy gained per kill"));
          var2.setItem(20, this.createSettingItem("Loss on Death", var6, "Energy lost on death"));
          var2.setItem(49, this.createBackButton());
          var1.openInventory(var2);
       }

       private void openGemOpsMenu(Player var1) {
          Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, "§d§l[ADMIN] Gem Operations");
          var2.setItem(11, this.createGiveItemButton("Energy Bottle", Material.HONEY_BOTTLE, "Give 1 energy bottle"));
          var2.setItem(13, this.createGiveItemButton("Repair Kit", Material.RECOVERY_COMPASS, "Give 1 repair kit"));
          var2.setItem(15, this.createGiveItemButton("Upgrader", Material.NETHER_STAR, "Give 1 upgrader"));
          var2.setItem(20, this.createGiveItemButton("Trader", Material.EMERALD, "Give 1 trader"));
          var2.setItem(22, this.createGiveItemButton("Gem Fragment", Material.PRISMARINE_SHARD, "Give 1 gem fragment"));
          var2.setItem(49, this.createBackButton());
          var1.openInventory(var2);
       }

       private void openEnergyControl(Player var1) {
          Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, "§c§l[ADMIN] Energy Control");
          var2.setItem(11, this.createEnergyOpButton("Set All to 10", "§aSet everyone to max energy"));
          var2.setItem(13, this.createEnergyOpButton("Set All to 5", "§eSet everyone to half energy"));
          var2.setItem(15, this.createEnergyOpButton("Add 1 to All", "§bGive everyone +1 energy"));
          var2.setItem(20, this.createEnergyOpButton("Remove 1 from All", "§cTake -1 energy from everyone"));
          var2.setItem(49, this.createBackButton());
          var1.openInventory(var2);
       }

       private void openPlayersControl(Player var1) {
          Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, "§a§l[ADMIN] Player Management");
          ArrayList var3 = new ArrayList(Bukkit.getOnlinePlayers());
          int var4 = 0;

          for(int var5 = 0; var5 < Math.min(45, var3.size()); ++var5) {
             Player var6 = (Player)var3.get(var5);
             var2.setItem(var4++, this.createPlayerManageItem(var6));
          }

          var2.setItem(49, this.createBackButton());
          var1.openInventory(var2);
       }

       private void openConfigEditor(Player var1) {
          Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, "§e§l[ADMIN] Config Editor");
          var2.setItem(10, this.createEditableConfigItem("Max Energy", "energy.max-energy", 10));
          var2.setItem(11, this.createEditableConfigItem("Starting Energy", "energy.starting-energy", 10));
          var2.setItem(12, this.createEditableConfigItem("Gain on Kill", "energy.gain-on-kill", 1));
          var2.setItem(13, this.createEditableConfigItem("Loss on Death", "energy.loss-on-death", 1));
          var2.setItem(19, this.createEditableConfigItem("Astra Daggers", "abilities.cooldowns.astra-daggers", 15));
          var2.setItem(20, this.createEditableConfigItem("Fire Fireball", "abilities.cooldowns.fire-fireball", 10));
          var2.setItem(21, this.createEditableConfigItem("Flux Ground", "abilities.cooldowns.flux-ground", 45));
          var2.setItem(22, this.createEditableConfigItem("Life Drainer", "abilities.cooldowns.life-heart-drainer", 60));
          var2.setItem(23, this.createEditableConfigItem("Puff Dash", "abilities.cooldowns.puff-dash", 5));
          var2.setItem(24, this.createEditableConfigItem("Speed Sedative", "abilities.cooldowns.speed-sedative", 35));
          var2.setItem(25, this.createEditableConfigItem("Strength Thorns", "abilities.cooldowns.strength-bloodthorns", 20));
          var2.setItem(28, this.createEditableConfigItem("Astral Projection", "abilities.cooldowns.astra-projection", 120));
          var2.setItem(29, this.createEditableConfigItem("Fire Campfire", "abilities.cooldowns.fire-campfire", 60));
          var2.setItem(30, this.createEditableConfigItem("Flux Beam", "abilities.cooldowns.flux-beam", 240));
          var2.setItem(31, this.createEditableConfigItem("Circle of Life", "abilities.cooldowns.life-circle-of-life", 60));
          var2.setItem(32, this.createEditableConfigItem("Breezy Bash", "abilities.cooldowns.puff-breezy-bash", 10));
          var2.setItem(33, this.createEditableConfigItem("Adrenaline Rush", "abilities.cooldowns.adrenaline-rush", 90));
          var2.setItem(34, this.createEditableConfigItem("Speed Storm", "abilities.cooldowns.speed-storm", 45));
          var2.setItem(37, this.createEditableConfigItem("Frailer", "abilities.cooldowns.strength-frailer", 25));
          var2.setItem(38, this.createEditableConfigItem("Chad Strength", "abilities.cooldowns.strength-chad", 30));
          var2.setItem(39, this.createEditableConfigItem("Durability Chip", "abilities.cooldowns.wealth-durability-chip", 30));
          var2.setItem(40, this.createEditableConfigItem("Unfortunate", "abilities.cooldowns.wealth-unfortunate", 90));
          var2.setItem(41, this.createEditableConfigItem("Rich Rush", "abilities.cooldowns.wealth-rich-rush", 540));
          var2.setItem(42, this.createEditableConfigItem("Amplification", "abilities.cooldowns.wealth-amplification", 180));
          var2.setItem(49, this.createBackButton());
          var1.openInventory(var2);
       }

       private ItemStack createGemToggleItem(GemType var1) {
          FileConfiguration var10000 = this.plugin.getConfig();
          String var10001 = var1.name().toLowerCase();
          boolean var2 = var10000.getBoolean("gems.enabled." + var10001, true);
          ItemStack var3 = new ItemStack(var2 ? Material.LIME_DYE : Material.GRAY_DYE);
          ItemMeta var4 = var3.getItemMeta();
          var4.setDisplayName((var2 ? "§a" : "§7") + var1.getDisplayName());
          ArrayList var5 = new ArrayList();
          var5.add("§7Status: " + (var2 ? "§aEnabled" : "§cDisabled"));
          var5.add("");
          var5.add("§eClick to toggle!");
          var4.setLore(var5);
          var3.setItemMeta(var4);
          return var3;
       }

       private ItemStack createSettingItem(String var1, int var2, String var3) {
          ItemStack var4 = new ItemStack(Material.COMPARATOR);
          ItemMeta var5 = var4.getItemMeta();
          var5.setDisplayName("§6" + var1);
          ArrayList var6 = new ArrayList();
          var6.add("§7Current: §f" + var2);
          var6.add("§7" + var3);
          var6.add("");
          var6.add("§8(Read-only - use Config Editor)");
          var5.setLore(var6);
          var4.setItemMeta(var5);
          return var4;
       }

       private ItemStack createEditableConfigItem(String var1, String var2, int var3) {
          int var4 = this.plugin.getConfig().getInt(var2, var3);
          ItemStack var5 = new ItemStack(Material.PAPER);
          ItemMeta var6 = var5.getItemMeta();
          var6.setDisplayName("§e" + var1);
          ArrayList var7 = new ArrayList();
          var7.add("§7Current: §f" + var4);
          var7.add("§7Path: §8" + var2);
          var7.add("");
          var7.add("§aLeft-Click: §7+1");
          var7.add("§aShift Left-Click: §7+10");
          var7.add("§cRight-Click: §7-1");
          var7.add("§cShift Right-Click: §7-10");
          var6.setLore(var7);
          var5.setItemMeta(var6);
          return var5;
       }

       private ItemStack createGiveItemButton(String var1, Material var2, String var3) {
          ItemStack var4 = new ItemStack(var2);
          ItemMeta var5 = var4.getItemMeta();
          var5.setDisplayName("§d" + var1);
          ArrayList var6 = new ArrayList();
          var6.add("§7" + var3);
          var6.add("");
          var6.add("§eLeft-click: Give to yourself");
          var6.add("§eRight-click: Give to all online");
          var5.setLore(var6);
          var4.setItemMeta(var5);
          return var4;
       }

       private ItemStack createEnergyOpButton(String var1, String var2) {
          ItemStack var3 = new ItemStack(Material.REDSTONE);
          ItemMeta var4 = var3.getItemMeta();
          var4.setDisplayName("§c" + var1);
          ArrayList var5 = new ArrayList();
          var5.add(var2);
          var5.add("");
          var5.add("§eClick to execute!");
          var4.setLore(var5);
          var3.setItemMeta(var4);
          return var3;
       }

       private ItemStack createPlayerManageItem(Player var1) {
          ItemStack var2 = new ItemStack(Material.PLAYER_HEAD);
          ItemMeta var3 = var2.getItemMeta();
          var3.setDisplayName("§e" + var1.getName());
          GemType var4 = this.plugin.getGemManager().getGemType(var1);
          int var5 = this.plugin.getEnergyManager().getEnergy(var1);
          ArrayList var6 = new ArrayList();
          String var10001 = var4 != null ? var4.getDisplayName() : "None";
          var6.add("§7Gem: §f" + var10001);
          var6.add("§7Energy: §f" + var5 + "§8/§f10");
          var6.add("");
          var6.add("§eLeft-click: Manage player");
          var3.setLore(var6);
          var2.setItemMeta(var3);
          return var2;
       }

       private ItemStack createBackButton() {
          ItemStack var1 = new ItemStack(Material.BARRIER);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName("§c« Back");
          ArrayList var3 = new ArrayList();
          var3.add("§7Return to main menu");
          var2.setLore(var3);
          var1.setItemMeta(var2);
          return var1;
       }

       private ItemStack createBorderItem(Player var1) {
          GemType var2 = this.plugin.getGemManager().getGemType(var1);
          Material var3 = Material.GRAY_STAINED_GLASS_PANE;
          if (var2 != null) {
             Material var10000;
             switch (var2) {
                case FIRE -> var10000 = Material.RED_STAINED_GLASS_PANE;
                case SPEED -> var10000 = Material.YELLOW_STAINED_GLASS_PANE;
                case WEALTH -> var10000 = Material.CYAN_STAINED_GLASS_PANE;
                case ASTRA -> var10000 = Material.MAGENTA_STAINED_GLASS_PANE;
                case PUFF -> var10000 = Material.LIME_STAINED_GLASS_PANE;
                case FLUX -> var10000 = Material.BLUE_STAINED_GLASS_PANE;
                case LIFE -> var10000 = Material.PINK_STAINED_GLASS_PANE;
                case STRENGTH -> var10000 = Material.ORANGE_STAINED_GLASS_PANE;
                default -> throw new IncompatibleClassChangeError();
             }

             var3 = var10000;
          }

          ItemStack var4 = new ItemStack(var3);
          ItemMeta var5 = var4.getItemMeta();
          var5.setDisplayName(" ");
          var4.setItemMeta(var5);
          return var4;
       }

       private ItemStack createAdminBorderItem() {
          ItemStack var1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
          ItemMeta var2 = var1.getItemMeta();
          var2.setDisplayName(" ");
          var1.setItemMeta(var2);
          return var1;
       }

       @EventHandler
       public void onInventoryClick(InventoryClickEvent var1) {
          String var2 = var1.getView().getTitle();
          Player var3 = (Player)var1.getWhoClicked();
          if (var2.equals("§5§lBlissGems Menu") || var2.equals("§c§l[ADMIN] BlissGems Control") || var2.equals("§9§l[ADMIN] Enabled Gems") || var2.equals("§6§l[ADMIN] Settings") || var2.equals("§d§l[ADMIN] Gem Operations") || var2.equals("§c§l[ADMIN] Energy Control") || var2.equals("§a§l[ADMIN] Player Management") || var2.equals("§e§l[ADMIN] Config Editor")) {
             var1.setCancelled(true);
             int var4 = var1.getSlot();
             boolean var5 = var1.getClick().isRightClick();
             boolean var6 = var1.getClick().isShiftClick();
             if (var2.equals("§c§l[ADMIN] BlissGems Control")) {
                this.handleAdminDashboardClick(var3, var4);
             } else if (var2.equals("§5§lBlissGems Menu")) {
                this.handlePlayerGuiClick(var3, var4);
             } else if (var2.equals("§9§l[ADMIN] Enabled Gems")) {
                this.handleEnabledGemsClick(var3, var4);
             } else if (var2.equals("§6§l[ADMIN] Settings")) {
                this.handleSettingsClick(var3, var4);
             } else if (var2.equals("§d§l[ADMIN] Gem Operations")) {
                this.handleGemOpsClick(var3, var4, var5);
             } else if (var2.equals("§c§l[ADMIN] Energy Control")) {
                this.handleEnergyControlClick(var3, var4);
             } else if (var2.equals("§a§l[ADMIN] Player Management")) {
                this.handlePlayersClick(var3, var4);
             } else if (var2.equals("§e§l[ADMIN] Config Editor")) {
                this.handleConfigEditorClick(var3, var4, var5, var6);
             }
          }

       }

       private void handlePlayerGuiClick(Player var1, int var2) {
          switch (var2) {
             case 15:
                this.handleRerollGem(var1);
             case 16:
             case 17:
             case 18:
             case 19:
             case 21:
             case 22:
             case 23:
             case 25:
             case 26:
             case 27:
             case 28:
             case 30:
             case 32:
             default:
                break;
             case 20:
                var1.closeInventory();
                var1.sendMessage("§c§l⚔ ABILITIES §r§7- Check /bliss for ability commands!");
                var1.sendMessage("§7Your gem abilities can be activated by right-clicking your gem.");
                break;
             case 24:
                var1.closeInventory();
                var1.sendMessage("§3§l⏱ COOLDOWNS §r§7- Hold your gem to see cooldowns in action bar!");
                break;
             case 29:
                var1.closeInventory();
                var1.performCommand("bliss trusted");
                break;
             case 31:
                var1.closeInventory();
                var1.performCommand("bliss stats me");
                break;
             case 33:
                this.handleSettingsToggle(var1);
          }

       }

       private void handleRerollGem(Player var1) {
          if (!var1.hasPermission("blissgems.admin")) {
             var1.sendMessage("§c§lNo Permission! §7Only admins can reroll gems.");
             var1.playSound(var1.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
          } else {
             int var2 = this.plugin.getEnergyManager().getEnergy(var1);
             if (var2 < 2) {
                var1.sendMessage("§c§lInsufficient Energy! §7You need §c2 energy §7to reroll.");
                var1.playSound(var1.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
             } else {
                GemType var3 = this.plugin.getGemManager().getGemType(var1);
                if (var3 == null) {
                   var1.sendMessage("§c§lNo gem found! §7You need a gem to reroll.");
                   var1.playSound(var1.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                } else {
                   GemType[] var4 = GemType.values();

                   GemType var5;
                   do {
                      var5 = var4[(new Random()).nextInt(var4.length)];
                   } while(var5 == var3);

                   boolean var6 = this.plugin.getGemManager().replaceGemType(var1, var5);
                   if (!var6) {
                      var1.sendMessage("§c§lReroll failed! §7Could not replace gem.");
                      var1.playSound(var1.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
                   } else {
                      this.plugin.getEnergyManager().setEnergy(var1, var2 - 2);
                      var1.closeInventory();
                      var1.sendMessage("§a§l✔ GEM REROLLED!");
                      var1.sendMessage("§7New Gem: §f" + var5.getDisplayName());
                      var1.sendMessage("§7Energy: §c" + (var2 - 2) + " §8(-2)");
                      var1.playSound(var1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
                      var1.playSound(var1.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.2F);
                      var1.spawnParticle(Particle.TOTEM_OF_UNDYING, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 50, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
                      var1.spawnParticle(Particle.ENCHANT, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 100, 0.8, (double)1.0F, 0.8);
                   }
                }
             }
          }
       }

       private void handleSettingsToggle(Player var1) {
          boolean var2 = this.plugin.getClickActivationManager().isClickActivationEnabled(var1);
          this.plugin.getClickActivationManager().setClickActivation(var1, !var2);
          var1.sendMessage("§e§l⚙ SETTINGS");
          var1.sendMessage("§7Click Activation: " + (!var2 ? "§aEnabled" : "§cDisabled"));
          var1.playSound(var1.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
          this.openPlayerDashboard(var1);
       }

       private void handleAdminDashboardClick(Player var1, int var2) {
          switch (var2) {
             case 10:
                this.openPlayersControl(var1);
             case 11:
             case 13:
             case 15:
             case 17:
             case 18:
             case 20:
             case 22:
             default:
                break;
             case 12:
                this.openEnabledGemsMenu(var1);
                break;
             case 14:
                this.openSettingsControl(var1);
                break;
             case 16:
                this.openGemOpsMenu(var1);
                break;
             case 19:
                this.openEnergyControl(var1);
                break;
             case 21:
                var1.closeInventory();
                this.plugin.reloadConfig();
                this.plugin.getConfigManager().reload();
                var1.sendMessage("§a§l✔ Config reloaded successfully!");
                var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0F, 1.5F);
                break;
             case 23:
                this.openConfigEditor(var1);
          }

       }

       private void handleEnabledGemsClick(Player var1, int var2) {
          if (var2 == 49) {
             this.openMainMenu(var1);
          } else {
             GemType var3 = null;
             switch (var2) {
                case 10:
                   var3 = GemType.FIRE;
                   break;
                case 11:
                   var3 = GemType.SPEED;
                   break;
                case 12:
                   var3 = GemType.WEALTH;
                   break;
                case 13:
                   var3 = GemType.ASTRA;
                   break;
                case 14:
                   var3 = GemType.PUFF;
                   break;
                case 15:
                   var3 = GemType.FLUX;
                   break;
                case 16:
                   var3 = GemType.LIFE;
                case 17:
                case 18:
                default:
                   break;
                case 19:
                   var3 = GemType.STRENGTH;
             }

             if (var3 != null) {
                String var10000 = var3.name().toLowerCase();
                String var4 = "gems.enabled." + var10000;
                boolean var5 = this.plugin.getConfig().getBoolean(var4, true);
                this.plugin.getConfig().set(var4, !var5);
                this.plugin.saveConfig();
                String var10001 = var3.getDisplayName();
                var1.sendMessage("§e" + var10001 + " §7is now " + (!var5 ? "§aEnabled" : "§cDisabled"));
                var1.playSound(var1.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, !var5 ? 1.5F : 0.8F);
                this.openEnabledGemsMenu(var1);
             }

          }
       }

       private void handleSettingsClick(Player var1, int var2) {
          if (var2 == 49) {
             this.openMainMenu(var1);
          } else {
             var1.sendMessage("§7Settings are read-only. Use Config Editor to change values.");
             var1.playSound(var1.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5F, 1.0F);
          }
       }

       private void handleGemOpsClick(Player var1, int var2, boolean var3) {
          if (var2 == 49) {
             this.openMainMenu(var1);
          } else {
             String var4 = null;
             String var5 = null;
             switch (var2) {
                case 11:
                   var4 = "energy_bottle";
                   var5 = "Energy Bottle";
                case 12:
                case 14:
                case 16:
                case 17:
                case 18:
                case 19:
                case 21:
                default:
                   break;
                case 13:
                   var4 = "repair_kit";
                   var5 = "Repair Kit";
                   break;
                case 15:
                   var4 = "gem_upgrader";
                   var5 = "Upgrader";
                   break;
                case 20:
                   var4 = "gem_trader";
                   var5 = "Trader";
                   break;
                case 22:
                   var4 = "gem_fragment";
                   var5 = "Gem Fragment";
             }

             if (var4 != null) {
                if (var3) {
                   int var6 = 0;

                   for(Player var8 : Bukkit.getOnlinePlayers()) {
                      ItemStack var9 = CustomItemManager.getItemById(var4);
                      if (var9 != null) {
                         var8.getInventory().addItem(new ItemStack[]{var9});
                         var8.sendMessage("§a§l✔ Received " + var5 + " from admin!");
                         ++var6;
                      }
                   }

                   var1.sendMessage("§a§l✔ Gave " + var5 + " to " + var6 + " players!");
                   var1.playSound(var1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
                } else {
                   ItemStack var10 = CustomItemManager.getItemById(var4);
                   if (var10 != null) {
                      var1.getInventory().addItem(new ItemStack[]{var10});
                      var1.sendMessage("§a§l✔ Received " + var5 + "!");
                      var1.playSound(var1.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.5F);
                   }
                }
             }

          }
       }

       private void handleEnergyControlClick(Player var1, int var2) {
          if (var2 == 49) {
             this.openMainMenu(var1);
          } else {
             int var3 = 0;
             String var4 = "";
             switch (var2) {
                case 11:
                   for(Player var14 : Bukkit.getOnlinePlayers()) {
                      this.plugin.getEnergyManager().setEnergy(var14, 10);
                      ++var3;
                   }

                   var4 = "Set all players to 10 energy";
                case 12:
                case 14:
                case 16:
                case 17:
                case 18:
                case 19:
                default:
                   break;
                case 13:
                   for(Player var13 : Bukkit.getOnlinePlayers()) {
                      this.plugin.getEnergyManager().setEnergy(var13, 5);
                      ++var3;
                   }

                   var4 = "Set all players to 5 energy";
                   break;
                case 15:
                   for(Player var12 : Bukkit.getOnlinePlayers()) {
                      int var16 = this.plugin.getEnergyManager().getEnergy(var12);
                      this.plugin.getEnergyManager().setEnergy(var12, Math.min(10, var16 + 1));
                      ++var3;
                   }

                   var4 = "Added +1 energy to all players";
                   break;
                case 20:
                   for(Player var6 : Bukkit.getOnlinePlayers()) {
                      int var7 = this.plugin.getEnergyManager().getEnergy(var6);
                      this.plugin.getEnergyManager().setEnergy(var6, Math.max(0, var7 - 1));
                      ++var3;
                   }

                   var4 = "Removed -1 energy from all players";
             }

             if (!var4.isEmpty()) {
                var1.sendMessage("§c§l⚡ ENERGY CONTROL");
                var1.sendMessage("§7" + var4 + " §8(§f" + var3 + " §7players)");
                var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0F, 1.5F);

                for(Player var15 : Bukkit.getOnlinePlayers()) {
                   var15.sendMessage("§c§l⚡ Energy updated by admin!");
                }
             }

          }
       }

       private void handlePlayersClick(Player var1, int var2) {
          if (var2 == 49) {
             this.openMainMenu(var1);
          } else {
             if (var2 < 45) {
                ArrayList var3 = new ArrayList(Bukkit.getOnlinePlayers());
                if (var2 < var3.size()) {
                   Player var4 = (Player)var3.get(var2);
                   var1.closeInventory();
                   var1.sendMessage("§a§l» Managing: §f" + var4.getName());
                   var1.sendMessage("§7Use commands:");
                   var1.sendMessage("§e/bliss give " + var4.getName() + " <gem> [tier]");
                   var1.sendMessage("§e/bliss energy " + var4.getName() + " set <amount>");
                   var1.playSound(var1.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                }
             }

          }
       }

       private void handleConfigEditorClick(Player var1, int var2, boolean var3, boolean var4) {
          if (var2 == 49) {
             this.openMainMenu(var1);
          } else {
             String var5 = null;
             short var6 = 0;
             String var7 = "";
             switch (var2) {
                case 10:
                   var5 = "energy.max-energy";
                   var6 = 10;
                   var7 = "Max Energy";
                   break;
                case 11:
                   var5 = "energy.starting-energy";
                   var6 = 10;
                   var7 = "Starting Energy";
                   break;
                case 12:
                   var5 = "energy.gain-on-kill";
                   var6 = 1;
                   var7 = "Gain on Kill";
                   break;
                case 13:
                   var5 = "energy.loss-on-death";
                   var6 = 1;
                   var7 = "Loss on Death";
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 26:
                case 27:
                case 35:
                case 36:
                default:
                   break;
                case 19:
                   var5 = "abilities.cooldowns.astra-daggers";
                   var6 = 15;
                   var7 = "Astra Daggers";
                   break;
                case 20:
                   var5 = "abilities.cooldowns.fire-fireball";
                   var6 = 10;
                   var7 = "Fire Fireball";
                   break;
                case 21:
                   var5 = "abilities.cooldowns.flux-ground";
                   var6 = 45;
                   var7 = "Flux Ground";
                   break;
                case 22:
                   var5 = "abilities.cooldowns.life-heart-drainer";
                   var6 = 60;
                   var7 = "Life Drainer";
                   break;
                case 23:
                   var5 = "abilities.cooldowns.puff-dash";
                   var6 = 5;
                   var7 = "Puff Dash";
                   break;
                case 24:
                   var5 = "abilities.cooldowns.speed-sedative";
                   var6 = 35;
                   var7 = "Speed Sedative";
                   break;
                case 25:
                   var5 = "abilities.cooldowns.strength-bloodthorns";
                   var6 = 20;
                   var7 = "Strength Thorns";
                   break;
                case 28:
                   var5 = "abilities.cooldowns.astra-projection";
                   var6 = 120;
                   var7 = "Astral Projection";
                   break;
                case 29:
                   var5 = "abilities.cooldowns.fire-campfire";
                   var6 = 60;
                   var7 = "Fire Campfire";
                   break;
                case 30:
                   var5 = "abilities.cooldowns.flux-beam";
                   var6 = 240;
                   var7 = "Flux Beam";
                   break;
                case 31:
                   var5 = "abilities.cooldowns.life-circle-of-life";
                   var6 = 60;
                   var7 = "Circle of Life";
                   break;
                case 32:
                   var5 = "abilities.cooldowns.puff-breezy-bash";
                   var6 = 10;
                   var7 = "Breezy Bash";
                   break;
                case 33:
                   var5 = "abilities.cooldowns.adrenaline-rush";
                   var6 = 90;
                   var7 = "Adrenaline Rush";
                   break;
                case 34:
                   var5 = "abilities.cooldowns.speed-storm";
                   var6 = 45;
                   var7 = "Speed Storm";
                   break;
                case 37:
                   var5 = "abilities.cooldowns.strength-frailer";
                   var6 = 25;
                   var7 = "Frailer";
                   break;
                case 38:
                   var5 = "abilities.cooldowns.strength-chad";
                   var6 = 30;
                   var7 = "Chad Strength";
                   break;
                case 39:
                   var5 = "abilities.cooldowns.wealth-durability-chip";
                   var6 = 30;
                   var7 = "Durability Chip";
                   break;
                case 40:
                   var5 = "abilities.cooldowns.wealth-unfortunate";
                   var6 = 90;
                   var7 = "Unfortunate";
                   break;
                case 41:
                   var5 = "abilities.cooldowns.wealth-rich-rush";
                   var6 = 540;
                   var7 = "Rich Rush";
                   break;
                case 42:
                   var5 = "abilities.cooldowns.wealth-amplification";
                   var6 = 180;
                   var7 = "Amplification";
             }

             if (var5 != null) {
                int var8 = this.plugin.getConfig().getInt(var5, var6);
                int var9 = 0;
                if (var3) {
                   var9 = var4 ? -10 : -1;
                } else {
                   var9 = var4 ? 10 : 1;
                }

                int var10 = Math.max(0, var8 + var9);
                this.plugin.getConfig().set(var5, var10);
                this.plugin.saveConfig();
                var1.sendMessage("§e§l⚙ CONFIG EDITOR");
                var1.sendMessage("§7" + var7 + ": §f" + var8 + " §7→ §f" + var10 + " §8(" + (var9 >= 0 ? "+" : "") + var9 + ")");
                var1.playSound(var1.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, var9 >= 0 ? 1.2F : 0.8F);
                this.openConfigEditor(var1);
             }

          }
       }

       @EventHandler
       public void onInventoryClose(InventoryCloseEvent var1) {
          Player var2 = (Player)var1.getPlayer();
          this.playerPage.remove(var2.getUniqueId());
          this.playerSubPage.remove(var2.getUniqueId());
       }
    }
}