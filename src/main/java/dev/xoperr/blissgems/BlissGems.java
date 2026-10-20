package dev.xoperr.blissgems;

import com.destroystokyo.paper.event.server.ServerExceptionEvent;
import com.destroystokyo.paper.exception.ServerException;
import com.destroystokyo.paper.exception.ServerPluginException;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_a.a;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_a.b;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_a.c;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_a.d;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_a.e;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_a.f;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_a.g;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_a.h;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_api.BlissGemsAPI;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_api.CooldownEntry;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_api.GemAbilityHandler;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_api.GemDefinition;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_api.GemPassiveHandler;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_api.GemRegistry;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_commands.BlissCommand;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_commands.FixHeartsCommand;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.c;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.d;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.e;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_libs_faststats_bukkit.BukkitMetrics;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_libs_faststats_core.Metrics;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_libs_faststats_core.SimpleMetrics;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_libs_faststats_core_concurrent.TrackingBase;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_libs_faststats_core_concurrent.TrackingExecutors;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_libs_faststats_core_concurrent.TrackingThreadFactory;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_libs_faststats_core_concurrent.TrackingThreadPoolExecutor;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_libs_faststats_core_data.Metric;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.AutoEnchantListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.ComprehensiveGemProtectionListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.GemInteractListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.KillTrackingListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.PassiveListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.PlayerDeathListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.PlayerJoinListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.RepairKitListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.ReviveBeaconListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.StunListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.SwapHandAbilityListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.TeleportListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.UpgraderListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_listeners.VillagerTradeListener;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.AbilityBindingManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.AbilityManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.AchievementManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.ClickActivationManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.CooldownDisplayManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.CriticalHitManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.EnergyManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.EnhancedGuiManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.FlowStateManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.GemManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.GemRegistryImpl;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.GemRitualManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.PassiveManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.PluginMessagingManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.RecipeManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.RepairKitManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.ReviveBeaconManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.SoulManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.StatsManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_managers.TrustedPlayersManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_utils.AbilityBinding;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_utils.AbilitySlot;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_utils.Achievement;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_utils.ConfigManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_utils.CustomItemManager;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_utils.EnergyState;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_utils.GemType;
import dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_utils.ParticleUtils;
import dev.xoperr.blissgems.BlissGems.BlissGems;
import dev.xoperr.blissgems.BlissGems.P_org_a_a_a.f;
import dev.xoperr.blissgems.BlissGems.P_org_b_a.a;
import dev.xoperr.blissgems.BlissGems.P_org_b_a.b;
import dev.xoperr.blissgems.BlissGems.P_org_b_a.e;
import dev.xoperr.blissgems.BlissGems.P_org_b_a.g;
import dev.xoperr.blissgems.BlissGems.P_org_b_a.k;
import dev.xoperr.blissgems.BlissGems.P_org_b_a.o;
import dev.xoperr.blissgems.BlissGems.P_org_b_a.t;
import dev.xoperr.blissgems.BlissGems.P_org_c_a.d;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.BanList.Type;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;



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
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.c protectionManager;
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.b particleManager;
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.e textManager;
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.a autoEnchantManager;
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.d regionManager;
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
         this.protectionManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.c(this);
      } catch (Exception var40) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ProtectionManager ===");
         this.getLogger().severe(var40.getMessage());
         var40.printStackTrace();
      }

      try {
         this.particleManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.b(this);
      } catch (Exception var39) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ParticleManager ===");
         this.getLogger().severe(var39.getMessage());
         var39.printStackTrace();
      }

      try {
         this.textManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.e(this);
      } catch (Exception var38) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: TextManager ===");
         this.getLogger().severe(var38.getMessage());
         var38.printStackTrace();
      }

      try {
         this.autoEnchantManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.a(this);
      } catch (Exception var37) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: AutoEnchantManager ===");
         this.getLogger().severe(var37.getMessage());
         var37.printStackTrace();
      }

      try {
         this.regionManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.d(this);
      } catch (Exception var36) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: RegionManager ===");
         this.getLogger().severe(var36.getMessage());
         var36.printStackTrace();
      }

      try {
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_c.a.a(this.protectionManager);
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.b.a(this.particleManager);
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_e.a.a(this.textManager);
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_a.a.a(this.autoEnchantManager);
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_d.a.a(this.regionManager);
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

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.c getProtectionManager() {
      return this.protectionManager;
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.b getParticleManager() {
      return this.particleManager;
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.e getTextManager() {
      return this.textManager;
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.a getAutoEnchantManager() {
      return this.autoEnchantManager;
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.d getRegionManager() {
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

    // ---------- root package classes ----------


static public class BlissGems extends JavaPlugin implements BlissGemsAPI {
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
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.c protectionManager;
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.b particleManager;
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.e textManager;
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.a autoEnchantManager;
   private dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.d regionManager;
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
         this.protectionManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.c(this);
      } catch (Exception var40) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ProtectionManager ===");
         this.getLogger().severe(var40.getMessage());
         var40.printStackTrace();
      }

      try {
         this.particleManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.b(this);
      } catch (Exception var39) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: ParticleManager ===");
         this.getLogger().severe(var39.getMessage());
         var39.printStackTrace();
      }

      try {
         this.textManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.e(this);
      } catch (Exception var38) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: TextManager ===");
         this.getLogger().severe(var38.getMessage());
         var38.printStackTrace();
      }

      try {
         this.autoEnchantManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.a(this);
      } catch (Exception var37) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: AutoEnchantManager ===");
         this.getLogger().severe(var37.getMessage());
         var37.printStackTrace();
      }

      try {
         this.regionManager = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.d(this);
      } catch (Exception var36) {
         this.getLogger().severe("=== BLISSGEMS FAILED TO INITIALIZE: RegionManager ===");
         this.getLogger().severe(var36.getMessage());
         var36.printStackTrace();
      }

      try {
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_c.a.a(this.protectionManager);
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.b.a(this.particleManager);
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_e.a.a(this.textManager);
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_a.a.a(this.autoEnchantManager);
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_d.a.a(this.regionManager);
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

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.c getProtectionManager() {
      return this.protectionManager;
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.b getParticleManager() {
      return this.particleManager;
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.e getTextManager() {
      return this.textManager;
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.a getAutoEnchantManager() {
      return this.autoEnchantManager;
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.d getRegionManager() {
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
}
    // ---------- other packages ----------
    static final class P_org_b_a {


static public final class a {
   private a() {
      throw new AssertionError("ApiStatus should not be instantiated");
   }

   @Documented
   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
   public @interface a {
      String a();
   }

   @Documented
   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
   public @interface b {
   }

   @Documented
   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
   public @interface c {
   }

   @Documented
   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.TYPE, ElementType.METHOD})
   public @interface d {
   }

   @Documented
   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.TYPE, ElementType.METHOD})
   public @interface e {
   }

   @Documented
   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
   public @interface f {
      String a() default "";
   }
}


static public final class b {
   private b() {
      throw new AssertionError("Async should not be instantiated");
   }

   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
   public @interface a {
   }

   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
   public @interface b {
   }
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
static public @interface c {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.TYPE_USE})
static public @interface d {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
static public @interface e {
   @k String a() default "";

   boolean b() default false;

   @k String c() default "";
}


static public final class f {
   private f() {
      throw new AssertionError("Debug should not be instantiated");
   }

   @Target({ElementType.TYPE})
   @Retention(RetentionPolicy.CLASS)
   public @interface a {
      @dev.xoperr.blissgems.BlissGems.P_org_a_a_a.d(
         a = "JAVA",
         b = "class Renderer{String $text(){return ",
         c = ";}}"
      )
      @k String a() default "";

      @dev.xoperr.blissgems.BlissGems.P_org_a_a_a.d(
         a = "JAVA",
         b = "class Renderer{Object[] $childrenArray(){return ",
         c = ";}}"
      )
      @k String b() default "";

      @dev.xoperr.blissgems.BlissGems.P_org_a_a_a.d(
         a = "JAVA",
         b = "class Renderer{boolean $hasChildren(){return ",
         c = ";}}"
      )
      @k String c() default "";
   }
}


@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
static public @interface g {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PACKAGE})
static public @interface h {
   a a() default h.a.NotSpecified;

   public static enum a {
      NotSpecified,
      Title,
      Sentence;
   }
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
static public @interface i {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.TYPE_USE})
static public @interface j {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PACKAGE})
static public @interface k {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
static public @interface l {
   String a() default "";

   Class<? extends Exception> b() default Exception.class;
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
static public @interface m {
   @k String a() default "";
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.FIELD, ElementType.TYPE_USE})
static public @interface n {
   @k String a();
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE_USE})
static public @interface o {
   long a();

   long b();
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
static public @interface p {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE_USE})
static public @interface q {
   @k String a() default "";
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE_USE})
static public @interface r {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE_USE})
static public @interface s {
}


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
static public @interface t {
}
    }

    static final class P_org_c_a {


@Documented
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
static public @interface a {
}


@Documented
@Target({ElementType.MODULE, ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
static public @interface b {
}


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
static public @interface c {
}


@Documented
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
static public @interface d {
}
    }

    static final class P_org_a_a_a {


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER, ElementType.METHOD})
static public @interface a {
   String a = "The method argument (if parameter was annotated) or this container (if instance method was annotated)";
   String b = "this";
   String c = "This container (if the parameter was annotated) or the return value (if instance method was annotated)";
   String d = "The return value of this method";
   String e = "this";

   String a() default "The method argument (if parameter was annotated) or this container (if instance method was annotated)";

   boolean b() default false;

   String c() default "This container (if the parameter was annotated) or the return value (if instance method was annotated)";

   boolean d() default false;
}

@f(
   a = "\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*"
)
static public @interface b {
}

/** @deprecated */
@Deprecated
static public final class c {
   private c() {
      throw new AssertionError("JdkConstants should not be instantiated");
   }

   public @interface a {
   }

   public @interface b {
   }

   public @interface c {
   }

   public @interface d {
   }

   public @interface e {
   }

   public @interface f {
   }

   public @interface g {
   }

   public @interface h {
   }

   public @interface i {
   }

   public @interface j {
   }

   public @interface k {
   }

   public @interface l {
   }

   public @interface m {
   }

   public @interface n {
   }

   public @interface o {
   }

   public @interface p {
   }

   public @interface q {
   }
}


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
static public @interface d {
   @k String a();

   @k String b() default "";

   @k String c() default "";
}


@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
static public @interface e {
   long[] a() default {};

   @k String[] b() default {};

   long[] c() default {};

   Class<?> d() default void.class;

   Class<?> e() default void.class;
}


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
static public @interface f {
   @d(
      a = "RegExp"
   )
   @k String a();
}

@f(
   a = "(?:[^%]|%%|(?:%(?:\\d+\\$)?(?:[-#+ 0,(<]*)?(?:\\d+)?(?:\\.\\d+)?(?:[tT])?(?:[a-zA-Z%])))*"
)
static public @interface g {
}

static class h {
   @d(
      a = "RegExp"
   )
   private static final String b = "(?:\\d+\\$)?";
   @d(
      a = "RegExp"
   )
   private static final String c = "(?:[-#+ 0,(<]*)?";
   @d(
      a = "RegExp"
   )
   private static final String d = "(?:\\d+)?";
   @d(
      a = "RegExp"
   )
   private static final String e = "(?:\\.\\d+)?";
   @d(
      a = "RegExp"
   )
   private static final String f = "(?:[tT])?(?:[a-zA-Z%])";
   @d(
      a = "RegExp"
   )
   private static final String g = "[^%]|%%";
   @d(
      a = "RegExp"
   )
   static final String a = "(?:[^%]|%%|(?:%(?:\\d+\\$)?(?:[-#+ 0,(<]*)?(?:\\d+)?(?:\\.\\d+)?(?:[tT])?(?:[a-zA-Z%])))*";
}


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
@d(
   a = "RegExp"
)
static public @interface i {
   @k String a() default "";

   @k String b() default "";
}


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
static public @interface j {
   String a();
}
    }

    static final class P_dev_xoperr_blissgems_libs_faststats_bukkit {


static public sealed interface BukkitMetrics extends Metrics permits BukkitMetricsImpl {
   @e(
      b = true
   )
   static Factory factory() {
      return new BukkitMetricsImpl.Factory();
   }

   void ready() throws IllegalPluginAccessException;

   public interface Factory extends Metrics.Factory<Plugin, Factory> {
      BukkitMetrics create(Plugin var1) throws IllegalStateException;
   }
}


static final class BukkitMetricsImpl extends SimpleMetrics implements BukkitMetrics {
   private final Plugin plugin;
   private final String pluginVersion;
   private final String minecraftVersion;
   private final String serverType;

   @b.b
   @e(
      c = "io"
   )
   private BukkitMetricsImpl(Factory var1, Plugin var2, Path var3) throws IllegalStateException {
      super(var1, (Path)var3);
      this.plugin = var2;
      Server var4 = var2.getServer();
      this.pluginVersion = (String)this.tryOrEmpty(() -> var2.getPluginMeta().getVersion()).orElseGet(() -> var2.getDescription().getVersion());
      this.minecraftVersion = (String)this.tryOrEmpty(() -> var4.getMinecraftVersion()).or(() -> this.tryOrEmpty(() -> var4.getBukkitVersion().split("-", 2)[0])).orElseGet(() -> var4.getVersion().split("\\(MC: |\\)", 3)[1]);
      this.serverType = var4.getName();
      this.startSubmitting();
   }

   Plugin plugin() {
      return this.plugin;
   }

   private boolean checkOnlineMode() {
      Server var1 = this.plugin.getServer();
      Optional var10000 = this.tryOrEmpty(() -> var1.getServerConfig().isProxyOnlineMode()).or(() -> this.tryOrEmpty(this::isProxyOnlineMode));
      Objects.requireNonNull(var1);
      return (Boolean)var10000.orElseGet(var1::getOnlineMode);
   }

   private boolean isProxyOnlineMode() {
      Server var1 = this.plugin.getServer();
      ConfigurationSection var2 = var1.spigot().getPaperConfig().getConfigurationSection("proxies");
      if (var2 == null) {
         return false;
      } else if (var2.getBoolean("velocity.enabled") && var2.getBoolean("velocity.online-mode")) {
         return true;
      } else {
         ConfigurationSection var3 = var1.spigot().getSpigotConfig().getConfigurationSection("settings");
         if (var3 == null) {
            return false;
         } else {
            return var3.getBoolean("bungeecord") && var2.getBoolean("bungee-cord.online-mode");
         }
      }
   }

   protected void appendDefaultData(JsonObject var1) {
      var1.addProperty("minecraft_version", this.minecraftVersion);
      var1.addProperty("online_mode", this.checkOnlineMode());
      var1.addProperty("player_count", (Number)this.getPlayerCount());
      var1.addProperty("plugin_version", this.pluginVersion);
      var1.addProperty("server_type", this.serverType);
   }

   private int getPlayerCount() {
      try {
         return this.plugin.getServer().getOnlinePlayers().size();
      } catch (Throwable var2) {
         this.error("Failed to get player count", var2);
         return 0;
      }
   }

   protected void printError(String var1, @d Throwable var2) {
      this.plugin.getLogger().log(Level.SEVERE, var1, var2);
   }

   protected void printInfo(String var1) {
      this.plugin.getLogger().info(var1);
   }

   protected void printWarning(String var1) {
      this.plugin.getLogger().warning(var1);
   }

   public void ready() {
      if (this.getErrorTracker().isPresent()) {
         try {
            Class.forName("com.destroystokyo.paper.event.server.ServerExceptionEvent");
            this.plugin.getServer().getPluginManager().registerEvents(new PaperEventListener(this), this.plugin);
         } catch (ClassNotFoundException var2) {
         }
      }

   }

   private <T> Optional<T> tryOrEmpty(Supplier<T> var1) {
      try {
         return Optional.of(var1.get());
      } catch (Exception | NoSuchMethodError var3) {
         return Optional.empty();
      }
   }

   static final class Factory extends SimpleMetrics.Factory<Plugin, BukkitMetrics.Factory> implements BukkitMetrics.Factory {
      public BukkitMetrics create(Plugin var1) throws IllegalStateException {
         Path var2 = getPluginsFolder(var1).resolve("faststats");
         Path var3 = var2.resolve("config.properties");
         return new BukkitMetricsImpl(this, var1, var3);
      }

      private static Path getPluginsFolder(Plugin var0) {
         try {
            return var0.getServer().getPluginsFolder().toPath();
         } catch (NoSuchMethodError var2) {
            return var0.getDataFolder().getParentFile().toPath();
         }
      }
   }
}


static record PaperEventListener(BukkitMetricsImpl metrics) implements Listener {
   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onServerException(ServerExceptionEvent var1) {
      ServerException var3 = var1.getException();
      if (var3 instanceof ServerPluginException var2) {
         if (var2.getResponsiblePlugin().equals(this.metrics.plugin())) {
            this.metrics.getErrorTracker().ifPresent((var1x) -> var1x.trackError((Throwable)var2));
         }
      }
   }
}
    }

    static final class P_dev_xoperr_blissgems_libs_faststats_core {


static final class ErrorHelper {
   private static final int MESSAGE_LENGTH = Math.min(1000, Integer.getInteger("faststats.message-length", 500));
   private static final int STACK_TRACE_LENGTH = Math.min(500, Integer.getInteger("faststats.stack-trace-length", 300));
   private static final int STACK_TRACE_LIMIT = Math.min(50, Integer.getInteger("faststats.stack-trace-limit", 15));
   private static final Pattern IPV4_PATTERN = Pattern.compile("\\b(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\b");
   private static final Pattern IPV6_PATTERN = Pattern.compile("(?i)\\b([0-9a-f]{1,4}:){7}[0-9a-f]{1,4}\\b|(?i)\\b([0-9a-f]{1,4}:){1,7}:\\b|(?i)\\b([0-9a-f]{1,4}:){1,6}:[0-9a-f]{1,4}\\b|(?i)\\b([0-9a-f]{1,4}:){1,5}(:[0-9a-f]{1,4}){1,2}\\b|(?i)\\b([0-9a-f]{1,4}:){1,4}(:[0-9a-f]{1,4}){1,3}\\b|(?i)\\b([0-9a-f]{1,4}:){1,3}(:[0-9a-f]{1,4}){1,4}\\b|(?i)\\b([0-9a-f]{1,4}:){1,2}(:[0-9a-f]{1,4}){1,5}\\b|(?i)\\b[0-9a-f]{1,4}:(:[0-9a-f]{1,4}){1,6}\\b|(?i)\\b:(:[0-9a-f]{1,4}){1,7}\\b|(?i)\\b::([0-9a-f]{1,4}:){0,5}[0-9a-f]{1,4}\\b|(?i)\\b::\\b");
   private static final Pattern USER_HOME_PATH_PATTERN = Pattern.compile("(/home/)[^/\\s]+|(/Users/)[^/\\s]+|((?i)[A-Z]:\\\\Users\\\\)[^\\\\\\s]+");

   public static JsonObject compile(Throwable var0, @d List<String> var1) {
      JsonObject var2 = new JsonObject();
      String var3 = getAnonymizedMessage(var0);
      JsonArray var4 = new JsonArray();
      String var5 = var3 != null ? var0.getClass().getName() + ": " + var3 : var0.getClass().getName();
      var4.add(var5);
      StackTraceElement[] var6 = var0.getStackTrace();
      List var7 = collapseStackTrace(var6);
      ArrayList var8 = new ArrayList(var7);
      if (var1 != null) {
         var8.removeAll(var1);
      }

      int var9 = Math.min(var8.size(), STACK_TRACE_LIMIT);
      populateTraces(var9, var8, var6, var4);
      appendCauseChain(var0.getCause(), var7, var1, var4);
      var2.addProperty("error", var0.getClass().getName());
      if (var3 != null) {
         var2.addProperty("message", var3);
      }

      var2.add("stack", var4);
      return var2;
   }

   private static void appendCauseChain(@d Throwable var0, List<String> var1, @d List<String> var2, JsonArray var3) {
      ArrayList var4 = new ArrayList(var1);
      if (var2 != null) {
         var4.addAll(var2);
      }

      for(Set var5 = Collections.newSetFromMap(new IdentityHashMap()); var0 != null && var5.add(var0); var0 = var0.getCause()) {
         String var6 = getAnonymizedMessage(var0);
         String var7 = var6 != null ? "Caused by: " + var0.getClass().getName() + ": " + var6 : "Caused by: " + var0.getClass().getName();
         var3.add(var7);
         StackTraceElement[] var8 = var0.getStackTrace();
         List var9 = collapseStackTrace(var8);
         ArrayList var10 = new ArrayList(var9);
         var10.removeAll(var4);
         int var11 = Math.min(var10.size(), STACK_TRACE_LIMIT);
         populateTraces(var11, var10, var8, var3);
      }

   }

   private static void populateTraces(int var0, List<String> var1, StackTraceElement[] var2, JsonArray var3) {
      for(int var4 = 0; var4 < var0; ++var4) {
         String var5 = (String)var1.get(var4);
         if (var5.length() <= STACK_TRACE_LENGTH) {
            var3.add("  at " + var5);
         } else {
            var3.add("  at " + var5.substring(0, STACK_TRACE_LENGTH) + "...");
         }
      }

      if (var0 > 0 && var0 < var1.size()) {
         var3.add("  ... " + (var1.size() - var0) + " more");
      } else {
         int var6 = var2.length - var1.size();
         if (var6 > 0) {
            var3.add("  ... " + var6 + " more");
         }
      }

   }

   private static List<String> collapseStackTrace(StackTraceElement[] var0) {
      List var1 = Arrays.stream(var0).map(StackTraceElement::toString).toList();
      return collapseRepeatingPattern(var1);
   }

   private static List<String> collapseRepeatingPattern(List<String> var0) {
      List var1 = collapseConsecutiveDuplicates(var0);
      int var2 = var1.size();

      for(int var3 = 1; var3 <= var2 / 2; ++var3) {
         boolean var4 = true;
         int var5 = 0;

         for(int var6 = 0; var6 < var2; ++var6) {
            if (!((String)var1.get(var6)).equals(var1.get(var6 % var3))) {
               var4 = false;
               break;
            }

            if (var6 > 0 && var6 % var3 == 0) {
               ++var5;
            }
         }

         if (var4 && var5 >= 2) {
            return var1.subList(0, var3);
         }
      }

      return var1;
   }

   private static List<String> collapseConsecutiveDuplicates(List<String> var0) {
      if (var0.isEmpty()) {
         return var0;
      } else {
         ArrayList var1 = new ArrayList();
         String var2 = null;

         for(String var4 : var0) {
            if (!var4.equals(var2)) {
               var1.add(var4);
               var2 = var4;
            }
         }

         return var1;
      }
   }

   public static boolean isSameLoader(ClassLoader var0, Throwable var1) {
      return isSameLoader(var0, var1, Collections.newSetFromMap(new IdentityHashMap()));
   }

   private static boolean isSameLoader(ClassLoader var0, @d Throwable var1, Set<Throwable> var2) {
      if (var1 != null && var2.add(var1)) {
         StackTraceElement[] var3 = var1.getStackTrace();
         if (var3 != null && var3.length != 0) {
            int var4 = findFirstNonLibraryFrameIndex(var3);
            if (var4 == -1) {
               return isSameLoader(var0, var1.getCause(), var2);
            } else {
               int var5 = Math.min(5, var3.length - var4);

               for(int var6 = 0; var6 < var5; ++var6) {
                  StackTraceElement var7 = var3[var4 + var6];
                  if (!isLibraryClass(var7.getClassName()) && !isFromLoader(var7, var0)) {
                     return isSameLoader(var0, var1.getCause(), var2);
                  }
               }

               return true;
            }
         } else {
            return isSameLoader(var0, var1.getCause(), var2);
         }
      } else {
         return false;
      }
   }

   private static int findFirstNonLibraryFrameIndex(StackTraceElement[] var0) {
      for(int var1 = 0; var1 < var0.length; ++var1) {
         if (!isLibraryClass(var0[var1].getClassName())) {
            return var1;
         }
      }

      return -1;
   }

   private static boolean isLibraryClass(String var0) {
      return var0.startsWith("java.") || var0.startsWith("javax.") || var0.startsWith("sun.") || var0.startsWith("com.sun.") || var0.startsWith("jdk.");
   }

   private static boolean isFromLoader(StackTraceElement var0, ClassLoader var1) {
      try {
         Class var2 = Class.forName(var0.getClassName(), false, var1);
         return isSameClassLoader(var2.getClassLoader(), var1);
      } catch (Throwable var3) {
         return false;
      }
   }

   private static boolean isSameClassLoader(ClassLoader var0, ClassLoader var1) {
      if (var0 == var1) {
         return true;
      } else {
         ClassLoader var2;
         for(var2 = var0; var2 != null && var2 != var1; var2 = var2.getParent()) {
         }

         return var1 == var2;
      }
   }

   private static String anonymize(String var0) {
      var0 = IPV4_PATTERN.matcher(var0).replaceAll("[IP hidden]");
      var0 = IPV6_PATTERN.matcher(var0).replaceAll("[IP hidden]");
      var0 = USER_HOME_PATH_PATTERN.matcher(var0).replaceAll("$1$2$3[username hidden]");
      String var1 = System.getProperty("user.name");
      if (var1 != null) {
         var0 = var0.replace(var1, "[username hidden]");
      }

      return var0;
   }

   private static @d String getAnonymizedMessage(Throwable var0) {
      String var1 = var0.getMessage();
      if (var1 == null) {
         return null;
      } else {
         String var2 = var1.length() > MESSAGE_LENGTH ? var1.substring(0, MESSAGE_LENGTH) + "..." : var1;
         return anonymize(var2);
      }
   }
}


static public sealed interface ErrorTracker permits SimpleErrorTracker {
   @e(
      a = " -> new"
   )
   static ErrorTracker contextAware() {
      SimpleErrorTracker var0 = new SimpleErrorTracker();
      var0.attachErrorContext(ErrorTracker.class.getClassLoader());
      return var0;
   }

   @e(
      a = " -> new"
   )
   static ErrorTracker contextUnaware() {
      return new SimpleErrorTracker();
   }

   @e(
      c = "this"
   )
   void trackError(String var1);

   @e(
      c = "this"
   )
   void trackError(Throwable var1);

   void attachErrorContext(@d ClassLoader var1) throws IllegalStateException;

   void detachErrorContext();

   boolean isContextAttached();

   @e(
      c = "this"
   )
   void setContextErrorHandler(@d BiConsumer<@d ClassLoader, Throwable> var1);

   @e(
      b = true
   )
   Optional<BiConsumer<@d ClassLoader, Throwable>> getContextErrorHandler();

   @e(
      b = true
   )
   TrackingBase base();

   @e(
      b = true
   )
   TrackingExecutors executors();

   @e(
      b = true
   )
   TrackingThreadFactory threadFactory();

   @e(
      b = true
   )
   TrackingThreadPoolExecutor threadPoolExecutor();

   @e(
      b = true
   )
   static boolean isSameLoader(ClassLoader var0, Throwable var1) {
      return ErrorHelper.isSameLoader(var0, var1);
   }
}


static public interface Metrics {
   @Token
   @e(
      b = true
   )
   String getToken();

   @e(
      b = true
   )
   Optional<ErrorTracker> getErrorTracker();

   @e(
      b = true
   )
   Config getConfig();

   default void ready() {
   }

   @e(
      c = "this"
   )
   void shutdown();

   public sealed interface Config permits SimpleMetrics.Config {
      @e(
         b = true
      )
      UUID serverId();

      @e(
         b = true
      )
      boolean enabled();

      @e(
         b = true
      )
      boolean errorTracking();

      @e(
         b = true
      )
      boolean additionalMetrics();

      @e(
         b = true
      )
      boolean debug();
   }

   public interface Factory<T, F extends Factory<T, F>> {
      @e(
         c = "this"
      )
      F addMetric(Metric<?> var1) throws IllegalArgumentException;

      @e(
         c = "this"
      )
      F onFlush(Runnable var1);

      @e(
         c = "this"
      )
      F errorTracker(ErrorTracker var1);

      @e(
         c = "this"
      )
      F debug(boolean var1);

      @e(
         c = "this"
      )
      F token(@Token String var1) throws IllegalArgumentException;

      @e(
         c = "this"
      )
      F url(URI var1);

      @b.b
      @e(
         a = "_ -> new",
         c = "io"
      )
      Metrics create(T var1) throws IllegalStateException;
   }
}


static final class MurmurHash3 {
   public static String hash(JsonObject var0) {
      long[] var1 = hash(var0.toString());
      String var10000 = Long.toHexString(var1[0]);
      return var10000 + Long.toHexString(var1[1]);
   }

   @e(
      a = "_ -> new",
      b = true
   )
   private static long[] hash(String var0) {
      byte[] var1 = var0.getBytes(StandardCharsets.UTF_8);
      long var2 = 0L;
      long var4 = 0L;
      long var6 = -8663945395140668459L;
      long var8 = 5545529020109919103L;
      int var10 = var1.length;
      int var11 = var10 / 16;

      for(int var12 = 0; var12 < var11; ++var12) {
         int var13 = getInt(var1, var12 * 16);
         int var14 = getInt(var1, var12 * 16 + 4);
         getInt(var1, var12 * 16 + 8);
         getInt(var1, var12 * 16 + 12);
         var13 *= 289559509;
         var13 = Integer.rotateLeft(var13, 31);
         var13 *= 658871167;
         long var17 = var2 ^ (long)var13;
         long var18 = Long.rotateLeft(var17, 27);
         long var19 = var18 + var4;
         var2 = var19 * 5L + 1390208809L;
         var14 *= 658871167;
         var14 = Integer.rotateLeft(var14, 33);
         var14 *= 289559509;
         var4 ^= (long)var14;
         var4 = Long.rotateLeft(var4, 31);
         var4 += var2;
         var4 = var4 * 5L + 944331445L;
      }

      int var31 = 0;
      int var39 = 0;
      int var47 = 0;
      int var15 = 0;
      int var16 = var11 * 16;
      switch (var10 & 15) {
         case 15:
            var15 ^= (var1[var16 + 14] & 255) << 16;
         case 14:
            var15 ^= (var1[var16 + 13] & 255) << 8;
         case 13:
            var15 ^= var1[var16 + 12] & 255;
            var15 *= 658871167;
            var15 = Integer.rotateLeft(var15, 33);
            var15 *= 289559509;
            var4 ^= (long)var15;
         case 12:
            var47 ^= (var1[var16 + 11] & 255) << 24;
         case 11:
            var47 ^= (var1[var16 + 10] & 255) << 16;
         case 10:
            var47 ^= (var1[var16 + 9] & 255) << 8;
         case 9:
            var47 ^= var1[var16 + 8] & 255;
            var47 *= 289559509;
            var47 = Integer.rotateLeft(var47, 31);
            var47 *= 658871167;
            var2 ^= (long)var47;
         case 8:
            var39 ^= (var1[var16 + 7] & 255) << 24;
         case 7:
            var39 ^= (var1[var16 + 6] & 255) << 16;
         case 6:
            var39 ^= (var1[var16 + 5] & 255) << 8;
         case 5:
            var39 ^= var1[var16 + 4] & 255;
            var39 *= 658871167;
            var39 = Integer.rotateLeft(var39, 33);
            var39 *= 289559509;
            var4 ^= (long)var39;
         case 4:
            var31 ^= (var1[var16 + 3] & 255) << 24;
         case 3:
            var31 ^= (var1[var16 + 2] & 255) << 16;
         case 2:
            var31 ^= (var1[var16 + 1] & 255) << 8;
         case 1:
            var31 ^= var1[var16] & 255;
            var31 *= 289559509;
            var31 = Integer.rotateLeft(var31, 31);
            var31 *= 658871167;
            var2 ^= (long)var31;
         default:
            var2 ^= (long)var10;
            var4 ^= (long)var10;
            var2 += var4;
            var4 += var2;
            var2 = fmix64(var2);
            var4 = fmix64(var4);
            var2 += var4;
            var4 += var2;
            return new long[]{var2, var4};
      }
   }

   @e(
      b = true
   )
   private static long fmix64(long var0) {
      var0 ^= var0 >>> 33;
      var0 *= -49064778989728563L;
      var0 ^= var0 >>> 33;
      var0 *= -4265267296055464877L;
      var0 ^= var0 >>> 33;
      return var0;
   }

   @e(
      b = true
   )
   private static int getInt(byte[] var0, int var1) {
      return var0[var1] & 255 | (var0[var1 + 1] & 255) << 8 | (var0[var1 + 2] & 255) << 16 | (var0[var1 + 3] & 255) << 24;
   }
}


static final class SimpleErrorTracker implements ErrorTracker {
   private final Map<String, Integer> collected = new ConcurrentHashMap();
   private final Map<String, JsonObject> reports = new ConcurrentHashMap();
   private final TrackingBase base = new SimpleTrackingBase(this);
   private final TrackingExecutors executors = new SimpleTrackingExecutors(this);
   private final TrackingThreadFactory threadFactory = new SimpleTrackingThreadFactory(this);
   private final TrackingThreadPoolExecutor threadPoolExecutor = new SimpleTrackingThreadPoolExecutor(this);
   private volatile @d BiConsumer<@d ClassLoader, Throwable> errorEvent = null;
   private volatile Thread.@d UncaughtExceptionHandler originalHandler = null;

   public void trackError(String var1) {
      this.trackError((Throwable)(new RuntimeException(var1)));
   }

   public void trackError(Throwable var1) {
      try {
         JsonObject var2 = ErrorHelper.compile(var1, (List)null);
         String var3 = MurmurHash3.hash(var2);
         if ((Integer)this.collected.compute(var3, (var0, var1x) -> var1x == null ? 1 : var1x + 1) > 1) {
            return;
         }

         this.reports.put(var3, var2);
      } catch (NoClassDefFoundError var4) {
      }

   }

   public JsonArray getData() {
      JsonArray var1 = new JsonArray(this.reports.size());
      this.reports.forEach((var2, var3) -> {
         JsonObject var4 = var3.deepCopy();
         var4.addProperty("hash", var2);
         Integer var5 = (Integer)this.collected.getOrDefault(var2, 1);
         if (var5 > 1) {
            var4.addProperty("count", (Number)var5);
         }

         var1.add((JsonElement)var4);
      });
      this.collected.forEach((var2, var3) -> {
         if (var3 > 0 && !this.reports.containsKey(var2)) {
            JsonObject var4 = new JsonObject();
            var4.addProperty("hash", var2);
            if (var3 > 1) {
               var4.addProperty("count", (Number)var3);
            }

            var1.add((JsonElement)var4);
         }
      });
      return var1;
   }

   public void clear() {
      this.collected.replaceAll((var0, var1) -> 0);
      this.reports.clear();
   }

   public boolean needsFlushing() {
      if (!this.reports.isEmpty()) {
         return true;
      } else {
         for(Integer var2 : this.collected.values()) {
            if (var2 > 0) {
               return true;
            }
         }

         return false;
      }
   }

   public synchronized void attachErrorContext(@d ClassLoader var1) throws IllegalStateException {
      if (this.originalHandler != null) {
         throw new IllegalStateException("Error context already attached");
      } else {
         this.originalHandler = Thread.getDefaultUncaughtExceptionHandler();
         Thread.setDefaultUncaughtExceptionHandler((var2, var3) -> {
            Thread.UncaughtExceptionHandler var4 = this.originalHandler;
            if (var4 != null) {
               var4.uncaughtException(var2, var3);
            }

            try {
               if (var1 != null && !ErrorTracker.isSameLoader(var1, var3)) {
                  return;
               }

               BiConsumer var5 = this.errorEvent;
               if (var5 != null) {
                  var5.accept(var1, var3);
               }

               this.trackError(var3);
            } catch (Throwable var6) {
               this.trackError(var6);
            }

         });
      }
   }

   public synchronized void detachErrorContext() {
      if (this.originalHandler != null) {
         Thread.setDefaultUncaughtExceptionHandler(this.originalHandler);
         this.originalHandler = null;
      }
   }

   public synchronized boolean isContextAttached() {
      return this.originalHandler != null;
   }

   public synchronized void setContextErrorHandler(@d BiConsumer<@d ClassLoader, Throwable> var1) {
      this.errorEvent = var1;
   }

   public synchronized Optional<BiConsumer<@d ClassLoader, Throwable>> getContextErrorHandler() {
      return Optional.ofNullable(this.errorEvent);
   }

   public TrackingBase base() {
      return this.base;
   }

   public TrackingExecutors executors() {
      return this.executors;
   }

   public TrackingThreadFactory threadFactory() {
      return this.threadFactory;
   }

   public TrackingThreadPoolExecutor threadPoolExecutor() {
      return this.threadPoolExecutor;
   }
}


static public abstract class SimpleMetrics implements Metrics {
   private final HttpClient httpClient;
   private @d ScheduledExecutorService executor;
   private final Set<Metric<?>> metrics;
   private final Config config;
   @Token
   private final String token;
   private final @d ErrorTracker tracker;
   private final @d Runnable flush;
   private final URI url;
   private final boolean debug;
   private final String SDK_NAME;
   private final String SDK_VERSION;
   private final String javaVersion;
   private final String osArch;
   private final String osName;
   private final String osVersion;
   private final int coreCount;

   @e(
      c = "io"
   )
   protected SimpleMetrics(Factory<?, ?> var1, Config var2) throws IllegalStateException {
      this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3L)).version(Version.HTTP_1_1).build();
      this.executor = null;
      Properties var3 = new Properties();

      try {
         InputStream var4 = this.getClass().getResourceAsStream("/META-INF/faststats.properties");

         try {
            if (var4 != null) {
               var3.load(var4);
            }
         } catch (Throwable var8) {
            if (var4 != null) {
               try {
                  var4.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (var4 != null) {
            var4.close();
         }
      } catch (IOException var9) {
      }

      this.SDK_NAME = var3.getProperty("name", "unknown");
      this.SDK_VERSION = var3.getProperty("version", "unknown");
      this.javaVersion = System.getProperty("java.version");
      this.osArch = System.getProperty("os.arch");
      this.osName = System.getProperty("os.name");
      this.osVersion = System.getProperty("os.version");
      this.coreCount = Runtime.getRuntime().availableProcessors();
      if (var1.token == null) {
         throw new IllegalStateException("Token must be specified");
      } else {
         this.config = var2;
         this.metrics = var2.additionalMetrics ? Set.copyOf(var1.metrics) : Set.of();
         this.debug = var1.debug || Boolean.getBoolean("faststats.debug") || var2.debug();
         this.token = var1.token;
         this.tracker = var2.errorTracking ? var1.tracker : null;
         this.flush = var1.flush;
         this.url = var1.url;
      }
   }

   @e(
      c = "io"
   )
   protected SimpleMetrics(Factory<?, ?> var1, Path var2) throws IllegalStateException {
      this(var1, SimpleMetrics.Config.read(var2));
   }

   @t
   protected SimpleMetrics(Config var1, Set<Metric<?>> var2, @Token String var3, @d ErrorTracker var4, @d Runnable var5, URI var6, boolean var7) {
      this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3L)).version(Version.HTTP_1_1).build();
      this.executor = null;
      Properties var8 = new Properties();

      try {
         InputStream var9 = this.getClass().getResourceAsStream("/META-INF/faststats.properties");

         try {
            if (var9 != null) {
               var8.load(var9);
            }
         } catch (Throwable var13) {
            if (var9 != null) {
               try {
                  var9.close();
               } catch (Throwable var12) {
                  var13.addSuppressed(var12);
               }
            }

            throw var13;
         }

         if (var9 != null) {
            var9.close();
         }
      } catch (IOException var14) {
      }

      this.SDK_NAME = var8.getProperty("name", "unknown");
      this.SDK_VERSION = var8.getProperty("version", "unknown");
      this.javaVersion = System.getProperty("java.version");
      this.osArch = System.getProperty("os.arch");
      this.osName = System.getProperty("os.name");
      this.osVersion = System.getProperty("os.version");
      this.coreCount = Runtime.getRuntime().availableProcessors();
      if (!var3.matches("[a-z0-9]{32}")) {
         throw new IllegalArgumentException("Invalid token '" + var3 + "', must match '[a-z0-9]{32}'");
      } else {
         this.metrics = var1.additionalMetrics ? Set.copyOf(var2) : Set.of();
         this.config = var1;
         this.debug = var7;
         this.token = var3;
         this.tracker = var4;
         this.flush = var5;
         this.url = var6;
      }
   }

   protected String getOnboardingMessage() {
      return "This plugin uses FastStats to collect anonymous usage statistics.\nNo personal or identifying information is ever collected.\nTo opt out, set 'enabled=false' in the metrics configuration file.\nLearn more at: https://faststats.dev/info\n\nSince this is your first start with FastStats, metrics submission will not start\nuntil you restart the server to allow you to opt out if you prefer.";
   }

   protected long getInitialDelay() {
      return TimeUnit.SECONDS.toMillis(Long.getLong("faststats.initial-delay", 30L));
   }

   protected long getPeriod() {
      return TimeUnit.MINUTES.toMillis(30L);
   }

   @b.b
   @g
   protected void startSubmitting() {
      this.startSubmitting(this.getInitialDelay(), this.getPeriod(), TimeUnit.MILLISECONDS);
   }

   private void startSubmitting(long var1, long var3, TimeUnit var5) {
      if (Boolean.getBoolean("faststats.first-run")) {
         this.info("Skipping metrics submission due to first-run flag");
      } else {
         if (this.config.firstRun) {
            int var6 = 0;
            String[] var7 = this.getOnboardingMessage().split("\n");

            for(String var11 : var7) {
               if (var11.length() > var6) {
                  var6 = var11.length();
               }
            }

            this.printInfo("-".repeat(var6));

            for(String var16 : var7) {
               this.printInfo(var16);
            }

            this.printInfo("-".repeat(var6));
            System.setProperty("faststats.first-run", "true");
            if (!this.config.externallyManaged()) {
               return;
            }
         }

         boolean var12 = Boolean.parseBoolean(System.getProperty("faststats.enabled", "true"));
         if (this.config.enabled() && var12) {
            if (this.isSubmitting()) {
               this.warn("Metrics already submitting, not starting again");
            } else {
               this.executor = Executors.newSingleThreadScheduledExecutor((var0) -> {
                  Thread var1 = new Thread(var0, "metrics-submitter");
                  var1.setDaemon(true);
                  return var1;
               });
               this.info("Starting metrics submission");
               this.executor.scheduleAtFixedRate(this::submit, Math.max(0L, var1), Math.max(1000L, var3), var5);
            }
         } else {
            this.warn("Metrics disabled, not starting submission");
         }
      }
   }

   protected boolean isSubmitting() {
      return this.executor != null && !this.executor.isShutdown();
   }

   public boolean submit() {
      try {
         return this.submitNow();
      } catch (Throwable var2) {
         this.error("Failed to submit metrics", var2);
         return false;
      }
   }

   private boolean submitNow() throws IOException {
      String var1 = this.createData().toString();
      byte[] var2 = var1.getBytes(StandardCharsets.UTF_8);
      this.info("Uncompressed data: " + var1);
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();

      boolean var10;
      label88: {
         boolean var18;
         try {
            GZIPOutputStream var4;
            label92: {
               var4 = new GZIPOutputStream(var3);

               try {
                  label93: {
                     var4.write(var2);
                     var4.finish();
                     byte[] var5 = var3.toByteArray();
                     this.info("Compressed size: " + var5.length + " bytes");
                     HttpRequest.Builder var10000 = HttpRequest.newBuilder().POST(BodyPublishers.ofByteArray(var5)).header("Content-Encoding", "gzip").header("Content-Type", "application/octet-stream").header("Authorization", "Bearer " + this.getToken());
                     String var10002 = this.getSdkName();
                     HttpRequest var6 = var10000.header("User-Agent", "FastStats Metrics " + var10002 + "/" + this.getSdkVersion()).timeout(Duration.ofSeconds(3L)).uri(this.url).build();
                     this.info("Sending metrics to: " + String.valueOf(this.url));

                     try {
                        HttpResponse var7 = this.httpClient.send(var6, BodyHandlers.ofString(StandardCharsets.UTF_8));
                        int var8 = var7.statusCode();
                        String var9 = (String)var7.body();
                        if (var8 >= 200 && var8 < 300) {
                           this.info("Metrics submitted with status code: " + var8 + " (" + var9 + ")");
                           Optional var19 = this.getErrorTracker();
                           Objects.requireNonNull(SimpleErrorTracker.class);
                           var19.map(SimpleErrorTracker.class::cast).ifPresent(SimpleErrorTracker::clear);
                           if (this.flush != null) {
                              this.flush.run();
                           }

                           var10 = true;
                           break label93;
                        }

                        if (var8 >= 300 && var8 < 400) {
                           this.warn("Received redirect response from metrics server: " + var8 + " (" + var9 + ")");
                        } else if (var8 >= 400 && var8 < 500) {
                           this.error("Submitted invalid request to metrics server: " + var8 + " (" + var9 + ")", (Throwable)null);
                        } else if (var8 >= 500 && var8 < 600) {
                           this.error("Received server error response from metrics server: " + var8 + " (" + var9 + ")", (Throwable)null);
                        } else {
                           this.warn("Received unexpected response from metrics server: " + var8 + " (" + var9 + ")");
                        }
                     } catch (HttpConnectTimeoutException var13) {
                        this.error("Metrics submission timed out after 3 seconds: " + String.valueOf(this.url), (Throwable)null);
                     } catch (ConnectException var14) {
                        this.error("Failed to connect to metrics server: " + String.valueOf(this.url), (Throwable)null);
                     } catch (Throwable var15) {
                        this.error("Failed to submit metrics", var15);
                     }

                     var18 = false;
                     break label92;
                  }
               } catch (Throwable var16) {
                  try {
                     var4.close();
                  } catch (Throwable var12) {
                     var16.addSuppressed(var12);
                  }

                  throw var16;
               }

               var4.close();
               break label88;
            }

            var4.close();
         } catch (Throwable var17) {
            try {
               var3.close();
            } catch (Throwable var11) {
               var17.addSuppressed(var11);
            }

            throw var17;
         }

         var3.close();
         return var18;
      }

      var3.close();
      return var10;
   }

   private String getSdkName() {
      return this.SDK_NAME;
   }

   private String getSdkVersion() {
      return this.SDK_VERSION;
   }

   protected JsonObject createData() {
      JsonObject var1 = new JsonObject();
      JsonObject var2 = new JsonObject();
      var2.addProperty("java_version", this.javaVersion);
      var2.addProperty("os_arch", this.osArch);
      var2.addProperty("os_name", this.osName);
      var2.addProperty("os_version", this.osVersion);
      var2.addProperty("core_count", (Number)this.coreCount);
      this.metrics.forEach((var2x) -> {
         try {
            var2x.getData().ifPresent((var2xx) -> var2.add(var2x.getId(), var2xx));
         } catch (Throwable var4) {
            this.error("Failed to build metric data: " + var2x.getId(), var4);
            this.getErrorTracker().ifPresent((var1) -> var1.trackError(var4));
         }

      });

      try {
         this.appendDefaultData(var2);
      } catch (Throwable var4) {
         this.error("Failed to append default data", var4);
         this.getErrorTracker().ifPresent((var1x) -> var1x.trackError(var4));
      }

      var1.addProperty("identifier", this.config.serverId().toString());
      var1.add("data", var2);
      Optional var10000 = this.getErrorTracker();
      Objects.requireNonNull(SimpleErrorTracker.class);
      var10000.map(SimpleErrorTracker.class::cast).map(SimpleErrorTracker::getData).filter((var0) -> !var0.isEmpty()).ifPresent((var1x) -> var1.add("errors", var1x));
      return var1;
   }

   @Token
   public String getToken() {
      return this.token;
   }

   public Optional<ErrorTracker> getErrorTracker() {
      return Optional.ofNullable(this.tracker);
   }

   public Metrics.Config getConfig() {
      return this.config;
   }

   @e(
      c = "param1"
   )
   protected abstract void appendDefaultData(JsonObject var1);

   protected void error(String var1, @d Throwable var2) {
      if (this.debug) {
         this.printError("[" + this.getClass().getName() + "]: " + var1, var2);
      }

   }

   protected void warn(String var1) {
      if (this.debug) {
         String var10001 = this.getClass().getName();
         this.printWarning("[" + var10001 + "]: " + var1);
      }

   }

   protected void info(String var1) {
      if (this.debug) {
         String var10001 = this.getClass().getName();
         this.printInfo("[" + var10001 + "]: " + var1);
      }

   }

   protected abstract void printError(String var1, @d Throwable var2);

   protected abstract void printInfo(String var1);

   protected abstract void printWarning(String var1);

   public void shutdown() {
      this.getErrorTracker().ifPresent(ErrorTracker::detachErrorContext);
      if (this.executor != null) {
         try {
            this.info("Shutting down metrics submission");
            this.executor.shutdown();
            Optional var10000 = this.getErrorTracker();
            Objects.requireNonNull(SimpleErrorTracker.class);
            var10000.map(SimpleErrorTracker.class::cast).filter(SimpleErrorTracker::needsFlushing).ifPresent((var1) -> this.submit());
         } catch (Throwable var5) {
            this.error("Failed to submit metrics on shutdown", var5);
         } finally {
            this.executor = null;
         }
      }

   }

   public abstract static class Factory<T, F extends Metrics.Factory<T, F>> implements Metrics.Factory<T, F> {
      private final Set<Metric<?>> metrics = new HashSet(0);
      private URI url = URI.create("https://metrics.faststats.dev/v1/collect");
      private @d ErrorTracker tracker;
      private @d Runnable flush;
      private @d String token;
      private boolean debug = false;

      public F addMetric(Metric<?> var1) throws IllegalArgumentException {
         if (!this.metrics.add(var1)) {
            throw new IllegalArgumentException("Metric already added: " + var1.getId());
         } else {
            return (F)this;
         }
      }

      public F onFlush(Runnable var1) {
         this.flush = var1;
         return (F)this;
      }

      public F errorTracker(ErrorTracker var1) {
         this.tracker = var1;
         return (F)this;
      }

      public F debug(boolean var1) {
         this.debug = var1;
         return (F)this;
      }

      public F token(@Token String var1) throws IllegalArgumentException {
         if (!var1.matches("[a-z0-9]{32}")) {
            throw new IllegalArgumentException("Invalid token '" + var1 + "', must match '[a-z0-9]{32}'");
         } else {
            this.token = var1;
            return (F)this;
         }
      }

      public F url(URI var1) {
         this.url = var1;
         return (F)this;
      }
   }

   public static record Config(UUID serverId, boolean additionalMetrics, boolean debug, boolean enabled, boolean errorTracking, boolean firstRun, boolean externallyManaged) implements Metrics.Config {
      public static final String DEFAULT_COMMENT = " FastStats (https://faststats.dev) collects anonymous usage statistics for plugin developers.\n# This helps developers understand how their projects are used in the real world.\n#\n# No IP addresses, player data, or personal information is collected.\n# The server ID below is randomly generated and can be regenerated at any time.\n#\n# Enabling metrics has no noticeable performance impact.\n# Keeping metrics enabled is recommended, but you can disable them by setting 'enabled=false'.\n#\n# If you suspect a plugin is collecting personal data or bypassing the \"enabled\" option,\n# please report it at: https://faststats.dev/abuse\n#\n# For more information, visit: https://faststats.dev/info\n";

      @e(
         c = "io"
      )
      public static Config read(Path var0) throws RuntimeException {
         return read(var0, " FastStats (https://faststats.dev) collects anonymous usage statistics for plugin developers.\n# This helps developers understand how their projects are used in the real world.\n#\n# No IP addresses, player data, or personal information is collected.\n# The server ID below is randomly generated and can be regenerated at any time.\n#\n# Enabling metrics has no noticeable performance impact.\n# Keeping metrics enabled is recommended, but you can disable them by setting 'enabled=false'.\n#\n# If you suspect a plugin is collecting personal data or bypassing the \"enabled\" option,\n# please report it at: https://faststats.dev/abuse\n#\n# For more information, visit: https://faststats.dev/info\n", false, false);
      }

      @e(
         c = "io"
      )
      public static Config read(Path var0, String var1, boolean var2, boolean var3) throws RuntimeException {
         Optional var4 = readOrEmpty(var0);
         boolean var5 = var4.isEmpty();
         AtomicBoolean var6 = new AtomicBoolean(var5);
         UUID var7 = (UUID)var4.map((var0x) -> var0x.getProperty("serverId")).map((var1x) -> {
            try {
               String var2 = var1x.trim();
               String var3 = var2.length() > 36 ? var2.substring(0, 36) : var2;
               if (!var3.equals(var1x)) {
                  var6.set(true);
               }

               return UUID.fromString(var3);
            } catch (IllegalArgumentException var4) {
               var6.set(true);
               return UUID.randomUUID();
            }
         }).orElseGet(() -> {
            var6.set(true);
            return UUID.randomUUID();
         });
         BiPredicate var8 = (var2x, var3x) -> (Boolean)var4.map((var1) -> var1.getProperty(var2x)).map(Boolean::parseBoolean).orElseGet(() -> {
               var6.set(true);
               return var3x;
            });
         boolean var9 = var2 ? var3 : var8.test("enabled", true);
         boolean var10 = var8.test("submitErrors", true);
         boolean var11 = var8.test("submitAdditionalMetrics", true);
         boolean var12 = var8.test("debug", false);
         if (var6.get()) {
            try {
               save(var0, var2, var1, var7, var9, var10, var11, var12);
            } catch (IOException var14) {
               throw new RuntimeException("Failed to save metrics config", var14);
            }
         }

         return new Config(var7, var11, var12, var9, var10, var5, var2);
      }

      private static Optional<Properties> readOrEmpty(Path var0) throws RuntimeException {
         if (!Files.isRegularFile(var0, new LinkOption[0])) {
            return Optional.empty();
         } else {
            try {
               BufferedReader var1 = Files.newBufferedReader(var0, StandardCharsets.UTF_8);

               Optional var3;
               try {
                  Properties var2 = new Properties();
                  var2.load(var1);
                  var3 = Optional.of(var2);
               } catch (Throwable var5) {
                  if (var1 != null) {
                     try {
                        var1.close();
                     } catch (Throwable var4) {
                        var5.addSuppressed(var4);
                     }
                  }

                  throw var5;
               }

               if (var1 != null) {
                  var1.close();
               }

               return var3;
            } catch (IOException var6) {
               throw new RuntimeException("Failed to read metrics config", var6);
            }
         }
      }

      private static void save(Path var0, boolean var1, String var2, UUID var3, boolean var4, boolean var5, boolean var6, boolean var7) throws IOException {
         Files.createDirectories(var0.getParent());
         OutputStream var8 = Files.newOutputStream(var0);

         try {
            OutputStreamWriter var9 = new OutputStreamWriter(var8, StandardCharsets.UTF_8);

            try {
               Properties var10 = new Properties();
               var10.setProperty("serverId", var3.toString());
               if (!var1) {
                  var10.setProperty("enabled", Boolean.toString(var4));
               }

               var10.setProperty("submitErrors", Boolean.toString(var5));
               var10.setProperty("submitAdditionalMetrics", Boolean.toString(var6));
               var10.setProperty("debug", Boolean.toString(var7));
               var10.store(var9, var2);
            } catch (Throwable var14) {
               try {
                  var9.close();
               } catch (Throwable var13) {
                  var14.addSuppressed(var13);
               }

               throw var14;
            }

            var9.close();
         } catch (Throwable var15) {
            if (var8 != null) {
               try {
                  var8.close();
               } catch (Throwable var12) {
                  var15.addSuppressed(var12);
               }
            }

            throw var15;
         }

         if (var8 != null) {
            var8.close();
         }

      }
   }
}


static final class SimpleTrackingBase implements TrackingBase {
   private final ErrorTracker tracker;

   public SimpleTrackingBase(ErrorTracker var1) {
      this.tracker = var1;
   }

   public Runnable tracked(Runnable var1) {
      return () -> {
         try {
            var1.run();
         } catch (Throwable var3) {
            this.tracker.trackError(var3);
            throw var3;
         }
      };
   }

   public <T> PrivilegedAction<T> tracked(PrivilegedAction<T> var1) {
      return () -> {
         try {
            return var1.run();
         } catch (Throwable var3) {
            this.tracker.trackError(var3);
            throw var3;
         }
      };
   }

   public <T> PrivilegedExceptionAction<T> tracked(PrivilegedExceptionAction<T> var1) {
      return () -> {
         try {
            return var1.run();
         } catch (Throwable var3) {
            this.tracker.trackError(var3);
            throw var3;
         }
      };
   }
}


static final class SimpleTrackingExecutors implements TrackingExecutors {
   private final ErrorTracker tracker;

   public SimpleTrackingExecutors(ErrorTracker var1) {
      this.tracker = var1;
   }

   public ExecutorService newFixedThreadPool(int var1) {
      return this.tracker.threadPoolExecutor().create(var1, var1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
   }

   public ExecutorService newWorkStealingPool(int var1) {
      return Executors.newWorkStealingPool(var1);
   }

   public ExecutorService newWorkStealingPool() {
      return Executors.newWorkStealingPool();
   }

   public ExecutorService newFixedThreadPool(int var1, ThreadFactory var2) {
      return Executors.newFixedThreadPool(var1, WrappedTrackingThreadFactory.wrap(this.tracker, var2));
   }

   public ExecutorService newSingleThreadExecutor() {
      return this.newSingleThreadExecutor(this.defaultThreadFactory());
   }

   public ExecutorService newSingleThreadExecutor(ThreadFactory var1) {
      return Executors.newSingleThreadExecutor(WrappedTrackingThreadFactory.wrap(this.tracker, var1));
   }

   public ExecutorService newCachedThreadPool() {
      return this.tracker.threadPoolExecutor().create(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue());
   }

   public ExecutorService newCachedThreadPool(ThreadFactory var1) {
      return Executors.newCachedThreadPool(WrappedTrackingThreadFactory.wrap(this.tracker, var1));
   }

   public ExecutorService newThreadPerTaskExecutor(ThreadFactory var1) {
      return Executors.newSingleThreadExecutor(WrappedTrackingThreadFactory.wrap(this.tracker, var1));
   }

   public ExecutorService newVirtualThreadPerTaskExecutor() {
      return this.newThreadPerTaskExecutor(Thread.ofVirtual().factory());
   }

   public ScheduledExecutorService newSingleThreadScheduledExecutor() {
      return Executors.newSingleThreadScheduledExecutor();
   }

   public ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory var1) {
      return Executors.newSingleThreadScheduledExecutor(WrappedTrackingThreadFactory.wrap(this.tracker, var1));
   }

   public ScheduledExecutorService newScheduledThreadPool(int var1) {
      return Executors.newScheduledThreadPool(var1);
   }

   public ScheduledExecutorService newScheduledThreadPool(int var1, ThreadFactory var2) {
      return Executors.newScheduledThreadPool(var1, WrappedTrackingThreadFactory.wrap(this.tracker, var2));
   }

   public ExecutorService unconfigurableExecutorService(ExecutorService var1) {
      return Executors.unconfigurableExecutorService(var1);
   }

   public ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService var1) {
      return Executors.unconfigurableScheduledExecutorService(var1);
   }

   public ThreadFactory defaultThreadFactory() {
      return new SimpleTrackingThreadFactory(this.tracker);
   }

   public <T> Callable<T> callable(Runnable var1, T var2) {
      return Executors.callable(this.tracker.base().tracked(var1), var2);
   }

   public Callable<Object> callable(Runnable var1) {
      return Executors.callable(this.tracker.base().tracked(var1));
   }

   public Callable<Object> callable(PrivilegedAction<?> var1) {
      return Executors.callable(this.tracker.base().tracked(var1));
   }

   public Callable<Object> callable(PrivilegedExceptionAction<?> var1) {
      return Executors.callable(this.tracker.base().tracked(var1));
   }
}


static final class SimpleTrackingThreadFactory implements TrackingThreadFactory {
   private static final AtomicInteger poolNumber = new AtomicInteger(1);
   private final ThreadGroup group;
   private final AtomicInteger threadNumber = new AtomicInteger(1);
   private final String namePrefix;
   private final ErrorTracker tracker;

   public SimpleTrackingThreadFactory(ErrorTracker var1) {
      this.tracker = var1;
      this.group = Thread.currentThread().getThreadGroup();
      this.namePrefix = "tracking-pool-" + poolNumber.getAndIncrement() + "-thread-";
   }

   public Thread newThread(Runnable var1) {
      return this.newThread(this.namePrefix + this.threadNumber.getAndIncrement(), var1);
   }

   public Thread newThread(String var1, Runnable var2) {
      Thread var3 = new Thread(this.group, this.tracker.base().tracked(var2), var1, 0L);
      if (var3.isDaemon()) {
         var3.setDaemon(false);
      }

      if (var3.getPriority() != 5) {
         var3.setPriority(5);
      }

      return var3;
   }
}


static final class SimpleTrackingThreadPoolExecutor implements TrackingThreadPoolExecutor {
   private final ErrorTracker tracker;

   public SimpleTrackingThreadPoolExecutor(ErrorTracker var1) {
      this.tracker = var1;
   }

   public ThreadPoolExecutor create(@o(
   a = 0L,
   b = 2147483647L
) int var1, @o(
   a = 0L,
   b = 2147483647L
) int var2, @o(
   a = 0L,
   b = 2147483647L
) long var3, TimeUnit var5, BlockingQueue<Runnable> var6) {
      return new ThreadPoolExecutor(var1, var2, var3, var5, var6, this.tracker.threadFactory()) {
         protected void afterExecute(Runnable var1, Throwable var2) {
            super.afterExecute(var1, var2);
            SimpleTrackingThreadPoolExecutor.this.tracker.trackError(var2);
         }
      };
   }

   public ThreadPoolExecutor create(@o(
   a = 0L,
   b = 2147483647L
) int var1, @o(
   a = 0L,
   b = 2147483647L
) int var2, @o(
   a = 0L,
   b = 2147483647L
) long var3, TimeUnit var5, BlockingQueue<Runnable> var6, ThreadFactory var7) {
      return new ThreadPoolExecutor(var1, var2, var3, var5, var6, var7) {
         protected void afterExecute(Runnable var1, Throwable var2) {
            super.afterExecute(var1, var2);
            SimpleTrackingThreadPoolExecutor.this.tracker.trackError(var2);
         }
      };
   }

   public ThreadPoolExecutor create(@o(
   a = 0L,
   b = 2147483647L
) int var1, @o(
   a = 0L,
   b = 2147483647L
) int var2, @o(
   a = 0L,
   b = 2147483647L
) long var3, TimeUnit var5, BlockingQueue<Runnable> var6, RejectedExecutionHandler var7) {
      return new ThreadPoolExecutor(var1, var2, var3, var5, var6, var7) {
         protected void afterExecute(Runnable var1, Throwable var2) {
            super.afterExecute(var1, var2);
            SimpleTrackingThreadPoolExecutor.this.tracker.trackError(var2);
         }
      };
   }

   public ThreadPoolExecutor create(@o(
   a = 0L,
   b = 2147483647L
) int var1, @o(
   a = 0L,
   b = 2147483647L
) int var2, @o(
   a = 0L,
   b = 2147483647L
) long var3, TimeUnit var5, BlockingQueue<Runnable> var6, ThreadFactory var7, RejectedExecutionHandler var8) {
      return new ThreadPoolExecutor(var1, var2, var3, var5, var6, var7, var8) {
         protected void afterExecute(Runnable var1, Throwable var2) {
            super.afterExecute(var1, var2);
            SimpleTrackingThreadPoolExecutor.this.tracker.trackError(var2);
         }
      };
   }
}


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@k
@f(
   a = "[a-z0-9]{32}"
)
static public @interface Token {
   String PATTERN = "[a-z0-9]{32}";
}


static final class WrappedTrackingThreadFactory implements ThreadFactory {
   private final ErrorTracker tracker;
   private final ThreadFactory factory;

   public WrappedTrackingThreadFactory(ErrorTracker var1, ThreadFactory var2) {
      this.tracker = var1;
      this.factory = var2;
   }

   public Thread newThread(Runnable var1) {
      return this.factory.newThread(this.tracker.base().tracked(var1));
   }

   public static ThreadFactory wrap(ErrorTracker var0, ThreadFactory var1) {
      return (ThreadFactory)(!(var1 instanceof TrackingThreadFactory) && !(var1 instanceof WrappedTrackingThreadFactory) ? new WrappedTrackingThreadFactory(var0, var1) : var1);
   }
}
    }

    static final class P_dev_xoperr_blissgems_libs_faststats_core_concurrent {


static public interface TrackingBase {
   @e(
      a = "_ -> new",
      b = true
   )
   Runnable tracked(Runnable var1);

   @e(
      a = "_ -> new",
      b = true
   )
   <T> PrivilegedAction<T> tracked(PrivilegedAction<T> var1);

   @e(
      a = "_ -> new",
      b = true
   )
   <T> PrivilegedExceptionAction<T> tracked(PrivilegedExceptionAction<T> var1);
}


@a.d
static public interface TrackingExecutors {
   ExecutorService newFixedThreadPool(int var1);

   ExecutorService newWorkStealingPool(int var1);

   ExecutorService newWorkStealingPool();

   ExecutorService newFixedThreadPool(int var1, ThreadFactory var2);

   ExecutorService newSingleThreadExecutor();

   ExecutorService newSingleThreadExecutor(ThreadFactory var1);

   ExecutorService newCachedThreadPool();

   ExecutorService newCachedThreadPool(ThreadFactory var1);

   ExecutorService newThreadPerTaskExecutor(ThreadFactory var1);

   ExecutorService newVirtualThreadPerTaskExecutor();

   ScheduledExecutorService newSingleThreadScheduledExecutor();

   ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory var1);

   ScheduledExecutorService newScheduledThreadPool(int var1);

   ScheduledExecutorService newScheduledThreadPool(int var1, ThreadFactory var2);

   ExecutorService unconfigurableExecutorService(ExecutorService var1);

   ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService var1);

   ThreadFactory defaultThreadFactory();

   <T> Callable<T> callable(Runnable var1, T var2);

   Callable<Object> callable(Runnable var1);

   Callable<Object> callable(PrivilegedAction<?> var1);

   Callable<Object> callable(PrivilegedExceptionAction<?> var1);
}


@a.d
static public interface TrackingThreadFactory extends ThreadFactory {
   @e(
      a = "_ -> new",
      b = true
   )
   Thread newThread(Runnable var1);

   @e(
      a = "_, _ -> new",
      b = true
   )
   Thread newThread(@k String var1, Runnable var2);
}


@a.d
static public interface TrackingThreadPoolExecutor {
   ThreadPoolExecutor create(@o(
   a = 0L,
   b = 2147483647L
) int var1, @o(
   a = 0L,
   b = 2147483647L
) int var2, @o(
   a = 0L,
   b = 2147483647L
) long var3, TimeUnit var5, BlockingQueue<Runnable> var6);

   ThreadPoolExecutor create(@o(
   a = 0L,
   b = 2147483647L
) int var1, @o(
   a = 0L,
   b = 2147483647L
) int var2, @o(
   a = 0L,
   b = 2147483647L
) long var3, TimeUnit var5, BlockingQueue<Runnable> var6, ThreadFactory var7);

   ThreadPoolExecutor create(@o(
   a = 0L,
   b = 2147483647L
) int var1, @o(
   a = 0L,
   b = 2147483647L
) int var2, @o(
   a = 0L,
   b = 2147483647L
) long var3, TimeUnit var5, BlockingQueue<Runnable> var6, RejectedExecutionHandler var7);

   ThreadPoolExecutor create(@o(
   a = 0L,
   b = 2147483647L
) int var1, @o(
   a = 0L,
   b = 2147483647L
) int var2, @o(
   a = 0L,
   b = 2147483647L
) long var3, TimeUnit var5, BlockingQueue<Runnable> var6, ThreadFactory var7, RejectedExecutionHandler var8);
}
    }

    static final class P_dev_xoperr_blissgems_libs_faststats_core_data {


static final class ArrayMetric<T> extends SimpleMetric<T[]> {
   public ArrayMetric(@SourceId String var1, Callable<T @d []> var2) throws IllegalArgumentException {
      super(var1, var2);
   }

   public Optional<JsonElement> getData() throws Exception {
      return this.compute().map((var0) -> {
         JsonArray var1 = new JsonArray(var0.length);

         for(Object var5 : var0) {
            Objects.requireNonNull(var5);
            byte var7 = 0;
            //$FF: var7->value
            //0->java/lang/Boolean
            //1->java/lang/Number
            if (var5 instanceof Boolean var3) {
               var1.add((Boolean)var5);
            } else if (var5 instanceof Number var4) {
               var1.add((Number)var5);
            } else {
               var1.add(var5.toString());
            }
         }

         return var1;
      });
   }
}


static public interface Metric<T> {
   @SourceId
   @e(
      b = true
   )
   String getId();

   @e(
      b = true
   )
   Optional<T> compute() throws Exception;

   @e(
      b = true
   )
   Optional<JsonElement> getData() throws Exception;

   @e(
      a = "_, _ -> new",
      b = true
   )
   static Metric<String[]> stringArray(@SourceId String var0, Callable<String @d []> var1) throws IllegalArgumentException {
      return new ArrayMetric<String[]>(var0, var1);
   }

   @e(
      a = "_, _ -> new",
      b = true
   )
   static Metric<Boolean[]> booleanArray(@SourceId String var0, Callable<Boolean @d []> var1) throws IllegalArgumentException {
      return new ArrayMetric<Boolean[]>(var0, var1);
   }

   @e(
      a = "_, _ -> new",
      b = true
   )
   static Metric<Number[]> numberArray(@SourceId String var0, Callable<Number @d []> var1) throws IllegalArgumentException {
      return new ArrayMetric<Number[]>(var0, var1);
   }

   @e(
      a = "_, _ -> new",
      b = true
   )
   static Metric<Boolean> bool(@SourceId String var0, Callable<@d Boolean> var1) throws IllegalArgumentException {
      return new SingleValueMetric<Boolean>(var0, var1);
   }

   @e(
      a = "_, _ -> new",
      b = true
   )
   static Metric<String> string(@SourceId String var0, Callable<@d String> var1) throws IllegalArgumentException {
      return new SingleValueMetric<String>(var0, var1);
   }

   @e(
      a = "_, _ -> new",
      b = true
   )
   static Metric<Number> number(@SourceId String var0, Callable<@d Number> var1) throws IllegalArgumentException {
      return new SingleValueMetric<Number>(var0, var1);
   }
}


static abstract class SimpleMetric<T> implements Metric<T> {
   @SourceId
   private final String id;
   private final Callable<@d T> callable;

   public SimpleMetric(@SourceId String var1, Callable<@d T> var2) throws IllegalArgumentException {
      if (!var1.matches("[a-z_]+")) {
         throw new IllegalArgumentException("Invalid source id '" + var1 + "', must match '[a-z_]+'");
      } else {
         this.id = var1;
         this.callable = var2;
      }
   }

   @SourceId
   public final String getId() {
      return this.id;
   }

   public final Optional<T> compute() throws Exception {
      return Optional.ofNullable(this.callable.call());
   }

   public boolean equals(Object var1) {
      if (var1 != null && this.getClass() == var1.getClass()) {
         SimpleMetric var2 = (SimpleMetric)var1;
         return Objects.equals(this.id, var2.id);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.id);
   }

   public String toString() {
      return "SimpleMetric{id='" + this.id + "'}";
   }
}


static final class SingleValueMetric<T> extends SimpleMetric<T> {
   public SingleValueMetric(@SourceId String var1, Callable<@d T> var2) throws IllegalArgumentException {
      super(var1, var2);
   }

   public Optional<JsonElement> getData() throws Exception {
      return this.compute().map((var0) -> {
         Objects.requireNonNull(var0);
         byte var2 = 0;
         JsonPrimitive var10000;
         //$FF: var2->value
         //0->java/lang/Boolean
         //1->java/lang/Number
         if (var0 instanceof Boolean var3) {
            var10000 = new JsonPrimitive((Boolean)var0);
         } else if (var0 instanceof Number var4) {
            var10000 = new JsonPrimitive((Number)var0);
         } else {
            var10000 = new JsonPrimitive(var0.toString());
         }

         return var10000;
      });
   }
}


@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@k
@f(
   a = "[a-z_]+"
)
static public @interface SourceId {
   String PATTERN = "[a-z_]+";
}
    }

    static final class P_dev_xoperr_blissgems_api {


static public interface BlissGemsAPI {
   GemRegistry getGemRegistry();

   AbilityManager getAbilityManager();

   EnergyManager getEnergyManager();

   ConfigManager getConfigManager();

   GemManager getGemManager();

   TrustedPlayersManager getTrustedPlayersManager();

   boolean playerHasGem(Player var1, String var2);
}

static public class CooldownEntry {
   private final String abilityKey;
   private final String displayName;

   public CooldownEntry(String var1, String var2) {
      this.abilityKey = var1;
      this.displayName = var2;
   }

   public String getAbilityKey() {
      return this.abilityKey;
   }

   public String getDisplayName() {
      return this.displayName;
   }
}


static public interface GemAbilityHandler {
   void onPrimary(Player var1, int var2);

   default void onSecondary(Player var1, int var2) {
   }

   default void onTertiary(Player var1, int var2) {
   }

   default void onQuaternary(Player var1, int var2) {
   }

   default void cleanup(Player var1) {
   }
}


static public class GemDefinition {
   private final String id;
   private final String displayName;
   private final String description;
   private final String color;
   private final String pluginName;
   private final int maxTier;
   private final Material material;
   private final int t1CustomModelData;
   private final int t2CustomModelData;
   private final List<String> t1Lore;
   private final List<String> t2Lore;
   private final String t1DisplayName;
   private final String t2DisplayName;

   private GemDefinition(Builder var1) {
      this.id = var1.id;
      this.displayName = var1.displayName;
      this.description = var1.description;
      this.color = var1.color;
      this.pluginName = var1.pluginName;
      this.maxTier = var1.maxTier;
      this.material = var1.material;
      this.t1CustomModelData = var1.t1CustomModelData;
      this.t2CustomModelData = var1.t2CustomModelData;
      this.t1Lore = var1.t1Lore;
      this.t2Lore = var1.t2Lore;
      this.t1DisplayName = var1.t1DisplayName;
      this.t2DisplayName = var1.t2DisplayName;
   }

   public String getId() {
      return this.id;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getDescription() {
      return this.description;
   }

   public String getColor() {
      return this.color;
   }

   public String getPluginName() {
      return this.pluginName;
   }

   public int getMaxTier() {
      return this.maxTier;
   }

   public Material getMaterial() {
      return this.material;
   }

   public int getT1CustomModelData() {
      return this.t1CustomModelData;
   }

   public int getT2CustomModelData() {
      return this.t2CustomModelData;
   }

   public List<String> getT1Lore() {
      return this.t1Lore;
   }

   public List<String> getT2Lore() {
      return this.t2Lore;
   }

   public String getT1DisplayName() {
      return this.t1DisplayName;
   }

   public String getT2DisplayName() {
      return this.t2DisplayName;
   }

   public String buildItemId(int var1) {
      return this.id + "_gem_t" + var1;
   }

   public static class Builder {
      private final String id;
      private String displayName;
      private String description = "";
      private String color = "§7";
      private String pluginName = "BlissGems";
      private int maxTier = 2;
      private Material material;
      private int t1CustomModelData;
      private int t2CustomModelData;
      private List<String> t1Lore;
      private List<String> t2Lore;
      private String t1DisplayName;
      private String t2DisplayName;

      public Builder(String var1) {
         this.material = Material.ECHO_SHARD;
         this.t1CustomModelData = -1;
         this.t2CustomModelData = -1;
         this.id = var1;
         String var10001 = var1.substring(0, 1).toUpperCase();
         this.displayName = var10001 + var1.substring(1);
      }

      public Builder displayName(String var1) {
         this.displayName = var1;
         return this;
      }

      public Builder description(String var1) {
         this.description = var1;
         return this;
      }

      public Builder color(String var1) {
         this.color = var1;
         return this;
      }

      public Builder plugin(String var1) {
         this.pluginName = var1;
         return this;
      }

      public Builder maxTier(int var1) {
         this.maxTier = var1;
         return this;
      }

      public Builder material(Material var1) {
         this.material = var1;
         return this;
      }

      public Builder t1CustomModelData(int var1) {
         this.t1CustomModelData = var1;
         return this;
      }

      public Builder t2CustomModelData(int var1) {
         this.t2CustomModelData = var1;
         return this;
      }

      public Builder t1Lore(List<String> var1) {
         this.t1Lore = var1;
         return this;
      }

      public Builder t2Lore(List<String> var1) {
         this.t2Lore = var1;
         return this;
      }

      public Builder t1DisplayName(String var1) {
         this.t1DisplayName = var1;
         return this;
      }

      public Builder t2DisplayName(String var1) {
         this.t2DisplayName = var1;
         return this;
      }

      public GemDefinition build() {
         if (this.id != null && !this.id.isEmpty()) {
            return new GemDefinition(this);
         } else {
            throw new IllegalArgumentException("Gem ID must not be empty");
         }
      }
   }
}


static public interface GemPassiveHandler {
   void applyPassives(Player var1, int var2);
}


static public interface GemRegistry {
   void registerGem(GemDefinition var1);

   void registerAbilities(String var1, GemAbilityHandler var2);

   void registerPassives(String var1, GemPassiveHandler var2);

   void registerCooldowns(String var1, List<CooldownEntry> var2);

   GemDefinition getGem(String var1);

   GemAbilityHandler getAbilityHandler(String var1);

   GemPassiveHandler getPassiveHandler(String var1);

   List<CooldownEntry> getCooldownEntries(String var1);

   Collection<GemDefinition> getAllGems();

   boolean isRegisteredGem(String var1);

   String gemIdFromItemId(String var1);

   int tierFromItemId(String var1);
}
    }

    static final class P_dev_xoperr_blissgems_managers {


static public class AbilityBindingManager {
   private final BlissGems a;
   private final Map<UUID, EnumMap<AbilityBinding, AbilitySlot>> b = new ConcurrentHashMap();
   private static final EnumMap<AbilityBinding, AbilitySlot> c = new EnumMap(AbilityBinding.class);

   public AbilityBindingManager(BlissGems var1) {
      this.a = var1;
   }

   public AbilitySlot getSlot(Player var1, AbilityBinding var2) {
      return (AbilitySlot)this.a(var1.getUniqueId()).get(var2);
   }

   public EnumMap<AbilityBinding, AbilitySlot> getAll(Player var1) {
      return new EnumMap(this.a(var1.getUniqueId()));
   }

   public void setBinding(Player var1, AbilityBinding var2, AbilitySlot var3) {
      EnumMap var4 = this.a(var1.getUniqueId());
      var4.entrySet().removeIf((var2x) -> var2x.getValue() == var3 && var2x.getKey() != var2);
      var4.put(var2, var3);
      this.a(var1.getUniqueId(), var4);
   }

   public void unbind(Player var1, AbilityBinding var2) {
      EnumMap var3 = this.a(var1.getUniqueId());
      var3.remove(var2);
      this.a(var1.getUniqueId(), var3);
   }

   public void resetToDefaults(Player var1) {
      EnumMap var2 = new EnumMap(c);
      this.b.put(var1.getUniqueId(), var2);
      this.a(var1.getUniqueId(), var2);
   }

   public void clearCache(UUID var1) {
      this.b.remove(var1);
   }

   private EnumMap<AbilityBinding, AbilitySlot> a(UUID var1) {
      EnumMap var2 = (EnumMap)this.b.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         var2 = this.b(var1);
         this.b.put(var1, var2);
         return var2;
      }
   }

   private EnumMap<AbilityBinding, AbilitySlot> b(UUID var1) {
      File var2 = this.c(var1);
      if (!var2.exists()) {
         return new EnumMap(c);
      } else {
         YamlConfiguration var3 = YamlConfiguration.loadConfiguration(var2);
         if (!((FileConfiguration)var3).contains("ability-bindings")) {
            return new EnumMap(c);
         } else {
            EnumMap var4 = new EnumMap(AbilityBinding.class);
            Map var5 = ((FileConfiguration)var3).getConfigurationSection("ability-bindings").getValues(false);

            for(Map.Entry var7 : var5.entrySet()) {
               AbilityBinding var8 = AbilityBinding.fromId((String)var7.getKey());
               AbilitySlot var9 = AbilitySlot.fromId(String.valueOf(var7.getValue()));
               if (var8 != null && var9 != null) {
                  var4.put(var8, var9);
               }
            }

            return var4;
         }
      }
   }

   private void a(UUID var1, EnumMap<AbilityBinding, AbilitySlot> var2) {
      File var3 = this.c(var1);
      YamlConfiguration var4 = var3.exists() ? YamlConfiguration.loadConfiguration(var3) : new YamlConfiguration();
      ((FileConfiguration)var4).set("ability-bindings", (Object)null);
      HashMap var5 = new HashMap();

      for(Map.Entry var7 : var2.entrySet()) {
         var5.put(((AbilityBinding)var7.getKey()).getId(), ((AbilitySlot)var7.getValue()).getId());
      }

      ((FileConfiguration)var4).createSection("ability-bindings", var5);

      try {
         ((FileConfiguration)var4).save(var3);
      } catch (IOException var8) {
         Logger var10000 = this.a.getLogger();
         String var10001 = String.valueOf(var1);
         var10000.warning("[AbilityBindings] Failed to save for " + var10001 + ": " + var8.getMessage());
      }

   }

   private File c(UUID var1) {
      File var2 = new File(this.a.getDataFolder(), "playerdata");
      if (!var2.exists()) {
         var2.mkdirs();
      }

      return new File(var2, String.valueOf(var1) + ".yml");
   }

   static {
      c.put(AbilityBinding.RIGHT_CLICK, AbilitySlot.PRIMARY);
      c.put(AbilityBinding.SHIFT_RIGHT_CLICK, AbilitySlot.SECONDARY);
      c.put(AbilityBinding.SWAP_HAND, AbilitySlot.TERTIARY);
      c.put(AbilityBinding.SHIFT_SWAP_HAND, AbilitySlot.QUATERNARY);
   }
}


static public class AbilityManager {
   private final BlissGems a;
   private final Map<UUID, Map<String, Long>> b;
   private final File c;

   public AbilityManager(BlissGems var1) {
      this.a = var1;
      this.b = new HashMap();
      this.c = new File(var1.getDataFolder(), "cooldowns");
      if (!this.c.exists()) {
         this.c.mkdirs();
      }

   }

   public boolean isOnCooldown(Player var1, String var2) {
      Map var3 = (Map)this.b.get(var1.getUniqueId());
      if (var3 == null) {
         return false;
      } else {
         Long var4 = (Long)var3.get(var2);
         if (var4 == null) {
            return false;
         } else {
            return System.currentTimeMillis() < var4;
         }
      }
   }

   public int getRemainingCooldown(Player var1, String var2) {
      Map var3 = (Map)this.b.get(var1.getUniqueId());
      if (var3 == null) {
         return 0;
      } else {
         Long var4 = (Long)var3.get(var2);
         if (var4 == null) {
            return 0;
         } else {
            long var5 = var4 - System.currentTimeMillis();
            return var5 > 0L ? (int)Math.ceil((double)var5 / (double)1000.0F) : 0;
         }
      }
   }

   public void setCooldown(Player var1, String var2, int var3) {
      Map var4 = (Map)this.b.computeIfAbsent(var1.getUniqueId(), (var0) -> new HashMap());
      var4.put(var2, System.currentTimeMillis() + (long)var3 * 1000L);
      this.a(var1.getUniqueId());
   }

   public boolean canUseAbility(Player var1, String var2) {
      if (this.a.getRegionManager() != null && this.a.getRegionManager().a(var1)) {
         String var3 = this.a.getRegionManager().a();
         if (var3 != null && !var3.isEmpty()) {
            var1.sendMessage(ChatColor.translateAlternateColorCodes('&', var3));
         }

         return false;
      } else if (!this.a.getEnergyManager().canUseAbilities(var1)) {
         this.a.getConfigManager().sendFormattedMessage(var1, "ability-no-energy");
         return false;
      } else if (!var2.startsWith("astra-") && this.a.getAstraAbilities().m(var1)) {
         var1.sendMessage("§4§l§oYour gem abilities are nullified by a Dimensional Void!");
         return false;
      } else {
         return !this.isOnCooldown(var1, var2);
      }
   }

   public void useAbility(Player var1, String var2) {
      int var3 = this.a.getConfigManager().getAbilityCooldown(var2);
      this.setCooldown(var1, var2, var3);
   }

   public void clearCooldowns(Player var1) {
      this.b.remove(var1.getUniqueId());
      File var2 = new File(this.c, String.valueOf(var1.getUniqueId()) + ".yml");
      if (var2.exists()) {
         var2.delete();
      }

   }

   public void clearCache(UUID var1) {
      this.b.remove(var1);
   }

   public void loadCooldowns(UUID var1) {
      File var2 = new File(this.c, String.valueOf(var1) + ".yml");
      if (var2.exists()) {
         YamlConfiguration var3 = YamlConfiguration.loadConfiguration(var2);
         HashMap var4 = new HashMap();
         Set var5 = var3.getKeys(false);
         if (var5 != null) {
            for(String var7 : var5) {
               long var8 = var3.getLong(var7, 0L);
               if (var8 > System.currentTimeMillis()) {
                  var4.put(var7, var8);
               }
            }
         }

         if (!var4.isEmpty()) {
            this.b.put(var1, var4);
         }

      }
   }

   private void a(UUID var1) {
      Map var2 = (Map)this.b.get(var1);
      if (var2 != null && !var2.isEmpty()) {
         File var3 = new File(this.c, String.valueOf(var1) + ".yml");
         YamlConfiguration var4 = new YamlConfiguration();

         for(Map.Entry var6 : var2.entrySet()) {
            if ((Long)var6.getValue() > System.currentTimeMillis()) {
               var4.set((String)var6.getKey(), var6.getValue());
            }
         }

         try {
            var4.save(var3);
         } catch (IOException var7) {
            Logger var10000 = this.a.getLogger();
            String var10001 = String.valueOf(var1);
            var10000.warning("Failed to save cooldowns for " + var10001 + ": " + var7.getMessage());
         }

      }
   }

   public void saveAllCooldowns() {
      for(UUID var2 : this.b.keySet()) {
         this.a(var2);
      }

   }
}


static public class AchievementManager {
   private final BlissGems a;
   private final File b;
   private final Map<UUID, Map<Achievement, Integer>> c = new HashMap();
   private final Map<UUID, Set<Achievement>> d = new HashMap();

   public AchievementManager(BlissGems var1) {
      this.a = var1;
      this.b = new File(var1.getDataFolder(), "achievements");
      if (!this.b.exists()) {
         this.b.mkdirs();
      }

   }

   public boolean isUnlocked(Player var1, Achievement var2) {
      return this.getUnlocked(var1).contains(var2);
   }

   public Set<Achievement> getUnlocked(Player var1) {
      UUID var2 = var1.getUniqueId();
      if (!this.d.containsKey(var2)) {
         this.a(var1);
      }

      return (Set)this.d.getOrDefault(var2, new HashSet());
   }

   public int getProgress(Player var1, Achievement var2) {
      UUID var3 = var1.getUniqueId();
      if (!this.c.containsKey(var3)) {
         this.a(var1);
      }

      return (Integer)((Map)this.c.getOrDefault(var3, new HashMap())).getOrDefault(var2, 0);
   }

   public void addProgress(Player var1, Achievement var2, int var3) {
      if (!this.isUnlocked(var1, var2)) {
         UUID var4 = var1.getUniqueId();
         Map var5 = (Map)this.c.computeIfAbsent(var4, (var0) -> new HashMap());
         int var6 = (Integer)var5.getOrDefault(var2, 0);
         int var7 = var6 + var3;
         var5.put(var2, var7);
         if (var7 >= var2.getTargetProgress()) {
            this.unlock(var1, var2);
         }

      }
   }

   public void setProgress(Player var1, Achievement var2, int var3) {
      if (!this.isUnlocked(var1, var2)) {
         UUID var4 = var1.getUniqueId();
         Map var5 = (Map)this.c.computeIfAbsent(var4, (var0) -> new HashMap());
         var5.put(var2, var3);
         if (var3 >= var2.getTargetProgress()) {
            this.unlock(var1, var2);
         }

      }
   }

   public void unlock(Player var1, Achievement var2) {
      if (!this.isUnlocked(var1, var2)) {
         UUID var3 = var1.getUniqueId();
         Set var4 = (Set)this.d.computeIfAbsent(var3, (var0) -> new HashSet());
         var4.add(var2);
         Map var5 = (Map)this.c.computeIfAbsent(var3, (var0) -> new HashMap());
         var5.put(var2, var2.getTargetProgress());
         var1.sendMessage("§6§l⭐ ACHIEVEMENT UNLOCKED! §e" + var2.getDisplayName());
         var1.sendMessage("§7§o" + var2.getDescription());
         var1.playSound(var1.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F);
         var1.spawnParticle(Particle.TOTEM_OF_UNDYING, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
         this.savePlayerData(var3);
      }
   }

   private void a(Player var1) {
      UUID var2 = var1.getUniqueId();
      File var3 = new File(this.b, String.valueOf(var2) + ".yml");
      HashSet var4 = new HashSet();
      HashMap var5 = new HashMap();
      if (var3.exists()) {
         YamlConfiguration var6 = YamlConfiguration.loadConfiguration(var3);

         for(Achievement var10 : Achievement.values()) {
            String var11 = var10.name().toLowerCase();
            var5.put(var10, ((FileConfiguration)var6).getInt("progress." + var11, 0));
            if (((FileConfiguration)var6).getBoolean("unlocked." + var11, false)) {
               var4.add(var10);
            }
         }
      }

      this.d.put(var2, var4);
      this.c.put(var2, var5);
   }

   public void savePlayerData(UUID var1) {
      File var2 = new File(this.b, String.valueOf(var1) + ".yml");
      YamlConfiguration var3 = new YamlConfiguration();
      Map var4 = (Map)this.c.getOrDefault(var1, new HashMap());
      Set var5 = (Set)this.d.getOrDefault(var1, new HashSet());

      for(Achievement var9 : Achievement.values()) {
         String var10 = var9.name().toLowerCase();
         ((FileConfiguration)var3).set("progress." + var10, var4.getOrDefault(var9, 0));
         ((FileConfiguration)var3).set("unlocked." + var10, var5.contains(var9));
      }

      try {
         ((FileConfiguration)var3).save(var2);
      } catch (IOException var11) {
         Logger var10000 = this.a.getLogger();
         String var10001 = String.valueOf(var1);
         var10000.warning("Failed to save achievements for " + var10001 + ": " + var11.getMessage());
      }

   }

   public void saveAll() {
      for(UUID var2 : this.d.keySet()) {
         this.savePlayerData(var2);
      }

   }

   public void clearCache(UUID var1) {
      this.c.remove(var1);
      this.d.remove(var1);
   }
}


static public class BlissGuiManager implements Listener {
   private final BlissGems plugin;
   private static final String GUI_TITLE = "§5§lBlissGems Menu";

   public BlissGuiManager(BlissGems var1) {
      this.plugin = var1;
   }

   public void openMainMenu(Player var1) {
      Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 27, "§5§lBlissGems Menu");
      var2.setItem(11, this.createGemInfoItem(var1));
      var2.setItem(13, this.createEnergyInfoItem(var1));
      var2.setItem(15, this.createSettingsItem(var1));
      ItemStack var3 = this.createBorderItem();

      for(int var4 = 0; var4 < 27; ++var4) {
         if (var2.getItem(var4) == null) {
            var2.setItem(var4, var3);
         }
      }

      var1.openInventory(var2);
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
         var5.add("§7Get one from an admin or find one.");
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

   private ItemStack createSettingsItem(Player var1) {
      ItemStack var2 = new ItemStack(Material.COMPARATOR);
      ItemMeta var3 = var2.getItemMeta();
      var3.setDisplayName("§e§lSettings");
      ArrayList var4 = new ArrayList();
      var4.add("§7Current Settings:");
      var4.add("");
      boolean var5 = this.plugin.getClickActivationManager().isClickActivationEnabled(var1);
      var4.add("§7Click Activation: " + (var5 ? "§aEnabled" : "§cDisabled"));
      var4.add("§8Use /bliss toggle_click to change");
      var4.add("");
      int var6 = this.plugin.getTrustedPlayersManager().getTrustedPlayers(var1).size();
      var4.add("§7Trusted Players: §f" + var6);
      var4.add("§8Use /bliss trust <player>");
      var4.add("");
      if (var1.hasPermission("blissgems.admin")) {
         boolean var7 = this.plugin.getConfigManager().isBanOnZeroEnergyEnabled();
         var4.add("§c§lAdmin Setting:");
         var4.add("§7Ban on 0 Energy: " + (var7 ? "§aEnabled" : "§cDisabled"));
         var4.add("§8Use /bliss bannable <true/false>");
      }

      var3.setLore(var4);
      var2.setItemMeta(var3);
      return var2;
   }

   private ItemStack createBorderItem() {
      ItemStack var1 = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
      ItemMeta var2 = var1.getItemMeta();
      var2.setDisplayName(" ");
      var1.setItemMeta(var2);
      return var1;
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent var1) {
      if (var1.getView().getTitle().equals("§5§lBlissGems Menu")) {
         var1.setCancelled(true);
      }

   }
}


static public class ClickActivationManager {
   private final BlissGems a;
   private final Map<UUID, Boolean> b = new HashMap();
   private final File c;

   public ClickActivationManager(BlissGems var1) {
      this.a = var1;
      this.c = new File(var1.getDataFolder(), "playerdata");
      if (!this.c.exists()) {
         this.c.mkdirs();
      }

   }

   public boolean isClickActivationEnabled(Player var1) {
      UUID var2 = var1.getUniqueId();
      if (this.b.containsKey(var2)) {
         return (Boolean)this.b.get(var2);
      } else {
         boolean var3 = this.a(var1);
         this.b.put(var2, var3);
         return var3;
      }
   }

   public boolean toggleClickActivation(Player var1) {
      boolean var2 = this.isClickActivationEnabled(var1);
      boolean var3 = !var2;
      this.setClickActivation(var1, var3);
      return var3;
   }

   public void setClickActivation(Player var1, boolean var2) {
      UUID var3 = var1.getUniqueId();
      this.b.put(var3, var2);
      this.a(var1, var2);
   }

   private boolean a(Player var1) {
      File var2 = this.b(var1);
      if (!var2.exists()) {
         return true;
      } else {
         YamlConfiguration var3 = YamlConfiguration.loadConfiguration(var2);
         return ((FileConfiguration)var3).getBoolean("click-activation-enabled", true);
      }
   }

   private void a(Player var1, boolean var2) {
      File var3 = this.b(var1);
      YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
      ((FileConfiguration)var4).set("click-activation-enabled", var2);

      try {
         ((FileConfiguration)var4).save(var3);
      } catch (IOException var6) {
         Logger var10000 = this.a.getLogger();
         String var10001 = var1.getName();
         var10000.warning("Failed to save click activation for " + var10001 + ": " + var6.getMessage());
      }

   }

   private File b(Player var1) {
      return new File(this.c, String.valueOf(var1.getUniqueId()) + ".yml");
   }

   public void clearCache(Player var1) {
      this.b.remove(var1.getUniqueId());
   }
}


static public class CooldownDisplayManager {
   private final BlissGems a;
   private BukkitTask b;
   private static final Map<GemType, List<String[]>> c = new HashMap();

   public CooldownDisplayManager(BlissGems var1) {
      this.a = var1;
      this.a();
   }

   private void a() {
      this.b = this.a.getServer().getScheduler().runTaskTimer(this.a, () -> {
         for(Player var2 : this.a.getServer().getOnlinePlayers()) {
            if (this.a.getGemManager().hasActiveGem(var2) && this.a(var2) && !this.a.getFireAbilities().a(var2) && !this.a.getFluxAbilities().a(var2) && !this.a.getStrengthAbilities().d(var2)) {
               int var3 = this.a.getEnergyManager().getEnergy(var2);
               String var4 = this.c(var2);
               if (var3 == 0) {
                  String var5 = "§c§lBROKEN §8| ";
                  if (!var4.isEmpty()) {
                     var4 = var5 + var4;
                  } else {
                     var4 = "§c§lBROKEN";
                  }
               }

               if (!var4.isEmpty()) {
                  var2.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(var4));
               }
            }
         }

      }, 0L, 10L);
   }

   private boolean a(Player var1) {
      return this.b(var1) != null;
   }

   private a b(Player var1) {
      GemRegistryImpl var2 = this.a.getGemRegistry();
      ItemStack var3 = var1.getInventory().getItemInMainHand();
      if (var3 != null) {
         String var4 = CustomItemManager.getIdByItem(var3);
         if (var4 != null) {
            if (GemType.isGem(var4)) {
               GemType var11 = GemType.fromOraxenId(var4);
               int var14 = GemType.getTierFromOraxenId(var4);
               String var16 = var11 != null ? var11.getId() : null;
               return new a(var11, var16, var14, "✋");
            }

            if (var2 != null && var2.isRegisteredGem(var4)) {
               String var10 = var2.gemIdFromItemId(var4);
               int var13 = var2.tierFromItemId(var4);
               return new a((GemType)null, var10, var13, "✋");
            }
         }
      }

      ItemStack var9 = var1.getInventory().getItemInOffHand();
      if (var9 != null) {
         String var5 = CustomItemManager.getIdByItem(var9);
         if (var5 != null) {
            if (GemType.isGem(var5)) {
               GemType var12 = GemType.fromOraxenId(var5);
               int var15 = GemType.getTierFromOraxenId(var5);
               String var8 = var12 != null ? var12.getId() : null;
               return new a(var12, var8, var15, "\ud83d\udee1");
            }

            if (var2 != null && var2.isRegisteredGem(var5)) {
               String var6 = var2.gemIdFromItemId(var5);
               int var7 = var2.tierFromItemId(var5);
               return new a((GemType)null, var6, var7, "\ud83d\udee1");
            }
         }
      }

      return null;
   }

   private String c(Player var1) {
      StringBuilder var2 = new StringBuilder();
      AbilityManager var3 = this.a.getAbilityManager();
      a var4 = this.b(var1);
      if (var4 == null) {
         return "";
      } else {
         GemType var5 = var4.a;
         int var6 = var4.c;
         if (var5 == null) {
            return this.a(var1, var4);
         } else {
            String var7 = var5.getColor();
            List var8 = (List)c.get(var5);
            if (var8 == null) {
               return "";
            } else if (var5 == GemType.ASTRA) {
               String var27 = this.a(var5, 0);
               int var34 = var3.getRemainingCooldown(var1, ((String[])var8.get(0))[0]);
               var2.append(var27).append(" ");
               if (var34 > 0) {
                  var2.append("§c").append(var34).append("s");
               } else {
                  var2.append("§aReady");
               }

               if (var6 == 2) {
                  String var41 = this.a(var5, 1);
                  int var48 = var3.getRemainingCooldown(var1, ((String[])var8.get(1))[0]);
                  var2.append(" §5(✦) ");
                  var2.append(var41).append(" ");
                  if (var48 > 0) {
                     var2.append("§c").append(var48).append("s");
                  } else {
                     var2.append("§aReady");
                  }
               }

               return var2.toString();
            } else if (var5 == GemType.FLUX) {
               String var26 = this.a(var5, 0);
               int var33 = var3.getRemainingCooldown(var1, ((String[])var8.get(0))[0]);
               var2.append(var26).append(" ");
               if (var33 > 0) {
                  var2.append("§c").append(var33).append("s");
               } else {
                  var2.append("§aReady");
               }

               if (var6 == 2) {
                  String var40 = this.a(var5, 1);
                  int var47 = var3.getRemainingCooldown(var1, ((String[])var8.get(1))[0]);
                  int var54 = var3.getRemainingCooldown(var1, ((String[])var8.get(2))[0]);
                  int var57 = var3.getRemainingCooldown(var1, ((String[])var8.get(3))[0]);
                  if (var54 > 0 && var57 > 0) {
                     var2.append(" §b(").append(var54).append("s§7|").append(var57).append("s) ");
                  } else if (var54 > 0) {
                     var2.append(" §b(").append(var54).append("s) ");
                  } else if (var57 > 0) {
                     var2.append(" §b(").append(var57).append("s) ");
                  } else {
                     var2.append(" §b(⚡) ");
                  }

                  var2.append(var40).append(" ");
                  if (var47 > 0) {
                     var2.append("§c").append(var47).append("s");
                  } else {
                     var2.append("§aReady");
                  }
               }

               return var2.toString();
            } else if (var5 == GemType.LIFE) {
               String var25 = this.a(var5, 0);
               int var32 = var3.getRemainingCooldown(var1, ((String[])var8.get(0))[0]);
               var2.append(var25).append(" ");
               if (var32 > 0) {
                  var2.append("§c").append(var32).append("s");
               } else {
                  var2.append("§aReady");
               }

               if (var6 == 2) {
                  String var39 = this.a(var5, 1);
                  int var46 = var3.getRemainingCooldown(var1, ((String[])var8.get(1))[0]);
                  int var53 = var3.getRemainingCooldown(var1, ((String[])var8.get(2))[0]);
                  int var56 = var3.getRemainingCooldown(var1, ((String[])var8.get(3))[0]);
                  if (var53 > 0 && var56 > 0) {
                     var2.append(" §d(").append(var53).append("s§7|").append(var56).append("s) ");
                  } else if (var53 > 0) {
                     var2.append(" §d(").append(var53).append("s) ");
                  } else if (var56 > 0) {
                     var2.append(" §d(").append(var56).append("s) ");
                  } else {
                     var2.append(" §d(❤) ");
                  }

                  var2.append(var39).append(" ");
                  if (var46 > 0) {
                     var2.append("§c").append(var46).append("s");
                  } else {
                     var2.append("§aReady");
                  }
               }

               return var2.toString();
            } else if (var5 == GemType.PUFF) {
               String var24 = this.a(var5, 0);
               int var31 = var3.getRemainingCooldown(var1, ((String[])var8.get(0))[0]);
               var2.append(var24).append(" ");
               if (var31 > 0) {
                  var2.append("§c").append(var31).append("s");
               } else {
                  var2.append("§aReady");
               }

               if (var6 == 2) {
                  String var38 = this.a(var5, 1);
                  int var45 = var3.getRemainingCooldown(var1, ((String[])var8.get(1))[0]);
                  int var52 = var3.getRemainingCooldown(var1, ((String[])var8.get(2))[0]);
                  if (var52 > 0) {
                     var2.append(" §f(").append(var52).append("s) ");
                  } else {
                     var2.append(" §f(✨) ");
                  }

                  var2.append(var38).append(" ");
                  if (var45 > 0) {
                     var2.append("§c").append(var45).append("s");
                  } else {
                     var2.append("§aReady");
                  }
               }

               return var2.toString();
            } else if (var5 == GemType.SPEED) {
               String var23 = this.a(var5, 0);
               int var30 = var3.getRemainingCooldown(var1, ((String[])var8.get(0))[0]);
               var2.append(var23).append(" ");
               if (var30 > 0) {
                  var2.append("§c").append(var30).append("s");
               } else {
                  var2.append("§aReady");
               }

               if (var6 == 2 && var8.size() > 1) {
                  String var37 = this.a(var5, 1);
                  int var44 = var3.getRemainingCooldown(var1, ((String[])var8.get(1))[0]);
                  int var51 = var3.getRemainingCooldown(var1, ((String[])var8.get(2))[0]);
                  if (var51 > 0) {
                     var2.append(" §a(").append(var51).append("s) ");
                  } else {
                     var2.append(" §a(⚡) ");
                  }

                  var2.append(var37).append(" ");
                  if (var44 > 0) {
                     var2.append("§c").append(var44).append("s");
                  } else {
                     var2.append("§aReady");
                  }
               }

               return var2.toString();
            } else if (var5 == GemType.WEALTH) {
               String var22 = this.a(var5, 0);
               int var29 = var3.getRemainingCooldown(var1, ((String[])var8.get(0))[0]);
               var2.append(var22).append(" ");
               if (var29 > 0) {
                  var2.append("§c").append(var29).append("s");
               } else {
                  var2.append("§aReady");
               }

               if (var6 == 2 && var8.size() > 1) {
                  String var36 = this.a(var5, 1);
                  int var43 = var3.getRemainingCooldown(var1, ((String[])var8.get(1))[0]);
                  int var50 = var8.size() > 2 ? var3.getRemainingCooldown(var1, ((String[])var8.get(2))[0]) : 0;
                  int var55 = var8.size() > 3 ? var3.getRemainingCooldown(var1, ((String[])var8.get(3))[0]) : 0;
                  if (var50 > 0 && var55 > 0) {
                     var2.append(" §e(").append(var50).append("s§7|").append(var55).append("s) ");
                  } else if (var50 > 0) {
                     var2.append(" §e(").append(var50).append("s) ");
                  } else if (var55 > 0) {
                     var2.append(" §e(").append(var55).append("s) ");
                  } else {
                     var2.append(" §e(\ud83d\udcb0) ");
                  }

                  var2.append(var36).append(" ");
                  if (var43 > 0) {
                     var2.append("§c").append(var43).append("s");
                  } else {
                     var2.append("§aReady");
                  }
               }

               return var2.toString();
            } else if (var5 == GemType.STRENGTH) {
               String var21 = this.a(var5, 0);
               int var28 = var3.getRemainingCooldown(var1, ((String[])var8.get(0))[0]);
               var2.append(var21).append(" ");
               if (var28 > 0) {
                  var2.append("§c").append(var28).append("s");
               } else {
                  var2.append("§aReady");
               }

               if (var6 == 2 && var8.size() > 1) {
                  String var35 = this.a(var5, 1);
                  int var42 = var3.getRemainingCooldown(var1, ((String[])var8.get(1))[0]);
                  int var49 = var8.size() > 2 ? var3.getRemainingCooldown(var1, ((String[])var8.get(2))[0]) : 0;
                  if (var49 > 0) {
                     var2.append(" §6(").append(var49).append("s) ");
                  } else {
                     var2.append(" §6(\ud83d\udcaa) ");
                  }

                  var2.append(var35).append(" ");
                  if (var42 > 0) {
                     var2.append("§c").append(var42).append("s");
                  } else {
                     var2.append("§aReady");
                  }
               }

               return var2.toString();
            } else {
               String[] var9 = (String[])var8.get(0);
               String var10 = var9[0];
               String var11 = this.a(var5, 0);
               int var12 = var3.getRemainingCooldown(var1, var10);
               var2.append(var11).append(" ");
               if (var12 > 0) {
                  var2.append("§c").append(var12).append("s");
               } else {
                  var2.append("§aReady");
               }

               if (var6 == 2 && var8.size() > 1) {
                  String[] var13 = (String[])var8.get(1);
                  String var14 = var13[0];
                  String var15 = this.a(var5, 1);
                  int var16 = var3.getRemainingCooldown(var1, var14);
                  var2.append(" ").append(var7).append("| ").append(var15).append(" ");
                  if (var16 > 0) {
                     var2.append("§c").append(var16).append("s");
                  } else {
                     var2.append("§aReady");
                  }

                  for(int var17 = 2; var17 < var8.size(); ++var17) {
                     String[] var18 = (String[])var8.get(var17);
                     String var19 = var18[0];
                     int var20 = var3.getRemainingCooldown(var1, var19);
                     var2.append(" ").append(var7).append("| ").append(var7).append("✦ ");
                     if (var20 > 0) {
                        var2.append("§c").append(var20).append("s");
                     } else {
                        var2.append("§aReady");
                     }
                  }
               }

               return var2.toString();
            }
         }
      }
   }

   private String a(GemType var1) {
      String var10000;
      switch (var1) {
         case ASTRA -> var10000 = "\ue000";
         case FIRE -> var10000 = "\ue001";
         case FLUX -> var10000 = "\ue002";
         case LIFE -> var10000 = "\ue003";
         case PUFF -> var10000 = "\ue004";
         case SPEED -> var10000 = "\ue005";
         case STRENGTH -> var10000 = "\ue006";
         case WEALTH -> var10000 = "\ue007";
         default -> throw new IncompatibleClassChangeError();
      }

      return var10000;
   }

   private String a(GemType var1, int var2) {
      String var10000;
      switch (var1) {
         case ASTRA -> var10000 = var2 == 0 ? "\ue010" : "\ue011";
         case FIRE -> var10000 = var2 == 0 ? "\ue012" : "\ue013";
         case FLUX -> var10000 = var2 == 0 ? "\ue014" : "\ue015";
         case LIFE -> var10000 = var2 == 0 ? "\ue016" : "\ue017";
         case PUFF -> var10000 = var2 == 0 ? "\ue018" : "\ue019";
         case SPEED -> var10000 = var2 == 0 ? "\ue01a" : "\ue01b";
         case STRENGTH -> var10000 = var2 == 0 ? "\ue01c" : "\ue01d";
         case WEALTH -> var10000 = var2 == 0 ? "\ue01e" : "\ue01f";
         default -> throw new IncompatibleClassChangeError();
      }

      return var10000;
   }

   private String b(GemType var1) {
      switch (var1) {
         case ASTRA -> {
            return "✦";
         }
         case FIRE -> {
            return "\ud83d\udd25";
         }
         case FLUX -> {
            return "⚡";
         }
         case LIFE -> {
            return "❤";
         }
         case PUFF -> {
            return "\ud83d\udca8";
         }
         case SPEED -> {
            return "⚡";
         }
         case STRENGTH -> {
            return "⚔";
         }
         case WEALTH -> {
            return "\ud83d\udcb0";
         }
         default -> {
            return "◆";
         }
      }
   }

   private String c(GemType var1) {
      switch (var1) {
         case ASTRA -> {
            return "§5";
         }
         case FIRE -> {
            return "§c";
         }
         case FLUX -> {
            return "§b";
         }
         case LIFE -> {
            return "§d";
         }
         case PUFF -> {
            return "§f";
         }
         case SPEED -> {
            return "§a";
         }
         case STRENGTH -> {
            return "§6";
         }
         case WEALTH -> {
            return "§e";
         }
         default -> {
            return "§7";
         }
      }
   }

   private String a(int var1) {
      StringBuilder var2 = new StringBuilder();
      String var3;
      String var4;
      if (var1 >= 8) {
         var3 = "§d§l";
         var4 = "●";
      } else if (var1 >= 5) {
         var3 = "§b";
         var4 = "●";
      } else if (var1 >= 3) {
         var3 = "§e";
         var4 = "●";
      } else if (var1 >= 2) {
         var3 = "§6";
         var4 = "●";
      } else if (var1 == 1) {
         var3 = "§4";
         var4 = "○";
      } else {
         var3 = "§c§l";
         var4 = "○";
      }

      var2.append(var3);

      for(int var5 = 0; var5 < 10; ++var5) {
         if (var5 < var1) {
            var2.append(var4);
         } else {
            var2.append("§8○");
         }
      }

      var2.append(" §7(").append(var1).append("/10)");
      return var2.toString();
   }

   private String a(int var1, int var2, String var3) {
      double var4 = (double)1.0F - (double)var1 / (double)var2;
      int var6 = (int)(var4 * (double)10.0F);
      StringBuilder var7 = new StringBuilder();
      String var8;
      if (var1 <= 3) {
         var8 = "§a";
      } else if (var1 <= 10) {
         var8 = "§e";
      } else if (var1 <= 30) {
         var8 = "§6";
      } else {
         var8 = "§c";
      }

      var7.append(var8).append(var3).append(" ");
      var7.append("§7[");

      for(int var9 = 0; var9 < 10; ++var9) {
         if (var9 < var6) {
            var7.append("§a▰");
         } else {
            var7.append("§8▱");
         }
      }

      var7.append("§7] ").append(var8).append(var1).append("s");
      return var7.toString();
   }

   private String a(Player var1, a var2) {
      if (var2.b == null) {
         return "";
      } else {
         GemRegistryImpl var3 = this.a.getGemRegistry();
         if (var3 == null) {
            return "";
         } else {
            List var4 = var3.getCooldownEntries(var2.b);
            if (var4 != null && !var4.isEmpty()) {
               GemDefinition var5 = var3.getGem(var2.b);
               String var6 = var5 != null ? var5.getColor() : "§7";
               AbilityManager var7 = this.a.getAbilityManager();
               StringBuilder var8 = new StringBuilder();
               boolean var9 = true;

               for(CooldownEntry var11 : var4) {
                  if (!var9) {
                     var8.append(" ").append(var6).append("| ");
                  }

                  var9 = false;
                  int var12 = var7.getRemainingCooldown(var1, var11.getAbilityKey());
                  var8.append(var6).append(var11.getDisplayName()).append(" ");
                  if (var12 > 0) {
                     var8.append("§c").append(var12).append("s");
                  } else {
                     var8.append("§aReady");
                  }
               }

               return var8.toString();
            } else {
               return "";
            }
         }
      }
   }

   public void stop() {
      if (this.b != null) {
         this.b.cancel();
      }

   }

   static {
      c.put(GemType.FIRE, Arrays.asList(new String[]{"fire-fireball", "Fireball"}, new String[]{"fire-campfire", "Campfire"}, new String[]{"fire-crisp", "Crisp"}, new String[]{"fire-meteor-shower", "Meteor"}));
      c.put(GemType.ASTRA, Arrays.asList(new String[]{"astra-daggers", "Daggers"}, new String[]{"astra-projection", "Projection"}));
      c.put(GemType.LIFE, Arrays.asList(new String[]{"life-drainer", "Drainer"}, new String[]{"life-circle-of-life", "Circle"}, new String[]{"life-vitality-vortex", "Vortex"}, new String[]{"life-heart-lock", "Lock"}));
      c.put(GemType.FLUX, Arrays.asList(new String[]{"flux-beam", "Beam"}, new String[]{"flux-ground", "Ground"}, new String[]{"flux-flashbang", "Flash"}, new String[]{"flux-kinetic-burst", "Kinetic"}));
      c.put(GemType.PUFF, Arrays.asList(new String[]{"puff-dash", "Dash"}, new String[]{"puff-breezy-bash", "Bash"}, new String[]{"puff-group-bash", "Group"}));
      c.put(GemType.SPEED, Arrays.asList(new String[]{"speed-blur", "Blur"}, new String[]{"speed-storm", "Storm"}, new String[]{"speed-terminal", "Terminal"}));
      c.put(GemType.STRENGTH, Arrays.asList(new String[]{"strength-nullify", "Nullify"}, new String[]{"strength-frailer", "Frailer"}, new String[]{"strength-shadow-stalker", "Stalker"}));
      c.put(GemType.WEALTH, Arrays.asList(new String[]{"wealth-unfortunate", "Unfortunate"}, new String[]{"wealth-rich-rush", "Rush"}, new String[]{"wealth-item-lock", "Lock"}, new String[]{"wealth-amplification", "Amplify"}));
   }

   private static class a {
      final GemType a;
      final String b;
      final int c;
      final String d;

      a(GemType var1, String var2, int var3, String var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }
   }
}


static public class CriticalHitManager {
   private final BlissGems a;
   private final Map<UUID, Integer> b = new HashMap();

   public CriticalHitManager(BlissGems var1) {
      this.a = var1;
   }

   public boolean registerHit(Player var1) {
      UUID var2 = var1.getUniqueId();
      int var3 = (Integer)this.b.merge(var2, 1, Integer::sum);
      int var4 = this.a.getConfig().getInt("abilities.strength-chad.hit-threshold", 4);
      if (var3 >= var4) {
         this.b.put(var2, 0);
         Particle.DustOptions var5 = new Particle.DustOptions(ParticleUtils.STRENGTH_RED, 2.0F);
         var1.spawnParticle(Particle.CRIT, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 50, (double)0.5F, (double)0.5F, (double)0.5F, 0.3);
         var1.spawnParticle(Particle.ENCHANTED_HIT, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
         var1.spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 20, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var5, true);
         var1.playSound(var1.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0F, 0.7F);
         var1.playSound(var1.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.5F, 2.0F);
         return true;
      } else {
         if (var3 == var4 - 1) {
            var1.spawnParticle(Particle.CRIT, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 15, 0.3, (double)0.5F, 0.3, 0.1);
            var1.playSound(var1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.7F, 1.8F);
         }

         return false;
      }
   }

   public int getHitCount(Player var1) {
      return (Integer)this.b.getOrDefault(var1.getUniqueId(), 0);
   }

   public void resetHitCount(Player var1) {
      this.b.remove(var1.getUniqueId());
   }

   public void clearHitData(UUID var1) {
      this.b.remove(var1);
   }
}


static public class EnergyManager {
   private final BlissGems a;
   private final File b;
   private final Map<UUID, Integer> c;

   public EnergyManager(BlissGems var1) {
      this.a = var1;
      this.b = new File(var1.getDataFolder(), "playerdata");
      this.c = new HashMap();
      if (!this.b.exists()) {
         this.b.mkdirs();
      }

   }

   public int getEnergy(Player var1) {
      return (Integer)this.c.computeIfAbsent(var1.getUniqueId(), (var2) -> {
         FileConfiguration var3 = this.a(var1);
         return var3.getInt("energy", this.a.getConfigManager().getStartingEnergy());
      });
   }

   public void setEnergy(Player var1, int var2) {
      int var3 = this.a.getConfigManager().getMaxEnergy();
      var2 = Math.max(0, Math.min(var3, var2));
      this.c.put(var1.getUniqueId(), var2);
      this.a(var1, var2);
      this.a.getGemManager().updateGemTextures(var1);
      if (this.a.getAchievementManager() != null) {
         if (var2 == 0) {
            this.a.getAchievementManager().unlock(var1, Achievement.SHATTERED);
         }

         if (var2 == var3) {
            this.a.getAchievementManager().unlock(var1, Achievement.OVERFLOWING);
         }
      }

   }

   public void addEnergy(Player var1, int var2) {
      this.setEnergy(var1, this.getEnergy(var1) + var2);
   }

   public void removeEnergy(Player var1, int var2) {
      int var3 = this.getEnergy(var1);
      if (var3 > 0) {
         this.setEnergy(var1, var3 - var2);
      }
   }

   public EnergyState getEnergyState(Player var1) {
      return EnergyState.fromEnergy(this.getEnergy(var1));
   }

   public boolean arePassivesActive(Player var1) {
      return this.getEnergyState(var1).passivesActive();
   }

   public boolean canUseAbilities(Player var1) {
      return this.getEnergyState(var1).abilitiesUsable();
   }

   private FileConfiguration a(Player var1) {
      File var2 = new File(this.b, String.valueOf(var1.getUniqueId()) + ".yml");
      return !var2.exists() ? new YamlConfiguration() : YamlConfiguration.loadConfiguration(var2);
   }

   private void a(Player var1, int var2) {
      File var3 = new File(this.b, String.valueOf(var1.getUniqueId()) + ".yml");
      FileConfiguration var4 = this.a(var1);
      var4.set("energy", var2);

      try {
         var4.save(var3);
      } catch (IOException var6) {
         Logger var10000 = this.a.getLogger();
         String var10001 = var1.getName();
         var10000.warning("Failed to save player data for " + var10001 + ": " + var6.getMessage());
      }

   }

   public void saveAll() {
      for(Player var2 : this.a.getServer().getOnlinePlayers()) {
         if (this.c.containsKey(var2.getUniqueId())) {
            this.a(var2, (Integer)this.c.get(var2.getUniqueId()));
         }
      }

   }

   public void clearCache(UUID var1) {
      this.c.remove(var1);
   }
}


static public class EnhancedGuiManager implements Listener {
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


static public class FlowStateManager {
   private final BlissGems a;
   private final Map<UUID, a> b = new HashMap();
   private static final int c = 3000;
   private static final int d = 5;

   public FlowStateManager(BlissGems var1) {
      this.a = var1;
   }

   public void registerAction(Player var1, ActionType var2) {
      UUID var3 = var1.getUniqueId();
      a var4 = (a)this.b.computeIfAbsent(var3, (var0) -> new a());
      long var5 = System.currentTimeMillis();
      if (var4.a() == var2) {
         long var7 = var5 - var4.b();
         if (var7 <= 3000L) {
            int var9 = Math.min(var4.c() + 1, 5);
            var4.a(var9);
            this.a(var1, var9);
            if (var9 > var4.c()) {
               var1.spawnParticle(Particle.ELECTRIC_SPARK, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 10 + var9 * 2, 0.3, (double)0.5F, 0.3, 0.05);
               if (var9 == 5) {
                  var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0F, 2.0F);
                  var1.sendMessage("§b§l⚡ MAX FLOW STATE!");
               } else if (var9 % 2 == 0) {
                  var1.playSound(var1.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 1.5F + (float)var9 * 0.2F);
               }
            }
         } else {
            var4.a(1);
            this.a(var1, 1);
         }
      } else {
         var4.a(1);
         this.a(var1, 1);
      }

      var4.a(var2);
      var4.a(var5);
   }

   private void a(Player var1, int var2) {
      if (var2 > 0) {
         int var3 = Math.min((var2 + 1) / 2, 2);
         var1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, var3, false, false));
         if (var2 >= 3) {
            int var4 = var2 >= 4 ? 1 : 0;
            var1.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 100, var4, false, false));
         }

         if (var2 >= 3) {
            var1.spawnParticle(Particle.ELECTRIC_SPARK, var1.getLocation().add((double)0.0F, (double)0.5F, (double)0.0F), 5, 0.3, 0.3, 0.3, 0.01);
         }

      }
   }

   public int getFlowLevel(Player var1) {
      a var2 = (a)this.b.get(var1.getUniqueId());
      if (var2 == null) {
         return 0;
      } else {
         long var3 = System.currentTimeMillis() - var2.b();
         return var3 > 3000L ? 0 : var2.c();
      }
   }

   public void resetFlowState(Player var1) {
      this.b.remove(var1.getUniqueId());
   }

   public void clearFlowState(UUID var1) {
      this.b.remove(var1);
   }

   public static enum ActionType {
      BLOCK_BREAK,
      ARROW_SHOOT,
      ATTACK,
      SPRINT,
      JUMP;

      // $FF: synthetic method
      private static ActionType[] a() {
         return new ActionType[]{BLOCK_BREAK, ARROW_SHOOT, ATTACK, SPRINT, JUMP};
      }
   }

   private static class a {
      private ActionType a = null;
      private long b = 0L;
      private int c = 0;

      public a() {
      }

      public ActionType a() {
         return this.a;
      }

      public void a(ActionType var1) {
         this.a = var1;
      }

      public long b() {
         return this.b;
      }

      public void a(long var1) {
         this.b = var1;
      }

      public int c() {
         return this.c;
      }

      public void a(int var1) {
         this.c = var1;
      }
   }
}


static public class GemManager {
   private final BlissGems a;
   private final Map<UUID, ActiveGem> b;

   public GemManager(BlissGems var1) {
      this.a = var1;
      this.b = new HashMap();
   }

   public void updateActiveGem(Player var1) {
      ActiveGem var2 = (ActiveGem)this.b.get(var1.getUniqueId());
      ItemStack[] var3 = var1.getInventory().getContents();
      ActiveGem var4 = null;
      GemRegistryImpl var5 = this.a.getGemRegistry();

      for(ItemStack var9 : var3) {
         String var10;
         if (var9 != null && (var10 = CustomItemManager.getIdByItem(var9)) != null) {
            if (GemType.isGem(var10)) {
               GemType var11 = GemType.fromOraxenId(var10);
               int var12 = GemType.getTierFromOraxenId(var10);
               if (var11 != null && this.a.getConfigManager().isGemEnabled(var11)) {
                  var4 = new ActiveGem(var11, var12);
                  break;
               }
            }

            if (var5 != null && var5.isRegisteredGem(var10)) {
               String var14 = var5.gemIdFromItemId(var10);
               int var15 = var5.tierFromItemId(var10);
               var4 = new ActiveGem(var14, var15);
               break;
            }
         }
      }

      if (var2 != null && (var4 == null || !Objects.equals(var2.getGemId(), var4.getGemId())) && var2.getGemId() != null && var5 != null) {
         GemAbilityHandler var13 = var5.getAbilityHandler(var2.getGemId());
         if (var13 != null) {
            var13.cleanup(var1);
         }
      }

      if (var4 != null) {
         this.b.put(var1.getUniqueId(), var4);
      } else {
         this.b.remove(var1.getUniqueId());
      }

   }

   public ActiveGem getActiveGem(Player var1) {
      return (ActiveGem)this.b.get(var1.getUniqueId());
   }

   public boolean hasActiveGem(Player var1) {
      return this.b.containsKey(var1.getUniqueId());
   }

   public GemType getGemType(Player var1) {
      ActiveGem var2 = this.getActiveGem(var1);
      return var2 != null ? var2.getType() : null;
   }

   public int getGemTier(Player var1) {
      ActiveGem var2 = this.getActiveGem(var1);
      return var2 != null ? var2.getTier() : 1;
   }

   public String getGemId(Player var1) {
      ActiveGem var2 = this.getActiveGem(var1);
      return var2 != null ? var2.getGemId() : null;
   }

   public boolean hasGemType(Player var1, GemType var2) {
      ActiveGem var3 = this.getActiveGem(var1);
      return var3 != null && var3.getType() == var2;
   }

   public boolean hasGemInOffhand(Player var1) {
      ItemStack var2 = var1.getInventory().getItemInOffHand();
      if (var2 == null) {
         return false;
      } else {
         String var3 = CustomItemManager.getIdByItem(var2);
         if (var3 == null) {
            return false;
         } else if (GemType.isGem(var3)) {
            GemType var5 = GemType.fromOraxenId(var3);
            return var5 != null && this.a.getConfigManager().isGemEnabled(var5);
         } else {
            GemRegistryImpl var4 = this.a.getGemRegistry();
            return var4 != null && var4.isRegisteredGem(var3);
         }
      }
   }

   public boolean hasGemTypeInOffhand(Player var1, GemType var2) {
      ItemStack var3 = var1.getInventory().getItemInOffHand();
      if (var3 == null) {
         return false;
      } else {
         String var4 = CustomItemManager.getIdByItem(var3);
         if (var4 != null && GemType.isGem(var4)) {
            GemType var5 = GemType.fromOraxenId(var4);
            return var5 == var2;
         } else {
            return false;
         }
      }
   }

   public GemType getGemTypeFromOffhand(Player var1) {
      ItemStack var2 = var1.getInventory().getItemInOffHand();
      if (var2 == null) {
         return null;
      } else {
         String var3 = CustomItemManager.getIdByItem(var2);
         return var3 != null && GemType.isGem(var3) ? GemType.fromOraxenId(var3) : null;
      }
   }

   public String getGemIdFromOffhand(Player var1) {
      ItemStack var2 = var1.getInventory().getItemInOffHand();
      if (var2 == null) {
         return null;
      } else {
         String var3 = CustomItemManager.getIdByItem(var2);
         if (var3 == null) {
            return null;
         } else {
            GemType var4 = GemType.fromOraxenId(var3);
            if (var4 != null) {
               return var4.getId();
            } else {
               GemRegistryImpl var5 = this.a.getGemRegistry();
               return var5 != null ? var5.gemIdFromItemId(var3) : null;
            }
         }
      }
   }

   public int getTierFromOffhand(Player var1) {
      ItemStack var2 = var1.getInventory().getItemInOffHand();
      if (var2 == null) {
         return 1;
      } else {
         String var3 = CustomItemManager.getIdByItem(var2);
         if (var3 == null) {
            return 1;
         } else if (GemType.isGem(var3)) {
            return GemType.getTierFromOraxenId(var3);
         } else {
            GemRegistryImpl var4 = this.a.getGemRegistry();
            return var4 != null && var4.isRegisteredGem(var3) ? var4.tierFromItemId(var3) : 1;
         }
      }
   }

   public boolean giveGem(Player var1, GemType var2, int var3) {
      String var4 = GemType.buildOraxenId(var2, var3);
      int var5 = this.a.getEnergyManager().getEnergy(var1);
      ItemStack var6 = CustomItemManager.getItemById(var4, var5);
      if (var6 != null) {
         var1.getInventory().addItem(new ItemStack[]{var6});
         this.updateActiveGem(var1);
         return true;
      } else {
         return false;
      }
   }

   public boolean giveGem(Player var1, String var2, int var3) {
      for(GemType var7 : GemType.values()) {
         if (var7.getId().equalsIgnoreCase(var2)) {
            return this.giveGem(var1, var7, var3);
         }
      }

      GemRegistryImpl var9 = this.a.getGemRegistry();
      if (var9 != null) {
         GemDefinition var10 = var9.getGem(var2);
         if (var10 != null) {
            String var11 = var10.buildItemId(var3);
            int var12 = this.a.getEnergyManager().getEnergy(var1);
            ItemStack var8 = CustomItemManager.getItemById(var11, var12);
            if (var8 != null) {
               var1.getInventory().addItem(new ItemStack[]{var8});
               this.updateActiveGem(var1);
               return true;
            }
         }
      }

      return false;
   }

   public ItemStack findGemInInventory(Player var1) {
      GemRegistryImpl var2 = this.a.getGemRegistry();

      for(ItemStack var6 : var1.getInventory().getContents()) {
         String var7;
         if (var6 != null && (var7 = CustomItemManager.getIdByItem(var6)) != null) {
            if (GemType.isGem(var7)) {
               return var6;
            }

            if (var2 != null && var2.isRegisteredGem(var7)) {
               return var6;
            }
         }
      }

      for(ItemStack var11 : var1.getEnderChest().getContents()) {
         String var12;
         if (var11 != null && (var12 = CustomItemManager.getIdByItem(var11)) != null) {
            if (GemType.isGem(var12)) {
               return var11;
            }

            if (var2 != null && var2.isRegisteredGem(var12)) {
               return var11;
            }
         }
      }

      return null;
   }

   public boolean isAnyGem(String var1) {
      if (var1 == null) {
         return false;
      } else if (GemType.isGem(var1)) {
         return true;
      } else {
         GemRegistryImpl var2 = this.a.getGemRegistry();
         return var2 != null && var2.isRegisteredGem(var1);
      }
   }

   public boolean replaceGemType(Player var1, GemType var2) {
      ItemStack var3 = this.findGemInInventory(var1);
      if (var3 == null) {
         return false;
      } else {
         String var4 = CustomItemManager.getIdByItem(var3);
         if (var4 == null) {
            return false;
         } else {
            int var5 = GemType.getTierFromOraxenId(var4);
            String var6 = GemType.buildOraxenId(var2, var5);
            int var7 = this.a.getEnergyManager().getEnergy(var1);
            ItemStack var8 = CustomItemManager.getItemById(var6, var7);
            if (var8 == null) {
               return false;
            } else {
               for(int var9 = 0; var9 < var1.getInventory().getSize(); ++var9) {
                  ItemStack var10 = var1.getInventory().getItem(var9);
                  if (var10 != null && var10.equals(var3)) {
                     var1.getInventory().setItem(var9, var8);
                     this.updateActiveGem(var1);
                     return true;
                  }
               }

               return false;
            }
         }
      }
   }

   public boolean upgradeGem(Player var1, GemType var2) {
      ItemStack var3 = this.findGemInInventory(var1);
      if (var3 == null) {
         return false;
      } else {
         String var4 = CustomItemManager.getIdByItem(var3);
         if (var4 == null) {
            return false;
         } else {
            GemType var5 = GemType.fromOraxenId(var4);
            int var6 = GemType.getTierFromOraxenId(var4);
            if (var5 == var2 && var6 == 1) {
               String var7 = GemType.buildOraxenId(var2, 2);
               int var8 = this.a.getEnergyManager().getEnergy(var1);
               ItemStack var9 = CustomItemManager.getItemById(var7, var8);
               if (var9 == null) {
                  return false;
               } else {
                  for(int var10 = 0; var10 < var1.getInventory().getSize(); ++var10) {
                     ItemStack var11 = var1.getInventory().getItem(var10);
                     if (var11 != null && var11.equals(var3)) {
                        var1.getInventory().setItem(var10, var9);
                        this.updateActiveGem(var1);
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }
   }

   public void updateGemTextures(Player var1) {
      int var2 = this.a.getEnergyManager().getEnergy(var1);

      for(ItemStack var6 : var1.getInventory().getContents()) {
         if (var6 != null) {
            CustomItemManager.updateGemTexture(var6, var2);
         }
      }

   }

   public void clearCache(UUID var1) {
      this.b.remove(var1);
   }

   public static class ActiveGem {
      private final GemType a;
      private final String b;
      private final int c;

      public ActiveGem(GemType var1, int var2) {
         this.a = var1;
         this.b = var1 != null ? var1.getId() : null;
         this.c = var2;
      }

      public ActiveGem(String var1, int var2) {
         this.a = null;
         this.b = var1;
         this.c = var2;
      }

      public GemType getType() {
         return this.a;
      }

      public String getGemId() {
         return this.b;
      }

      public int getTier() {
         return this.c;
      }
   }
}


static public class GemRegistryImpl implements GemRegistry {
   private static final Pattern a = Pattern.compile("^(.+)_gem_t(\\d+)$");
   private final BlissGems b;
   private final Logger c;
   private final Map<String, GemDefinition> d = new ConcurrentHashMap();
   private final Map<String, GemAbilityHandler> e = new ConcurrentHashMap();
   private final Map<String, GemPassiveHandler> f = new ConcurrentHashMap();
   private final Map<String, List<CooldownEntry>> g = new ConcurrentHashMap();

   public GemRegistryImpl(BlissGems var1) {
      this.b = var1;
      this.c = var1.getLogger();
   }

   public void registerGem(GemDefinition var1) {
      String var2 = var1.getId();
      if (this.d.containsKey(var2)) {
         throw new IllegalArgumentException("Gem ID '" + var2 + "' is already registered!");
      } else {
         this.d.put(var2, var1);
         if (!"BlissGems".equals(var1.getPluginName())) {
            this.a(var1);
         }

         this.c.info("[AddonAPI] Registered gem: " + var2 + " (" + var1.getPluginName() + ")");
      }
   }

   private void a(GemDefinition var1) {
      if (var1.getT1CustomModelData() > 0) {
         String var2 = var1.buildItemId(1);
         String var3 = var1.getT1DisplayName() != null ? var1.getT1DisplayName() : var1.getColor() + "§l" + var1.getDisplayName().toUpperCase() + " GEM";
         CustomItemManager.registerAddonItem(var2, var1.getMaterial(), var1.getT1CustomModelData(), var3, var1.getT1Lore());
      }

      if (var1.getMaxTier() >= 2 && var1.getT2CustomModelData() > 0) {
         String var4 = var1.buildItemId(2);
         String var5 = var1.getT2DisplayName() != null ? var1.getT2DisplayName() : var1.getColor() + "§l" + var1.getDisplayName().toUpperCase() + " GEM";
         CustomItemManager.registerAddonItem(var4, var1.getMaterial(), var1.getT2CustomModelData(), var5, var1.getT2Lore());
      }

   }

   public void registerAbilities(String var1, GemAbilityHandler var2) {
      this.e.put(var1, var2);
   }

   public void registerPassives(String var1, GemPassiveHandler var2) {
      this.f.put(var1, var2);
   }

   public void registerCooldowns(String var1, List<CooldownEntry> var2) {
      this.g.put(var1, Collections.unmodifiableList(new ArrayList(var2)));
   }

   public GemDefinition getGem(String var1) {
      return (GemDefinition)this.d.get(var1);
   }

   public GemAbilityHandler getAbilityHandler(String var1) {
      return (GemAbilityHandler)this.e.get(var1);
   }

   public GemPassiveHandler getPassiveHandler(String var1) {
      return (GemPassiveHandler)this.f.get(var1);
   }

   public List<CooldownEntry> getCooldownEntries(String var1) {
      return (List)this.g.getOrDefault(var1, Collections.emptyList());
   }

   public Collection<GemDefinition> getAllGems() {
      return Collections.unmodifiableCollection(this.d.values());
   }

   public boolean isRegisteredGem(String var1) {
      return this.gemIdFromItemId(var1) != null;
   }

   public String gemIdFromItemId(String var1) {
      if (var1 == null) {
         return null;
      } else {
         Matcher var2 = a.matcher(var1);
         if (var2.matches()) {
            String var3 = var2.group(1);
            if (this.d.containsKey(var3)) {
               return var3;
            }
         }

         return null;
      }
   }

   public int tierFromItemId(String var1) {
      if (var1 == null) {
         return 1;
      } else {
         Matcher var2 = a.matcher(var1);
         if (var2.matches()) {
            try {
               return Integer.parseInt(var2.group(2));
            } catch (NumberFormatException var4) {
               return 1;
            }
         } else {
            return 1;
         }
      }
   }
}


static public class GemRitualManager {
   private final BlissGems a;

   public GemRitualManager(BlissGems var1) {
      this.a = var1;
   }

   public void performGemRitual(final Player var1, GemType var2, boolean var3, int var4) {
      if (!var3 && this.a.getAchievementManager() != null) {
         this.a.getAchievementManager().unlock(var1, Achievement.REAWAKENING);
      }

      Location var5 = var1.getLocation().clone();
      Color var6 = this.a(var2);
      var1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 300, 0, false, false));
      var1.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 1, false, false));
      final ArrayList var7 = new ArrayList();
      GemType[] var8 = GemType.values();
      Location var9 = var1.getLocation();

      for(int var10 = 0; var10 < var8.length; ++var10) {
         String var11 = this.a(var8[var10], var4);
         ItemStack var12 = CustomItemManager.getItemById(var11, 10);
         if (var12 != null) {
            double var13 = (double)var10 / (double)8.0F * (double)2.0F * Math.PI;
            double var15 = (double)2.5F;
            double var17 = (double)1.5F;
            double var19 = Math.cos(var13) * var15;
            double var21 = Math.sin(var13) * var15;
            Location var23 = var9.clone().add(var19, var17, var21);
            ItemDisplay var24 = (ItemDisplay)var1.getWorld().spawn(var23, ItemDisplay.class);
            var24.setItemStack(var12);
            var24.setBillboard(Billboard.FIXED);
            var24.setViewRange(100.0F);
            var24.setGlowing(true);
            var7.add(var24);
         }
      }

      (new BukkitRunnable() {
         int a = 0;
         final int b = 80;

         public void run() {
            if (var1.isOnline() && this.a < 80) {
               for(int var18 = 0; var18 < var7.size(); ++var18) {
                  ItemDisplay var19 = (ItemDisplay)var7.get(var18);
                  if (var19 != null && var19.isValid()) {
                     double var3 = (double)var18 / (double)8.0F * (double)2.0F * Math.PI;
                     double var5 = (double)this.a / (double)20.0F * Math.PI + var3;
                     double var7x = (double)2.5F;
                     double var9 = (double)1.5F + Math.sin((double)this.a / (double)10.0F) * 0.3;
                     double var11 = Math.cos(var5) * var7x;
                     double var13 = Math.sin(var5) * var7x;
                     Location var15 = var1.getLocation().clone().add(var11, var9, var13);
                     var19.teleport(var15);
                     float var16 = (float)(this.a * 5 % 360);
                     Transformation var17 = new Transformation(new Vector3f(0.0F, 0.0F, 0.0F), new AxisAngle4f((float)Math.toRadians((double)var16), 0.0F, 1.0F, 0.0F), new Vector3f(0.7F, 0.7F, 0.7F), new AxisAngle4f(0.0F, 0.0F, 0.0F, 1.0F));
                     var19.setTransformation(var17);
                  }
               }

               if (this.a % 10 == 0) {
                  var1.playSound(var1.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.6F, 1.0F + (float)this.a / 80.0F);
               }

               ++this.a;
            } else {
               for(ItemDisplay var2 : var7) {
                  if (var2 != null && var2.isValid()) {
                     var2.remove();
                  }
               }

               var7.clear();
               this.cancel();
            }
         }
      }).runTaskTimer(this.a, 0L, 1L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
         final Location var5 = var1.getLocation().add((double)0.0F, (double)1.5F, (double)0.0F);
         final ArrayList var6 = new ArrayList();

         for(int var7 = 0; var7 < var8.length; ++var7) {
            String var8x = this.a(var8[var7], var4);
            ItemStack var9 = CustomItemManager.getItemById(var8x, 10);
            if (var9 != null) {
               double var10 = (double)var7 / (double)8.0F * (double)2.0F * Math.PI;
               double var12 = 12.566370614359172 + var10;
               double var14 = (double)2.5F;
               double var16 = (double)1.5F;
               double var18 = Math.cos(var12) * var14;
               double var20 = Math.sin(var12) * var14;
               Location var22 = var1.getLocation().clone().add(var18, var16, var20);
               ItemDisplay var23 = (ItemDisplay)var1.getWorld().spawn(var22, ItemDisplay.class);
               var23.setItemStack(var9);
               var23.setBillboard(Billboard.FIXED);
               var23.setViewRange(100.0F);
               var23.setGlowing(true);
               var6.add(var23);
            }
         }

         (new BukkitRunnable() {
            int a = 0;
            final int b = 40;

            public void run() {
               if (var1.isOnline() && this.a < 40) {
                  double var20 = (double)this.a / (double)40.0F;

                  for(int var3 = 0; var3 < var6.size(); ++var3) {
                     ItemDisplay var4 = (ItemDisplay)var6.get(var3);
                     if (var4 != null && var4.isValid()) {
                        GemType var5x = var8[var3];
                        double var6x = (double)var3 / (double)8.0F * (double)2.0F * Math.PI;
                        double var8x = 12.566370614359172 + var6x;
                        double var10 = (double)2.5F;
                        double var12 = (double)1.5F;
                        Location var14 = var1.getLocation().add(Math.cos(var8x) * var10, var12, Math.sin(var8x) * var10);
                        Location var15;
                        if (var5x == var2) {
                           var15 = var5.clone();
                        } else {
                           var15 = var14.clone().add((double)0.0F, (double)-3.0F, (double)0.0F);
                        }

                        Location var16 = var14.clone().add((var15.getX() - var14.getX()) * var20, (var15.getY() - var14.getY()) * var20, (var15.getZ() - var14.getZ()) * var20);
                        var4.teleport(var16);
                        float var17 = var5x == var2 ? (float)(this.a * 15 % 360) : (float)(this.a * 3 % 360);
                        Transformation var18 = new Transformation(new Vector3f(0.0F, 0.0F, 0.0F), new AxisAngle4f((float)Math.toRadians((double)var17), 0.0F, 1.0F, 0.0F), var5x == var2 ? new Vector3f(1.0F, 1.0F, 1.0F) : new Vector3f(0.7F, 0.7F, 0.7F), new AxisAngle4f(0.0F, 0.0F, 0.0F, 1.0F));
                        var4.setTransformation(var18);
                        if (var5x == var2) {
                           GemRitualManager.this.a(var5x);
                           var1.getWorld().spawnParticle(Particle.FIREWORK, var16, 3, 0.1, 0.1, 0.1, 0.05);
                           var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var16, 2, 0.1, 0.1, 0.1, 0.01);
                        }
                     }
                  }

                  if (this.a % 5 == 0) {
                     var1.playSound(var1.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.7F, 1.0F + (float)var20);
                  }

                  ++this.a;
               } else {
                  for(ItemDisplay var2x : var6) {
                     if (var2x != null && var2x.isValid()) {
                        var2x.remove();
                     }
                  }

                  var6.clear();
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L);
         this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
            var1.playSound(var1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
            var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.8F, 1.8F);
         }, 40L);
      }, 80L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var1.isOnline() && this.a < 20) {
                  double var1x = (double)this.a / (double)20.0F * (double)5.0F;

                  for(int var3 = 0; var3 < 32; ++var3) {
                     double var4 = (double)var3 / (double)32.0F * (double)2.0F * Math.PI;
                     double var6x = Math.cos(var4) * var1x;
                     double var8 = Math.sin(var4) * var1x;
                     Location var10 = var5.clone().add(var6x, 0.1, var8);
                     Particle.DustOptions var11 = new Particle.DustOptions(var6, 1.5F);
                     var1.getWorld().spawnParticle(Particle.DUST, var10, 3, 0.1, 0.1, 0.1, (double)0.0F, var11, true);
                     var1.getWorld().spawnParticle(Particle.END_ROD, var10, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.01);
                  }

                  if (this.a % 5 == 0) {
                     var1.playSound(var5, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.5F, 1.5F);
                  }

                  ++this.a;
               } else {
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L), 120L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> (new BukkitRunnable() {
            int a = 0;
            final int b = 40;

            public void run() {
               if (var1.isOnline() && this.a < 40) {
                  double var1x = (double)this.a / (double)40.0F;
                  double var3 = var1x * (double)5.0F;

                  for(int var5x = 0; var5x < 3; ++var5x) {
                     double var6x = (double)var5x / (double)3.0F * (double)2.0F * Math.PI;
                     double var8 = var1x * (double)6.0F * Math.PI + var6x;
                     double var10 = (double)1.5F * ((double)1.0F - var1x * (double)0.5F);
                     double var12 = Math.cos(var8) * var10;
                     double var14 = Math.sin(var8) * var10;
                     Location var16 = var5.clone().add(var12, var3, var14);
                     Particle.DustOptions var17 = new Particle.DustOptions(var6, 1.8F);
                     var1.getWorld().spawnParticle(Particle.DUST, var16, 5, 0.1, 0.1, 0.1, (double)0.0F, var17, true);
                     var1.getWorld().spawnParticle(Particle.ENCHANT, var16, 8, 0.2, 0.2, 0.2, (double)0.5F);
                     var1.getWorld().spawnParticle(Particle.END_ROD, var16, 2, 0.1, 0.1, 0.1, 0.02);
                  }

                  for(double var18 = (double)0.0F; var18 <= var3; var18 += 0.3) {
                     Particle.DustOptions var7 = new Particle.DustOptions(var6, 0.8F);
                     var1.getWorld().spawnParticle(Particle.DUST, var5.clone().add((double)0.0F, var18, (double)0.0F), 2, 0.15, 0.1, 0.15, (double)0.0F, var7, true);
                  }

                  if (this.a % 10 == 0) {
                     var1.playSound(var5, Sound.BLOCK_BELL_USE, 0.7F, 1.0F + (float)var1x);
                     var1.playSound(var5, Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.5F, 1.5F);
                  }

                  ++this.a;
               } else {
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L), 140L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
         Location var4 = var5.clone().add((double)0.0F, (double)5.0F, (double)0.0F);
         Particle.DustOptions var5x = new Particle.DustOptions(var6, 2.5F);
         var1.getWorld().spawnParticle(Particle.DUST, var4, 200, (double)1.5F, (double)1.5F, (double)1.5F, (double)0.0F, var5x, true);
         var1.getWorld().spawnParticle(Particle.FIREWORK, var4, 100, (double)1.0F, (double)1.0F, (double)1.0F, 0.2);
         var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var4, 80, 1.2, 1.2, 1.2, 0.1);
         var1.getWorld().spawnParticle(Particle.END_ROD, var4, 60, (double)1.5F, (double)1.5F, (double)1.5F, 0.15);
         var1.getWorld().spawnParticle(Particle.ENCHANT, var4, 150, (double)2.0F, (double)2.0F, (double)2.0F, (double)1.0F);

         for(double var6x = (double)0.0F; var6x <= (double)10.0F; var6x += 0.2) {
            Particle.DustOptions var8 = new Particle.DustOptions(var6, 1.5F);
            var1.getWorld().spawnParticle(Particle.DUST, var5.clone().add((double)0.0F, var6x, (double)0.0F), 8, 0.2, 0.1, 0.2, (double)0.0F, var8, true);
            var1.getWorld().spawnParticle(Particle.END_ROD, var5.clone().add((double)0.0F, var6x, (double)0.0F), 3, 0.15, 0.1, 0.15, 0.02);
         }

         var1.playSound(var5, Sound.ITEM_TOTEM_USE, 1.0F, 1.0F);
         var1.playSound(var5, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.6F, 2.0F);
         var1.playSound(var5, Sound.BLOCK_BEACON_ACTIVATE, 1.0F, 1.5F);
         var1.playSound(var5, Sound.BLOCK_END_PORTAL_SPAWN, 0.5F, 1.5F);
         (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var1.isOnline() && this.a < 20) {
                  for(int var1x = 0; var1x < 10; ++var1x) {
                     double var2 = (Math.random() - (double)0.5F) * (double)3.0F;
                     double var4 = (Math.random() - (double)0.5F) * (double)3.0F;
                     double var6x = (double)5.0F - (double)this.a / (double)20.0F * (double)4.5F;
                     Location var8 = var5.clone().add(var2, var6x, var4);
                     Location var9 = var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F);
                     Particle.DustOptions var10 = new Particle.DustOptions(var6, 1.2F);
                     var1.getWorld().spawnParticle(Particle.DUST, var8, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, var10, true);
                     var1.getWorld().spawnParticle(Particle.END_ROD, var8, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.01);
                  }

                  ++this.a;
               } else {
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L);
      }, 180L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
         Location var3 = var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F);
         Particle.DustOptions var4 = new Particle.DustOptions(var6, 2.0F);
         var1.getWorld().spawnParticle(Particle.DUST, var3, 150, 0.8, (double)1.0F, 0.8, (double)0.0F, var4, true);
         var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var3, 50, (double)0.5F, 0.8, (double)0.5F, 0.1);
         var1.getWorld().spawnParticle(Particle.ENCHANT, var3, 100, 0.6, (double)1.0F, 0.6, 0.8);
         var1.getWorld().spawnParticle(Particle.FIREWORK, var3, 30, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
         var1.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, var3, 40, 0.6, 0.8, 0.6, (double)0.0F);

         for(int var5x = 0; var5x < 360; var5x += 10) {
            double var6x = Math.toRadians((double)var5x);
            double var8 = Math.cos(var6x) * (double)2.0F;
            double var10 = Math.sin(var6x) * (double)2.0F;
            Particle.DustOptions var12 = new Particle.DustOptions(var6, 1.5F);
            var1.getWorld().spawnParticle(Particle.DUST, var3.clone().add(var8, (double)0.0F, var10), 5, 0.1, 0.1, 0.1, (double)0.0F, var12, true);
            var1.getWorld().spawnParticle(Particle.END_ROD, var3.clone().add(var8, (double)0.0F, var10), 2, (double)0.0F, (double)0.0F, (double)0.0F, 0.05);
         }

         var1.playSound(var5, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
         var1.playSound(var5, Sound.BLOCK_BELL_USE, 1.0F, 2.0F);
         var1.playSound(var5, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 2.0F);
         var1.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 0, false, false));
      }, 200L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var1.isOnline() && this.a < 40) {
                  double var1x = (double)this.a / (double)40.0F * (double)4.0F * Math.PI;
                  double var3 = (double)1.5F;
                  double var5 = Math.cos(var1x) * var3;
                  double var7 = Math.sin(var1x) * var3;
                  Location var9 = var1.getLocation().add(var5, (double)1.5F, var7);
                  Particle.DustOptions var10 = new Particle.DustOptions(var6, 1.0F);
                  var1.getWorld().spawnParticle(Particle.DUST, var9, 3, 0.1, 0.1, 0.1, (double)0.0F, var10, true);
                  var1.getWorld().spawnParticle(Particle.END_ROD, var9, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.01);
                  if (this.a % 5 == 0) {
                     Location var11 = var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F);
                     var1.getWorld().spawnParticle(Particle.END_ROD, var11, 3, (double)0.5F, (double)0.5F, (double)0.5F, 0.02);
                  }

                  ++this.a;
               } else {
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L), 200L);
   }

   public void performReviveBeaconRitual(final Player var1, final Location var2) {
      final Color var3 = Color.fromRGB(255, 215, 0);
      var1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 0, false, false));
      (new BukkitRunnable() {
         int a = 0;

         public void run() {
            if (var1.isOnline() && this.a < 20) {
               double var1x = (double)this.a / (double)20.0F * (double)6.0F;

               for(int var3x = 0; var3x < 32; ++var3x) {
                  double var4 = (double)var3x / (double)32.0F * (double)2.0F * Math.PI;
                  double var6 = Math.cos(var4) * var1x;
                  double var8 = Math.sin(var4) * var1x;
                  Location var10 = var2.clone().add(var6, 0.1, var8);
                  Particle.DustOptions var11 = new Particle.DustOptions(var3, 2.0F);
                  var1.getWorld().spawnParticle(Particle.DUST, var10, 5, 0.1, 0.1, 0.1, (double)0.0F, var11, true);
                  var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var10, 2, (double)0.0F, (double)0.0F, (double)0.0F, 0.02);
               }

               if (this.a % 5 == 0) {
                  var1.playSound(var2, Sound.BLOCK_BEACON_AMBIENT, 0.7F, 1.5F);
               }

               ++this.a;
            } else {
               this.cancel();
            }
         }
      }).runTaskTimer(this.a, 0L, 1L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> (new BukkitRunnable() {
            int a = 0;
            final int b = 30;

            public void run() {
               if (var1.isOnline() && this.a < 30) {
                  double var1x = (double)this.a / (double)30.0F;
                  double var3x = var1x * (double)8.0F;

                  for(double var5 = (double)0.0F; var5 <= var3x; var5 += 0.3) {
                     Particle.DustOptions var7 = new Particle.DustOptions(var3, 1.5F);
                     var1.getWorld().spawnParticle(Particle.DUST, var2.clone().add((double)0.0F, var5, (double)0.0F), 3, 0.2, 0.1, 0.2, (double)0.0F, var7, true);
                     var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var2.clone().add((double)0.0F, var5, (double)0.0F), 1, 0.1, 0.1, 0.1, 0.01);
                  }

                  if (this.a % 10 == 0) {
                     var1.playSound(var2, Sound.BLOCK_BEACON_POWER_SELECT, 0.8F, 1.0F + (float)var1x);
                  }

                  ++this.a;
               } else {
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L), 20L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
         Location var3x = var2.clone().add((double)0.0F, (double)8.0F, (double)0.0F);
         Particle.DustOptions var4 = new Particle.DustOptions(var3, 3.0F);
         var1.getWorld().spawnParticle(Particle.DUST, var3x, 300, (double)2.0F, (double)2.0F, (double)2.0F, (double)0.0F, var4, true);
         var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var3x, 150, (double)1.5F, (double)1.5F, (double)1.5F, 0.15);
         var1.getWorld().spawnParticle(Particle.FIREWORK, var3x, 80, (double)1.0F, (double)1.0F, (double)1.0F, 0.2);
         var1.getWorld().spawnParticle(Particle.END_ROD, var3x, 100, (double)2.0F, (double)2.0F, (double)2.0F, 0.2);

         for(double var5 = (double)0.0F; var5 <= (double)20.0F; var5 += 0.2) {
            Particle.DustOptions var7 = new Particle.DustOptions(var3, 2.0F);
            var1.getWorld().spawnParticle(Particle.DUST, var2.clone().add((double)0.0F, var5, (double)0.0F), 12, 0.3, 0.1, 0.3, (double)0.0F, var7, true);
            var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var2.clone().add((double)0.0F, var5, (double)0.0F), 3, 0.2, 0.1, 0.2, 0.03);
         }

         var1.playSound(var2, Sound.BLOCK_BEACON_ACTIVATE, 1.5F, 1.0F);
         var1.playSound(var2, Sound.ITEM_TOTEM_USE, 1.0F, 1.2F);
         var1.playSound(var2, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5F, 2.0F);
      }, 50L);
      this.a.getServer().getScheduler().runTaskLater(this.a, () -> (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var1.isOnline() && this.a < 40) {
                  double var1x = (double)this.a / (double)40.0F * (double)4.0F * Math.PI;
                  double var3x = (double)2.0F;

                  for(int var5 = 0; var5 < 3; ++var5) {
                     double var6 = (double)var5 * (double)2.0F + (double)1.0F;
                     double var8 = Math.cos(var1x + (double)var5 * Math.PI / (double)1.5F) * var3x;
                     double var10 = Math.sin(var1x + (double)var5 * Math.PI / (double)1.5F) * var3x;
                     Location var12 = var2.clone().add(var8, var6, var10);
                     Particle.DustOptions var13 = new Particle.DustOptions(var3, 1.2F);
                     var1.getWorld().spawnParticle(Particle.DUST, var12, 5, 0.1, 0.1, 0.1, (double)0.0F, var13, true);
                     var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var12, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.01);
                  }

                  if (this.a % 5 == 0) {
                     var1.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var2.clone().add((double)0.0F, (double)2.0F, (double)0.0F), 5, (double)0.5F, (double)0.5F, (double)0.5F, 0.02);
                  }

                  ++this.a;
               } else {
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L), 60L);
   }

   private String a(GemType var1, int var2) {
      return GemType.buildOraxenId(var1, var2);
   }

   private Color a(GemType var1) {
      Color var10000;
      switch (var1) {
         case ASTRA -> var10000 = Color.fromRGB(106, 11, 184);
         case FIRE -> var10000 = Color.fromRGB(255, 85, 85);
         case FLUX -> var10000 = Color.fromRGB(85, 255, 255);
         case LIFE -> var10000 = Color.fromRGB(85, 255, 85);
         case PUFF -> var10000 = Color.fromRGB(255, 255, 255);
         case SPEED -> var10000 = Color.fromRGB(255, 255, 85);
         case STRENGTH -> var10000 = Color.fromRGB(170, 0, 0);
         case WEALTH -> var10000 = Color.fromRGB(255, 170, 0);
         default -> throw new IncompatibleClassChangeError();
      }

      return var10000;
   }
}


static public class PassiveManager {
   private final BlissGems a;

   public PassiveManager(BlissGems var1) {
      this.a = var1;
      this.a();
   }

   private void a() {
      int var1 = this.a.getConfigManager().getPassiveUpdateInterval();
      (new BukkitRunnable() {
         public void run() {
            for(Player var2 : PassiveManager.this.a.getServer().getOnlinePlayers()) {
               if (!var2.isDead() && PassiveManager.this.a.getGemManager().hasGemInOffhand(var2) && PassiveManager.this.a.getEnergyManager().arePassivesActive(var2)) {
                  PassiveManager.this.a(var2);
               }
            }

         }
      }).runTaskTimer(this.a, (long)var1, (long)var1);
   }

   private void a(Player var1) {
      if (this.a.getRegionManager() == null || !this.a.getRegionManager().a(var1)) {
         int var2 = this.a.getGemManager().getTierFromOffhand(var1);
         String var3 = this.a.getGemManager().getGemIdFromOffhand(var1);
         GemType var4 = this.a.getGemManager().getGemTypeFromOffhand(var1);
         if (var4 != null) {
            switch (var4) {
               case ASTRA -> this.b(var1);
               case FIRE -> this.c(var1);
               case FLUX -> this.d(var1);
               case LIFE -> this.e(var1);
               case PUFF -> this.f(var1);
               case SPEED -> this.g(var1);
               case STRENGTH -> this.h(var1);
               case WEALTH -> this.i(var1);
            }

            this.a(var1, var4, var2);
         } else {
            if (var3 != null) {
               GemRegistryImpl var5 = this.a.getGemRegistry();
               if (var5 != null) {
                  GemPassiveHandler var6 = var5.getPassiveHandler(var3);
                  if (var6 != null) {
                     var6.applyPassives(var1, var2);
                  }

                  this.a(var1, var3, var2);
               }
            }

         }
      }
   }

   private void a(Player var1, String var2, int var3) {
      String var4 = "passives.extra-effects." + var2 + ".t" + var3;
      List var5 = this.a.getConfig().getMapList(var4);
      if (var5 != null && !var5.isEmpty()) {
         int var6 = this.a.getConfigManager().getPassiveUpdateInterval() + 10;

         for(Map var8 : var5) {
            Object var9 = var8.get("type");
            if (var9 != null) {
               PotionEffectType var10 = PotionEffectType.getByName(var9.toString().toUpperCase());
               if (var10 == null) {
                  Logger var10000 = this.a.getLogger();
                  String var10001 = String.valueOf(var9);
                  var10000.warning("Unknown potion effect '" + var10001 + "' in " + var4);
               } else {
                  Object var11 = var8.get("level");
                  int var12 = var11 instanceof Number ? ((Number)var11).intValue() : 1;
                  int var13 = Math.max(0, var12 - 1);
                  var1.addPotionEffect(new PotionEffect(var10, var6, var13, true, false), true);
               }
            }
         }

      }
   }

   private void a(Player var1, GemType var2, int var3) {
      String var10000 = var2.name().toLowerCase();
      String var4 = "passives.extra-effects." + var10000 + ".t" + var3;
      List var5 = this.a.getConfig().getMapList(var4);
      if (var5 != null && !var5.isEmpty()) {
         int var6 = this.a.getConfigManager().getPassiveUpdateInterval() + 10;

         for(Map var8 : var5) {
            Object var9 = var8.get("type");
            if (var9 != null) {
               PotionEffectType var10 = PotionEffectType.getByName(var9.toString().toUpperCase());
               if (var10 == null) {
                  Logger var14 = this.a.getLogger();
                  String var10001 = String.valueOf(var9);
                  var14.warning("Unknown potion effect '" + var10001 + "' in " + var4);
               } else {
                  Object var11 = var8.get("level");
                  int var12 = var11 instanceof Number ? ((Number)var11).intValue() : 1;
                  int var13 = Math.max(0, var12 - 1);
                  var1.addPotionEffect(new PotionEffect(var10, var6, var13, true, false), true);
               }
            }
         }

      }
   }

   private void b(Player var1) {
   }

   private void c(Player var1) {
      int var2 = this.a.getGemManager().getTierFromOffhand(var1);
      if (this.a.getConfigManager().isFireResistanceEnabled(var2)) {
         int var3 = this.a.getConfigManager().getPassiveUpdateInterval();
         var1.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, var3 + 10, 0, true, false), true);
      }

   }

   private void d(Player var1) {
      var1.removePotionEffect(PotionEffectType.WEAKNESS);
      var1.removePotionEffect(PotionEffectType.SLOWNESS);
      var1.removePotionEffect(PotionEffectType.HUNGER);
      if (var1.isSprinting()) {
         this.a.getFlowStateManager().registerAction(var1, FlowStateManager.ActionType.SPRINT);
      }

   }

   private void e(Player var1) {
      if (!var1.isDead()) {
         var1.removePotionEffect(PotionEffectType.WITHER);
         int var2 = this.a.getGemManager().getTierFromOffhand(var1);
         double var3 = this.a.getConfigManager().getLifeHealAmount(var2);
         double var5 = var1.getHealth();
         double var7 = var1.getMaxHealth();
         if (var5 < var7) {
            var1.setHealth(Math.min(var7, var5 + var3));
         }

      }
   }

   private void f(Player var1) {
   }

   private void g(Player var1) {
      int var2 = this.a.getGemManager().getTierFromOffhand(var1);
      int var3 = this.a.getConfigManager().getSpeedLevel(var2);
      int var4 = this.a.getConfigManager().getPassiveUpdateInterval();
      int var5 = var4 + 10;
      var1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, var5, var3, true, false), true);
      var1.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, var5, 0, true, false), true);
   }

   private void h(Player var1) {
      int var2 = this.a.getGemManager().getTierFromOffhand(var1);
      int var3 = this.a.getConfigManager().getStrengthLevel(var2);
      int var4 = this.a.getConfigManager().getPassiveUpdateInterval();
      int var5 = var4 + 10;
      PotionEffect var6 = var1.getPotionEffect(PotionEffectType.STRENGTH);
      if (var6 == null || var6.getAmplifier() <= var3 && (var6.getAmplifier() != var3 || var6.getDuration() <= var5 + 5)) {
         var1.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, var5, var3, true, false), true);
      }
   }

   private void i(Player var1) {
      int var2 = this.a.getGemManager().getTierFromOffhand(var1);
      int var3 = this.a.getConfigManager().getLuckLevel(var2);
      int var4 = this.a.getConfigManager().getPassiveUpdateInterval();
      int var5 = var4 + 10;
      var1.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, var5, var3, true, false), true);
      var1.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, var5, 0, true, false), true);
   }

   public void registerBuiltInHandlers(GemRegistry var1) {
      var1.registerPassives("astra", (var1x, var2) -> this.b(var1x));
      var1.registerPassives("fire", (var1x, var2) -> this.c(var1x));
      var1.registerPassives("flux", (var1x, var2) -> this.d(var1x));
      var1.registerPassives("life", (var1x, var2) -> this.e(var1x));
      var1.registerPassives("puff", (var1x, var2) -> this.f(var1x));
      var1.registerPassives("speed", (var1x, var2) -> this.g(var1x));
      var1.registerPassives("strength", (var1x, var2) -> this.h(var1x));
      var1.registerPassives("wealth", (var1x, var2) -> this.i(var1x));
   }
}


static public class PluginMessagingManager implements PluginMessageListener {
   private final BlissGems a;
   public static final String ABILITY_CHANNEL = "blissgems:ability";
   public static final String GEM_DATA_CHANNEL = "blissgems:gemdata";

   public PluginMessagingManager(BlissGems var1) {
      this.a = var1;
      var1.getServer().getMessenger().registerIncomingPluginChannel(var1, "blissgems:ability", this);
      var1.getServer().getMessenger().registerOutgoingPluginChannel(var1, "blissgems:gemdata");
   }

   public void onPluginMessageReceived(String var1, Player var2, byte[] var3) {
      if (var1.equals("blissgems:ability")) {
         ByteArrayDataInput var4 = ByteStreams.newDataInput(var3);
         String var5 = var4.readUTF();
         if (!this.a.getGemManager().hasActiveGem(var2)) {
            var2.sendMessage("§c§lYou don't have a gem!");
         } else {
            int var6 = this.a.getEnergyManager().getEnergy(var2);
            if (var6 <= 0) {
               String var11 = this.a.getConfigManager().getFormattedMessage("no-energy");
               if (var11 != null && !var11.isEmpty()) {
                  var2.sendMessage(var11);
               }

            } else {
               GemType var7 = this.a.getGemManager().getGemType(var2);
               int var8 = this.a.getGemManager().getGemTier(var2);
               if (var7 != null) {
                  switch (var5) {
                     case "main":
                        this.a(var2, var7, var8);
                        break;
                     case "secondary":
                        if (var8 < 2) {
                           var2.sendMessage("§c§lSecondary abilities require Tier 2 gem!");
                           return;
                        }

                        this.b(var2, var7, var8);
                        break;
                     case "tertiary":
                        if (var8 < 2) {
                           var2.sendMessage("§c§lTertiary abilities require Tier 2 gem!");
                           return;
                        }

                        this.c(var2, var7, var8);
                  }

               }
            }
         }
      }
   }

   private void a(Player var1, GemType var2, int var3) {
      switch (var2) {
         case ASTRA -> this.a.getAstraAbilities().a(var1, var3);
         case FIRE -> this.a.getFireAbilities().a(var1, var3);
         case FLUX -> this.a.getFluxAbilities().a(var1, var3);
         case LIFE -> this.a.getLifeAbilities().a(var1, var3);
         case PUFF -> this.a.getPuffAbilities().a(var1, var3);
         case SPEED -> this.a.getSpeedAbilities().a(var1, var3);
         case STRENGTH -> this.a.getStrengthAbilities().a(var1, var3);
         case WEALTH -> this.a.getWealthAbilities().a(var1, var3);
      }

   }

   private void b(Player var1, GemType var2, int var3) {
      switch (var2) {
         case ASTRA -> this.a.getAstraAbilities().a(var1, var3);
         case FIRE -> this.a.getFireAbilities().a(var1, var3);
         case FLUX -> this.a.getFluxAbilities().a(var1, var3);
         case LIFE -> this.a.getLifeAbilities().a(var1, var3);
         case PUFF -> this.a.getPuffAbilities().a(var1, var3);
         case SPEED -> this.a.getSpeedAbilities().a(var1, var3);
         case STRENGTH -> this.a.getStrengthAbilities().a(var1, var3);
         case WEALTH -> this.a.getWealthAbilities().a(var1, var3);
      }

   }

   private void c(Player var1, GemType var2, int var3) {
      switch (var2) {
         case WEALTH -> this.a.getWealthAbilities().a(var1, var3);
         default -> var1.sendMessage("§c§lThis gem doesn't have a tertiary ability!");
      }

   }

   public void sendGemData(Player var1) {
      if (var1.isOnline()) {
         GemType var2 = this.a.getGemManager().getGemType(var1);
         int var3 = this.a.getGemManager().getGemTier(var1);
         int var4 = this.a.getEnergyManager().getEnergy(var1);
         ByteArrayDataOutput var5 = ByteStreams.newDataOutput();
         var5.writeUTF(var2 != null ? var2.name() : "NONE");
         var5.writeInt(var3);
         var5.writeInt(var4);
         var1.sendPluginMessage(this.a, "blissgems:gemdata", var5.toByteArray());
      }
   }

   public void shutdown() {
      this.a.getServer().getMessenger().unregisterIncomingPluginChannel(this.a, "blissgems:ability");
      this.a.getServer().getMessenger().unregisterOutgoingPluginChannel(this.a, "blissgems:gemdata");
   }
}


static public class RecipeManager {
   private final BlissGems a;
   private final List<NamespacedKey> b = new ArrayList();

   public RecipeManager(BlissGems var1) {
      this.a = var1;
   }

   public void registerRecipes() {
      if (!this.a.getConfig().getBoolean("crafting.enabled", true)) {
         this.a.getLogger().info("Crafting recipes are disabled in config");
      } else {
         this.a();
         this.b();
         this.c();
         this.d();
         this.e();
         this.a.getLogger().info("Registered " + this.b.size() + " custom crafting recipes");
      }
   }

   private void a() {
      ItemStack var1 = CustomItemManager.getItemById("gem_fragment");
      if (var1 == null) {
         this.a.getLogger().warning("Could not create gem_fragment item - recipe not registered");
      } else {
         var1.setAmount(4);
         NamespacedKey var2 = new NamespacedKey(this.a, "gem_fragment");
         ShapedRecipe var3 = new ShapedRecipe(var2, var1);
         var3.shape(new String[]{"DAD", "EIE", "DAD"});
         var3.setIngredient('D', Material.DIAMOND);
         var3.setIngredient('A', Material.AMETHYST_CLUSTER);
         var3.setIngredient('E', Material.EMERALD);
         var3.setIngredient('I', Material.IRON_BLOCK);
         this.a.getServer().addRecipe(var3);
         this.b.add(var2);
      }
   }

   private void b() {
      ItemStack var1 = CustomItemManager.getItemById("gem_trader");
      if (var1 == null) {
         this.a.getLogger().warning("Could not create gem_trader item - recipe not registered");
      } else {
         NamespacedKey var2 = new NamespacedKey(this.a, "gem_trader");
         ShapedRecipe var3 = new ShapedRecipe(var2, var1);
         var3.shape(new String[]{"BDB", "DSD", "BDB"});
         var3.setIngredient('B', Material.DIAMOND_BLOCK);
         var3.setIngredient('D', Material.DRAGON_BREATH);
         var3.setIngredient('S', Material.SCULK_CATALYST);
         this.a.getServer().addRecipe(var3);
         this.b.add(var2);
      }
   }

   private void c() {
      ItemStack var1 = CustomItemManager.getItemById("repair_kit");
      if (var1 == null) {
         this.a.getLogger().warning("Could not create repair_kit item - recipe not registered");
      } else {
         NamespacedKey var2 = new NamespacedKey(this.a, "repair_kit");
         ShapedRecipe var3 = new ShapedRecipe(var2, var1);
         var3.shape(new String[]{"FAF", "NTN", "FAF"});
         var3.setIngredient('F', Material.PRISMARINE_SHARD);
         var3.setIngredient('A', Material.ANVIL);
         var3.setIngredient('N', Material.NETHERITE_INGOT);
         var3.setIngredient('T', Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
         this.a.getServer().addRecipe(var3);
         this.b.add(var2);
      }
   }

   private void d() {
      ItemStack var1 = CustomItemManager.getItemById("revive_beacon");
      if (var1 == null) {
         this.a.getLogger().warning("Could not create revive_beacon item - recipe not registered");
      } else {
         NamespacedKey var2 = new NamespacedKey(this.a, "revive_beacon");
         ShapedRecipe var3 = new ShapedRecipe(var2, var1);
         var3.shape(new String[]{"ETE", "TBT", "ETE"});
         var3.setIngredient('E', Material.ECHO_SHARD);
         var3.setIngredient('T', Material.TOTEM_OF_UNDYING);
         var3.setIngredient('B', Material.BEACON);
         this.a.getServer().addRecipe(var3);
         this.b.add(var2);
      }
   }

   private void e() {
      ItemStack var1 = CustomItemManager.getItemById("gem_upgrader");
      if (var1 == null) {
         this.a.getLogger().warning("Could not create gem_upgrader - recipe not registered");
      } else {
         NamespacedKey var2 = new NamespacedKey(this.a, "gem_upgrader");
         ShapedRecipe var3 = new ShapedRecipe(var2, var1);
         var3.shape(new String[]{"BBB", "BSB", "BBB"});
         var3.setIngredient('B', Material.DIAMOND_BLOCK);
         var3.setIngredient('S', Material.NETHER_STAR);
         this.a.getServer().addRecipe(var3);
         this.b.add(var2);
      }
   }

   public void unregisterRecipes() {
      for(NamespacedKey var2 : this.b) {
         this.a.getServer().removeRecipe(var2);
      }

      this.b.clear();
   }
}


static public class RepairKitManager {
   private final BlissGems a;
   private final Map<Location, a> b = new HashMap();

   public RepairKitManager(BlissGems var1) {
      this.a = var1;
   }

   public boolean createPedestal(final Location var1) {
      final Block var2 = var1.getBlock();
      if (var2.getType() != Material.BEACON) {
         return false;
      } else if (this.b.containsKey(var1)) {
         return false;
      } else {
         final int var3 = this.a.getConfig().getInt("repair-kit.energy-per-second", 1);
         int var4 = this.a.getConfig().getInt("repair-kit.max-total-energy", 10);
         final double var5 = this.a.getConfig().getDouble("repair-kit.heal-radius", (double)10.0F);
         final int var7 = this.a.getConfig().getInt("repair-kit.update-interval", 20);
         final boolean var8 = this.a.getConfig().getBoolean("repair-kit.prioritize-lowest", true);
         final a var9 = new a(var1, var4);
         this.b.put(var1, var9);
         var1.getWorld().playSound(var1, Sound.BLOCK_BEACON_ACTIVATE, 1.0F, 1.0F);
         var1.getWorld().spawnParticle(Particle.END_ROD, var1.clone().add((double)0.5F, (double)1.0F, (double)0.5F), 50, (double)0.5F, (double)1.0F, (double)0.5F, 0.1);
         BukkitTask var10 = (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var2.getType() == Material.BEACON && !var9.b()) {
                  ++this.a;
                  if (this.a % var7 == 0) {
                     List var1x = RepairKitManager.this.a(var1, var5);
                     if (var1x.isEmpty()) {
                        if (this.a >= 1200) {
                           RepairKitManager.this.removePedestal(var1);
                           this.cancel();
                           return;
                        }
                     } else {
                        if (var8) {
                           var1x.sort(Comparator.comparingInt((var1xx) -> RepairKitManager.this.a.getEnergyManager().getEnergy(var1xx)));
                        }

                        int var2x = 0;

                        for(Player var4 : var1x) {
                           int var5x = RepairKitManager.this.a.getEnergyManager().getEnergy(var4);
                           int var6 = RepairKitManager.this.a.getConfigManager().getMaxEnergy();
                           if (var5x < var6) {
                              int var7x = Math.min(var3, var6 - var5x);
                              var7x = Math.min(var7x, var9.a());
                              if (var7x > 0) {
                                 RepairKitManager.this.a.getEnergyManager().addEnergy(var4, var7x);
                                 var9.a(var7x);
                                 var2x += var7x;
                                 if (RepairKitManager.this.a.getAchievementManager() != null) {
                                    RepairKitManager.this.a.getAchievementManager().addProgress(var4, Achievement.GOOD_AS_NEW, var7x);
                                 }

                                 Particle var8x = Particle.valueOf(RepairKitManager.this.a.getConfig().getString("repair-kit.particle", "HAPPY_VILLAGER"));
                                 var4.spawnParticle(var8x, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 10, 0.3, (double)0.5F, 0.3, (double)0.0F);
                                 var4.playSound(var4.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.5F);
                                 String var9x = RepairKitManager.this.a.getConfigManager().getFormattedMessage("gem-energy-restored", "amount", String.valueOf(var7x));
                                 if (var9x != null && !var9x.isEmpty()) {
                                    var4.sendMessage(var9x);
                                 }
                              }

                              if (var9.a() <= 0) {
                                 break;
                              }
                           }
                        }

                        if (var9.a() <= 0) {
                           RepairKitManager.this.removePedestal(var1);

                           for(Player var14 : RepairKitManager.this.b(var1, var5)) {
                              var14.sendMessage("§d§oThe Repair Kit has been exhausted!");
                           }

                           this.cancel();
                           return;
                        }
                     }

                     if (RepairKitManager.this.a.getConfig().getBoolean("repair-kit.play-effects", true)) {
                        Particle var11 = Particle.valueOf(RepairKitManager.this.a.getConfig().getString("repair-kit.particle", "HAPPY_VILLAGER"));
                        int var13 = RepairKitManager.this.a.getConfig().getInt("repair-kit.particle-count", 5);
                        var1.getWorld().spawnParticle(var11, var1.clone().add((double)0.5F, (double)1.0F, (double)0.5F), var13, 0.3, (double)0.5F, 0.3, (double)0.0F);
                        if (this.a % (var7 * 3) == 0) {
                           for(int var15 = 0; var15 < 16; ++var15) {
                              double var16 = (double)var15 / (double)16.0F * (double)2.0F * Math.PI;
                              double var18 = Math.cos(var16) * var5;
                              double var19 = Math.sin(var16) * var5;
                              var1.getWorld().spawnParticle(Particle.END_ROD, var1.clone().add(var18, (double)0.5F, var19), 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
                           }
                        }
                     }
                  }

               } else {
                  RepairKitManager.this.removePedestal(var1);
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L);
         var9.a(var10);
         return true;
      }
   }

   public void removePedestal(Location var1) {
      a var2 = (a)this.b.remove(var1);
      if (var2 != null) {
         if (var2.c() != null) {
            var2.c().cancel();
         }

         var1.getWorld().playSound(var1, Sound.BLOCK_BEACON_DEACTIVATE, 1.0F, 1.0F);
         var1.getWorld().spawnParticle(Particle.SMOKE, var1.clone().add((double)0.5F, (double)1.0F, (double)0.5F), 30, (double)0.5F, (double)1.0F, (double)0.5F, 0.05);
      }

   }

   private List<Player> a(Location var1, double var2) {
      ArrayList var4 = new ArrayList();
      int var5 = this.a.getConfigManager().getMaxEnergy();

      for(Player var7 : this.b(var1, var2)) {
         if (this.a.getEnergyManager().getEnergy(var7) < var5) {
            var4.add(var7);
         }
      }

      return var4;
   }

   private List<Player> b(Location var1, double var2) {
      ArrayList var4 = new ArrayList();

      for(Entity var6 : var1.getWorld().getNearbyEntities(var1, var2, var2, var2)) {
         if (var6 instanceof Player) {
            var4.add((Player)var6);
         }
      }

      return var4;
   }

   public boolean isPedestal(Location var1) {
      return this.b.containsKey(var1);
   }

   public void cleanup() {
      for(Location var2 : new ArrayList(this.b.keySet())) {
         this.removePedestal(var2);
      }

   }

   private static class a {
      private final Location a;
      private int b;
      private BukkitTask c;

      public a(Location var1, int var2) {
         this.a = var1;
         this.b = var2;
      }

      public int a() {
         return this.b;
      }

      public void a(int var1) {
         this.b -= var1;
      }

      public boolean b() {
         return this.b <= 0;
      }

      public void a(BukkitTask var1) {
         this.c = var1;
      }

      public BukkitTask c() {
         return this.c;
      }
   }
}


static public class ReviveBeaconManager {
   private final BlissGems a;
   private final Map<UUID, a> b = new HashMap();

   public ReviveBeaconManager(BlissGems var1) {
      this.a = var1;
   }

   public void activateBeacon(Player var1, Location var2, int var3, double var4) {
      UUID var6 = var1.getUniqueId();
      if (this.b.containsKey(var6)) {
         ((a)this.b.get(var6)).d();
      }

      a var7 = new a(this.a, var1, var2, var3, var4);
      this.b.put(var6, var7);
      var7.a();
   }

   public boolean canRevive(Player var1) {
      UUID var2 = var1.getUniqueId();
      if (!this.b.containsKey(var2)) {
         return false;
      } else {
         a var3 = (a)this.b.get(var2);
         return var3.b() && var3.a(var1.getLocation());
      }
   }

   public Location getReviveLocation(Player var1) {
      UUID var2 = var1.getUniqueId();
      return !this.b.containsKey(var2) ? null : ((a)this.b.get(var2)).c();
   }

   public void removeBeacon(Player var1) {
      UUID var2 = var1.getUniqueId();
      if (this.b.containsKey(var2)) {
         ((a)this.b.get(var2)).d();
         this.b.remove(var2);
      }

   }

   public void cleanup() {
      for(a var2 : this.b.values()) {
         var2.d();
      }

      this.b.clear();
   }

   private static class a {
      private final BlissGems a;
      private final Player b;
      private final Location c;
      private final double d;
      private final long e;
      private int f = -1;
      private boolean g = true;

      public a(BlissGems var1, Player var2, Location var3, int var4, double var5) {
         this.a = var1;
         this.b = var2;
         this.c = var3.clone();
         this.d = var5;
         this.e = System.currentTimeMillis() + (long)var4 * 1000L;
      }

      public void a() {
         int var1 = (int)((this.e - System.currentTimeMillis()) / 50L);
         this.f = this.a.getServer().getScheduler().scheduleSyncDelayedTask(this.a, () -> {
            this.g = false;
            this.b.sendMessage("§c§lYour Revive Beacon has expired!");
         }, (long)var1);
         this.a.getServer().getScheduler().runTaskTimer(this.a, (var1x) -> {
            if (this.g && System.currentTimeMillis() < this.e) {
               if (this.c.getWorld() != null) {
                  this.c.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, this.c.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 10, (double)0.5F, (double)0.5F, (double)0.5F, 0.05);
               }

            } else {
               var1x.cancel();
            }
         }, 0L, 20L);
      }

      public boolean b() {
         return this.g && System.currentTimeMillis() < this.e;
      }

      public boolean a(Location var1) {
         if (this.c.getWorld() != null && var1.getWorld() != null) {
            if (!this.c.getWorld().equals(var1.getWorld())) {
               return false;
            } else {
               return this.c.distance(var1) <= this.d;
            }
         } else {
            return false;
         }
      }

      public Location c() {
         return this.c.clone();
      }

      public void d() {
         this.g = false;
         if (this.f != -1) {
            this.a.getServer().getScheduler().cancelTask(this.f);
         }

      }
   }
}


static public class SoulManager {
   private final BlissGems a;
   private final NamespacedKey b;
   private final Map<UUID, List<CapturedMob>> c = new HashMap();
   private static final int d = 2;
   private static final String e = "soul_absorb_";
   private int f = 0;
   private final Map<UUID, List<UUID>> g = new HashMap();

   public SoulManager(BlissGems var1) {
      this.a = var1;
      this.b = new NamespacedKey(var1, "captured_mob");
   }

   public void absorbSoul(Player var1, Entity var2) {
      boolean var3 = var2 instanceof Player;
      double var4;
      if (var3) {
         var4 = this.a.getConfig().getDouble("abilities.soul-absorption.player-kill-hearts", (double)10.0F);
      } else {
         var4 = this.a.getConfig().getDouble("abilities.soul-absorption.mob-kill-hearts", (double)4.0F);
      }

      int var6 = this.a.getConfig().getInt("abilities.soul-absorption.duration", 60);
      AttributeInstance var7 = var1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
      if (var7 != null) {
         UUID var8 = UUID.randomUUID();
         BlissGems var10002 = this.a;
         String var10003 = var8.toString();
         NamespacedKey var9 = new NamespacedKey(var10002, "soul-absorb-" + var10003.replace("-", ""));
         ++this.f;
         AttributeModifier var10 = new AttributeModifier(var8, var9.toString(), var4, Operation.ADD_NUMBER);
         var7.addModifier(var10);
         ((List)this.g.computeIfAbsent(var1.getUniqueId(), (var0) -> new ArrayList())).add(var8);
         this.a(var1.getUniqueId(), var8);
         this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
            if (var1.isOnline()) {
               var7.removeModifier(var10);
               double var5 = var7.getValue();
               if (var1.getHealth() > var5) {
                  var1.setHealth(var5);
               }
            }

            List var7x = (List)this.g.get(var1.getUniqueId());
            if (var7x != null) {
               var7x.remove(var8);
               if (var7x.isEmpty()) {
                  this.g.remove(var1.getUniqueId());
               }
            }

            this.b(var1.getUniqueId(), var8);
         }, (long)var6 * 20L);
         var1.getWorld().spawnParticle(Particle.SOUL, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 15, (double)0.5F, (double)0.5F, (double)0.5F, 0.05);
         var1.getWorld().spawnParticle(Particle.ENCHANTED_HIT, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 10, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F);
         var1.playSound(var1.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.5F);
         var1.playSound(var1.getLocation(), Sound.BLOCK_SOUL_SAND_BREAK, 1.0F, 1.5F);
         String var11 = var3 ? ((Player)var2).getName() : var2.getType().toString().toLowerCase().replace("_", " ");
         var1.sendMessage("§d§oAbsorbed soul from " + var11 + "! +" + var4 / (double)2.0F + " hearts for " + var6 + "s");
      }
   }

   public boolean captureMob(Player var1, LivingEntity var2) {
      if (var2 instanceof Player) {
         var1.sendMessage("§c§oCannot capture players!");
         return false;
      } else {
         List var3 = (List)this.c.computeIfAbsent(var1.getUniqueId(), (var0) -> new ArrayList());
         if (var3.size() >= 2) {
            var1.sendMessage("§c§oYour gem is full! (Max 2 souls)");
            return false;
         } else if (!this.a(var2)) {
            var1.sendMessage("§c§oThis entity cannot be captured!");
            return false;
         } else {
            CapturedMob var4 = new CapturedMob(var2.getType(), var2.getCustomName());
            var3.add(var4);
            var2.remove();
            Location var5 = var2.getLocation();
            var5.getWorld().spawnParticle(Particle.REVERSE_PORTAL, var5.add((double)0.0F, (double)1.0F, (double)0.0F), 50, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
            var5.getWorld().spawnParticle(Particle.WITCH, var5, 30, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F);
            var1.playSound(var1.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 2.0F);
            String var6 = var4.getDisplayName();
            var1.sendMessage("§d§lSoul Captured! §d§oCaptured " + var6 + " (" + var3.size() + "/2)");
            return true;
         }
      }
   }

   public void releaseAllSouls(Player var1) {
      List var2 = (List)this.c.get(var1.getUniqueId());
      if (var2 != null && !var2.isEmpty()) {
         Location var3 = var1.getLocation().add(var1.getLocation().getDirection().multiply(2));
         var3.setY(var1.getLocation().getY());
         Collections.reverse(var2);
         int var4 = 0;

         for(CapturedMob var6 : var2) {
            Entity var7 = var1.getWorld().spawnEntity(var3, var6.getType());
            if (var6.getCustomName() != null && var7 instanceof LivingEntity) {
               var7.setCustomName(var6.getCustomName());
               var7.setCustomNameVisible(true);
            }

            var3.getWorld().spawnParticle(Particle.PORTAL, var3.add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
            ++var4;
         }

         var2.clear();
         var1.playSound(var1.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 0.8F);
         var1.sendMessage("§d§lReleased " + var4 + " captured soul(s)!");
      } else {
         var1.sendMessage("§c§oNo souls captured!");
      }
   }

   public List<CapturedMob> getCapturedSouls(Player var1) {
      return (List)this.c.getOrDefault(var1.getUniqueId(), new ArrayList());
   }

   public int getCapturedCount(Player var1) {
      return this.getCapturedSouls(var1).size();
   }

   private boolean a(LivingEntity var1) {
      EntityType var2 = var1.getType();
      return var2 != EntityType.ENDER_DRAGON && var2 != EntityType.WITHER && var2 != EntityType.WARDEN && var2 != EntityType.PLAYER && var2 != EntityType.ARMOR_STAND && !(var1 instanceof NPC);
   }

   public void clearSouls(UUID var1) {
      this.c.remove(var1);
   }

   public void cleanup(Player var1) {
      AttributeInstance var2 = var1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
      if (var2 != null) {
         Set var3 = this.b(var1.getUniqueId());
         ArrayList var4 = new ArrayList();

         for(AttributeModifier var6 : var2.getModifiers()) {
            if (var6.getUniqueId() != null && var3.contains(var6.getUniqueId())) {
               var4.add(var6);
            } else if (this.a(var6)) {
               var4.add(var6);
            }
         }

         for(AttributeModifier var9 : var4) {
            var2.removeModifier(var9);
         }

         double var8 = var2.getValue();
         if (var1.getHealth() > var8) {
            var1.setHealth(var8);
         }

         this.g.remove(var1.getUniqueId());
         this.c(var1.getUniqueId());
         if (!var4.isEmpty()) {
            Logger var10000 = this.a.getLogger();
            int var10001 = var4.size();
            var10000.info("[SoulManager] Removed " + var10001 + " stale soul-absorb modifier(s) from " + var1.getName());
         }

      }
   }

   private boolean a(AttributeModifier var1) {
      String var2 = var1.getName();
      if (var2 != null) {
         String var3 = var2.toLowerCase();
         if (var3.contains("soul_absorb") || var3.contains("soul-absorb")) {
            return true;
         }

         if (var3.startsWith("blissgems:")) {
            return true;
         }
      }

      try {
         NamespacedKey var6 = var1.getKey();
         if (var6 != null) {
            if ("blissgems".equals(var6.getNamespace())) {
               return true;
            }

            String var4 = var6.getKey();
            if (var4 != null && (var4.contains("soul_absorb") || var4.contains("soul-absorb"))) {
               return true;
            }
         }
      } catch (Throwable var5) {
      }

      return false;
   }

   private File a(UUID var1) {
      File var2 = new File(this.a.getDataFolder(), "playerdata");
      if (!var2.exists()) {
         var2.mkdirs();
      }

      return new File(var2, String.valueOf(var1) + ".yml");
   }

   private void a(UUID var1, UUID var2) {
      File var3 = this.a(var1);
      YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
      List var5 = ((FileConfiguration)var4).getStringList("soul-modifier-uuids");
      var5.add(var2.toString());
      ((FileConfiguration)var4).set("soul-modifier-uuids", var5);

      try {
         ((FileConfiguration)var4).save(var3);
      } catch (IOException var7) {
      }

   }

   private void b(UUID var1, UUID var2) {
      File var3 = this.a(var1);
      if (var3.exists()) {
         YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
         List var5 = ((FileConfiguration)var4).getStringList("soul-modifier-uuids");
         var5.remove(var2.toString());
         ((FileConfiguration)var4).set("soul-modifier-uuids", var5.isEmpty() ? null : var5);

         try {
            ((FileConfiguration)var4).save(var3);
         } catch (IOException var7) {
         }

      }
   }

   private Set<UUID> b(UUID var1) {
      File var2 = this.a(var1);
      if (!var2.exists()) {
         return Collections.emptySet();
      } else {
         YamlConfiguration var3 = YamlConfiguration.loadConfiguration(var2);
         List var4 = ((FileConfiguration)var3).getStringList("soul-modifier-uuids");
         HashSet var5 = new HashSet();

         for(String var7 : var4) {
            try {
               var5.add(UUID.fromString(var7));
            } catch (IllegalArgumentException var9) {
            }
         }

         return var5;
      }
   }

   private void c(UUID var1) {
      File var2 = this.a(var1);
      if (var2.exists()) {
         YamlConfiguration var3 = YamlConfiguration.loadConfiguration(var2);
         if (((FileConfiguration)var3).contains("soul-modifier-uuids")) {
            ((FileConfiguration)var3).set("soul-modifier-uuids", (Object)null);

            try {
               ((FileConfiguration)var3).save(var2);
            } catch (IOException var5) {
            }
         }

      }
   }

   public static class CapturedMob {
      private final EntityType a;
      private final String b;

      public CapturedMob(EntityType var1, String var2) {
         this.a = var1;
         this.b = var2;
      }

      public EntityType getType() {
         return this.a;
      }

      public String getCustomName() {
         return this.b;
      }

      public String getDisplayName() {
         return this.b != null && !this.b.isEmpty() ? this.b : this.a.toString().toLowerCase().replace("_", " ");
      }
   }
}


static public class StatsManager {
   private final BlissGems a;
   private final Map<UUID, PlayerStats> b = new HashMap();
   private final File c;

   public StatsManager(BlissGems var1) {
      this.a = var1;
      this.c = new File(var1.getDataFolder(), "playerstats");
      if (!this.c.exists()) {
         this.c.mkdirs();
      }

      this.a();
   }

   public void recordKill(Player var1, Player var2) {
      PlayerStats var3 = (PlayerStats)this.b.computeIfAbsent(var1.getUniqueId(), (var0) -> new PlayerStats());
      ++var3.kills;
      this.a(var1.getUniqueId());
   }

   public void recordDeath(Player var1) {
      PlayerStats var2 = (PlayerStats)this.b.computeIfAbsent(var1.getUniqueId(), (var0) -> new PlayerStats());
      ++var2.deaths;
      this.a(var1.getUniqueId());
   }

   public void recordGemSwitch(Player var1) {
      PlayerStats var2 = (PlayerStats)this.b.computeIfAbsent(var1.getUniqueId(), (var0) -> new PlayerStats());
      long var3 = System.currentTimeMillis();
      var2.timePlayedMs += var3 - var2.lastGemSwitchTime;
      var2.lastGemSwitchTime = var3;
      this.a(var1.getUniqueId());
   }

   public int getKills(Player var1) {
      return ((PlayerStats)this.b.getOrDefault(var1.getUniqueId(), new PlayerStats())).kills;
   }

   public int getDeaths(Player var1) {
      return ((PlayerStats)this.b.getOrDefault(var1.getUniqueId(), new PlayerStats())).deaths;
   }

   public long getTimePlayed(Player var1) {
      PlayerStats var2 = (PlayerStats)this.b.getOrDefault(var1.getUniqueId(), new PlayerStats());
      long var3 = var2.timePlayedMs;
      if (var2.lastGemSwitchTime > 0L) {
         var3 += System.currentTimeMillis() - var2.lastGemSwitchTime;
      }

      return var3;
   }

   public List<Map.Entry<UUID, Integer>> getTopKillers(int var1) {
      return this.b.entrySet().stream().sorted((var0, var1x) -> Integer.compare(((PlayerStats)var1x.getValue()).kills, ((PlayerStats)var0.getValue()).kills)).limit((long)var1).map((var0) -> new AbstractMap.SimpleEntry((UUID)var0.getKey(), ((PlayerStats)var0.getValue()).kills)).toList();
   }

   public Map<String, Integer> getGemUsageStats() {
      HashMap var1 = new HashMap();

      for(Player var3 : Bukkit.getOnlinePlayers()) {
         String var4 = this.a.getGemManager().getGemType(var3) != null ? this.a.getGemManager().getGemType(var3).getDisplayName() : "None";
         var1.put(var4, (Integer)var1.getOrDefault(var4, 0) + 1);
      }

      return var1;
   }

   private void a(UUID var1) {
   }

   private void a() {
   }

   public void cleanup(UUID var1) {
      this.recordGemSwitch(Bukkit.getPlayer(var1));
   }

   public static class PlayerStats {
      public int kills = 0;
      public int deaths = 0;
      public long timePlayedMs = 0L;
      public long lastGemSwitchTime = System.currentTimeMillis();
   }
}


static public class TrustedPlayersManager {
   private final BlissGems a;
   private final Map<UUID, Set<UUID>> b = new HashMap();
   private final File c;

   public TrustedPlayersManager(BlissGems var1) {
      this.a = var1;
      this.c = new File(var1.getDataFolder(), "playerdata");
      if (!this.c.exists()) {
         this.c.mkdirs();
      }

   }

   public boolean isTrusted(Player var1, Player var2) {
      if (var1.getUniqueId().equals(var2.getUniqueId())) {
         return true;
      } else {
         UUID var3 = var1.getUniqueId();
         if (!this.b.containsKey(var3)) {
            this.a(var1);
         }

         Set var4 = (Set)this.b.get(var3);
         return var4 != null && var4.contains(var2.getUniqueId());
      }
   }

   public void addTrustedPlayer(Player var1, Player var2) {
      UUID var3 = var1.getUniqueId();
      if (!this.b.containsKey(var3)) {
         this.a(var1);
      }

      Object var4 = (Set)this.b.get(var3);
      if (var4 == null) {
         var4 = new HashSet();
         this.b.put(var3, var4);
      }

      ((Set)var4).add(var2.getUniqueId());
      this.b(var1);
   }

   public boolean removeTrustedPlayer(Player var1, Player var2) {
      UUID var3 = var1.getUniqueId();
      if (!this.b.containsKey(var3)) {
         this.a(var1);
      }

      Set var4 = (Set)this.b.get(var3);
      if (var4 == null) {
         return false;
      } else {
         boolean var5 = var4.remove(var2.getUniqueId());
         if (var5) {
            this.b(var1);
         }

         return var5;
      }
   }

   public Set<UUID> getTrustedPlayers(Player var1) {
      UUID var2 = var1.getUniqueId();
      if (!this.b.containsKey(var2)) {
         this.a(var1);
      }

      Set var3 = (Set)this.b.get(var2);
      return var3 != null ? new HashSet(var3) : new HashSet();
   }

   private void a(Player var1) {
      UUID var2 = var1.getUniqueId();
      File var3 = this.c(var1);
      HashSet var4 = new HashSet();
      if (var3.exists()) {
         YamlConfiguration var5 = YamlConfiguration.loadConfiguration(var3);

         for(String var8 : ((FileConfiguration)var5).getStringList("trusted-players")) {
            try {
               var4.add(UUID.fromString(var8));
            } catch (IllegalArgumentException var10) {
               Logger var10000 = this.a.getLogger();
               String var10001 = var1.getName();
               var10000.warning("Invalid UUID in trusted players for " + var10001 + ": " + var8);
            }
         }
      }

      this.b.put(var2, var4);
   }

   private void b(Player var1) {
      UUID var2 = var1.getUniqueId();
      Object var3 = (Set)this.b.get(var2);
      if (var3 == null) {
         var3 = new HashSet();
      }

      File var4 = this.c(var1);
      YamlConfiguration var5 = YamlConfiguration.loadConfiguration(var4);
      List var6 = (List)((Set)var3).stream().map(UUID::toString).collect(Collectors.toList());
      ((FileConfiguration)var5).set("trusted-players", var6);

      try {
         ((FileConfiguration)var5).save(var4);
      } catch (IOException var8) {
         Logger var10000 = this.a.getLogger();
         String var10001 = var1.getName();
         var10000.warning("Failed to save trusted players for " + var10001 + ": " + var8.getMessage());
      }

   }

   private File c(Player var1) {
      return new File(this.c, String.valueOf(var1.getUniqueId()) + ".yml");
   }

   public void clearCache(Player var1) {
      this.b.remove(var1.getUniqueId());
   }
}
    }

    static final class P_dev_xoperr_blissgems_listeners {


static public class AutoEnchantListener implements Listener {
   private final BlissGems plugin;
   private static final String PDC_PREFIX = "ae_orig_";

   public AutoEnchantListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler
   public void onPlayerItemHeld(final PlayerItemHeldEvent var1) {
      final Player var2 = var1.getPlayer();
      ItemStack var3 = var2.getInventory().getItem(var1.getPreviousSlot());
      if (var3 != null && !var3.getType().isAir()) {
         this.stripAutoEnchants(var3);
      }

      (new BukkitRunnable() {
         public void run() {
            if (var2.isOnline()) {
               AutoEnchantListener.this.applyAutoEnchants(var2, var1.getNewSlot());
            }
         }
      }).runTaskLater(this.plugin, 1L);
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent var1) {
      final Player var2 = var1.getPlayer();
      (new BukkitRunnable() {
         public void run() {
            if (var2.isOnline()) {
               AutoEnchantListener.this.stripAllAutoEnchants(var2);
               int var1 = var2.getInventory().getHeldItemSlot();
               AutoEnchantListener.this.applyAutoEnchants(var2, var1);
            }
         }
      }).runTaskLater(this.plugin, 5L);
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent var1) {
      this.stripAllAutoEnchants(var1.getPlayer());
      this.stripAllAmplifyEnchants(var1.getPlayer());
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent var1) {
      if (var1.getWhoClicked() instanceof Player) {
         this.scheduleRefresh((Player)var1.getWhoClicked());
      }
   }

   @EventHandler
   public void onInventoryDrag(InventoryDragEvent var1) {
      if (var1.getWhoClicked() instanceof Player) {
         this.scheduleRefresh((Player)var1.getWhoClicked());
      }
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerDropItem(PlayerDropItemEvent var1) {
      if (!var1.isCancelled()) {
         ItemStack var2 = var1.getItemDrop().getItemStack();
         boolean var3 = this.stripAutoEnchants(var2);
         var3 |= h.a(var2);
         if (var3) {
            var1.getItemDrop().setItemStack(var2);
         }

         this.scheduleRefresh(var1.getPlayer());
      }
   }

   @EventHandler
   public void onPlayerSwapHand(PlayerSwapHandItemsEvent var1) {
      this.scheduleRefresh(var1.getPlayer());
   }

   private void scheduleRefresh(final Player var1) {
      (new BukkitRunnable() {
         public void run() {
            if (var1.isOnline()) {
               AutoEnchantListener.this.refreshAutoEnchants(var1);
            }
         }
      }).runTaskLater(this.plugin, 1L);
   }

   private void refreshAutoEnchants(Player var1) {
      int var2 = var1.getInventory().getHeldItemSlot();

      for(int var3 = 0; var3 < var1.getInventory().getSize(); ++var3) {
         if (var3 != var2) {
            ItemStack var4 = var1.getInventory().getItem(var3);
            if (var4 != null && !var4.getType().isAir()) {
               this.stripAutoEnchants(var4);
            }
         }
      }

      ItemStack var5 = var1.getInventory().getItemInOffHand();
      if (!var5.getType().isAir()) {
         this.stripAutoEnchants(var5);
      }

      this.applyAutoEnchants(var1, var2);
   }

   public void applyAutoEnchants(Player var1, int var2) {
      if (this.plugin.getConfig().getBoolean("auto-enchant.enabled", true)) {
         GemManager.ActiveGem var3 = this.plugin.getGemManager().getActiveGem(var1);
         if (var3 != null) {
            if (var3.getTier() >= 2 || var3.getType() == GemType.STRENGTH) {
               if (this.plugin.getEnergyManager().arePassivesActive(var1)) {
                  ItemStack var4 = var1.getInventory().getItem(var2);
                  if (var4 != null && var4.getType() != Material.AIR) {
                     GemType var5 = var3.getType();
                     int var6 = var3.getTier();
                     Map var7 = this.getEnchantsForGem(var5, var4, var6);
                     if (!var7.isEmpty()) {
                        ItemMeta var8 = var4.getItemMeta();
                        if (var8 != null) {
                           PersistentDataContainer var9 = var8.getPersistentDataContainer();
                           boolean var10 = false;

                           for(Map.Entry var12 : var7.entrySet()) {
                              Enchantment var13 = (Enchantment)var12.getKey();
                              int var14 = (Integer)var12.getValue();
                              int var15 = var8.getEnchantLevel(var13);
                              NamespacedKey var16 = new NamespacedKey(this.plugin, "ae_orig_" + var13.getKey().getKey());
                              if (!var9.has(var16, PersistentDataType.INTEGER) && var15 < var14) {
                                 var9.set(var16, PersistentDataType.INTEGER, var15);
                                 var8.addEnchant(var13, var14, true);
                                 var10 = true;
                              }
                           }

                           if (var10) {
                              var4.setItemMeta(var8);
                           }

                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean stripAutoEnchants(ItemStack var1) {
      if (var1 != null && !var1.getType().isAir() && var1.hasItemMeta()) {
         ItemMeta var2 = var1.getItemMeta();
         if (var2 == null) {
            return false;
         } else {
            PersistentDataContainer var3 = var2.getPersistentDataContainer();
            boolean var4 = false;

            for(Enchantment var8 : Enchantment.values()) {
               NamespacedKey var9 = new NamespacedKey(this.plugin, "ae_orig_" + var8.getKey().getKey());
               if (var3.has(var9, PersistentDataType.INTEGER)) {
                  int var10 = (Integer)var3.get(var9, PersistentDataType.INTEGER);
                  if (var10 == 0) {
                     var2.removeEnchant(var8);
                  } else {
                     var2.addEnchant(var8, var10, true);
                  }

                  var3.remove(var9);
                  var4 = true;
               }
            }

            if (var4) {
               var1.setItemMeta(var2);
            }

            return var4;
         }
      } else {
         return false;
      }
   }

   public void stripAllAutoEnchants(Player var1) {
      for(int var2 = 0; var2 < var1.getInventory().getSize(); ++var2) {
         ItemStack var3 = var1.getInventory().getItem(var2);
         if (var3 != null && !var3.getType().isAir()) {
            this.stripAutoEnchants(var3);
         }
      }

      ItemStack var4 = var1.getInventory().getItemInOffHand();
      if (!var4.getType().isAir()) {
         this.stripAutoEnchants(var4);
      }

   }

   private void stripAllAmplifyEnchants(Player var1) {
      for(int var2 = 0; var2 < var1.getInventory().getSize(); ++var2) {
         ItemStack var3 = var1.getInventory().getItem(var2);
         if (var3 != null && !var3.getType().isAir()) {
            h.a(var3);
         }
      }

      ItemStack[] var5 = var1.getInventory().getArmorContents();
      boolean var6 = false;

      for(int var4 = 0; var4 < var5.length; ++var4) {
         if (var5[var4] != null && !var5[var4].getType().isAir() && h.a(var5[var4])) {
            var6 = true;
         }
      }

      if (var6) {
         var1.getInventory().setArmorContents(var5);
      }

      ItemStack var7 = var1.getInventory().getItemInOffHand();
      if (!var7.getType().isAir()) {
         h.a(var7);
      }

   }

   public boolean hasAutoEnchants(ItemStack var1) {
      if (var1 != null && var1.hasItemMeta()) {
         PersistentDataContainer var2 = var1.getItemMeta().getPersistentDataContainer();

         for(Enchantment var6 : Enchantment.values()) {
            NamespacedKey var7 = new NamespacedKey(this.plugin, "ae_orig_" + var6.getKey().getKey());
            if (var2.has(var7, PersistentDataType.INTEGER)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private Map<Enchantment, Integer> getEnchantsForGem(GemType var1, ItemStack var2, int var3) {
      HashMap var4 = new HashMap();
      Material var5 = var2.getType();
      switch (var1) {
         case SPEED:
            if (this.plugin.getConfig().getBoolean("auto-enchant.speed.efficiency", true) && this.isTool(var5)) {
               var4.put(Enchantment.EFFICIENCY, 5);
            }
            break;
         case WEALTH:
            if (this.plugin.getConfig().getBoolean("auto-enchant.wealth.fortune", true) && (this.isPickaxe(var5) || this.isShovel(var5) || this.isAxe(var5))) {
               var4.put(Enchantment.FORTUNE, 3);
            }

            if (this.plugin.getConfig().getBoolean("auto-enchant.wealth.looting", true) && this.isSword(var5)) {
               var4.put(Enchantment.LOOTING, 3);
            }

            if (this.plugin.getConfig().getBoolean("auto-enchant.wealth.mending", true) && (this.isTool(var5) || this.isWeapon(var5) || this.isArmor(var5))) {
               var4.put(Enchantment.MENDING, 1);
            }
            break;
         case FIRE:
            if (this.plugin.getConfig().getBoolean("auto-enchant.fire.flame", true) && var5 == Material.BOW) {
               var4.put(Enchantment.FLAME, 1);
            }

            if (this.plugin.getConfig().getBoolean("auto-enchant.fire.fire-aspect", true) && this.isSword(var5)) {
               var4.put(Enchantment.FIRE_ASPECT, 2);
            }
            break;
         case PUFF:
            if (this.plugin.getConfig().getBoolean("auto-enchant.puff.feather-falling", true) && this.isBoots(var5)) {
               var4.put(Enchantment.FEATHER_FALLING, 4);
            }

            if (this.plugin.getConfig().getBoolean("auto-enchant.puff.power", true) && var5 == Material.BOW) {
               var4.put(Enchantment.POWER, 5);
            }

            if (this.plugin.getConfig().getBoolean("auto-enchant.puff.punch", true) && var5 == Material.BOW) {
               var4.put(Enchantment.PUNCH, 2);
            }
            break;
         case STRENGTH:
            if (this.plugin.getConfig().getBoolean("auto-enchant.strength.sharpness", true) && (this.isSword(var5) || this.isAxe(var5))) {
               int var6 = var3 >= 2 ? 5 : 2;
               var4.put(Enchantment.SHARPNESS, var6);
            }
            break;
         case LIFE:
            if (this.plugin.getConfig().getBoolean("auto-enchant.life.unbreaking", true) && (this.isTool(var5) || this.isWeapon(var5) || this.isArmor(var5))) {
               var4.put(Enchantment.UNBREAKING, 3);
            }
      }

      return var4;
   }

   private boolean isTool(Material var1) {
      return this.isPickaxe(var1) || this.isShovel(var1) || this.isAxe(var1) || this.isHoe(var1);
   }

   private boolean isPickaxe(Material var1) {
      return var1 == Material.WOODEN_PICKAXE || var1 == Material.STONE_PICKAXE || var1 == Material.IRON_PICKAXE || var1 == Material.GOLDEN_PICKAXE || var1 == Material.DIAMOND_PICKAXE || var1 == Material.NETHERITE_PICKAXE;
   }

   private boolean isShovel(Material var1) {
      return var1 == Material.WOODEN_SHOVEL || var1 == Material.STONE_SHOVEL || var1 == Material.IRON_SHOVEL || var1 == Material.GOLDEN_SHOVEL || var1 == Material.DIAMOND_SHOVEL || var1 == Material.NETHERITE_SHOVEL;
   }

   private boolean isAxe(Material var1) {
      return var1 == Material.WOODEN_AXE || var1 == Material.STONE_AXE || var1 == Material.IRON_AXE || var1 == Material.GOLDEN_AXE || var1 == Material.DIAMOND_AXE || var1 == Material.NETHERITE_AXE;
   }

   private boolean isHoe(Material var1) {
      return var1 == Material.WOODEN_HOE || var1 == Material.STONE_HOE || var1 == Material.IRON_HOE || var1 == Material.GOLDEN_HOE || var1 == Material.DIAMOND_HOE || var1 == Material.NETHERITE_HOE;
   }

   private boolean isSword(Material var1) {
      return var1 == Material.WOODEN_SWORD || var1 == Material.STONE_SWORD || var1 == Material.IRON_SWORD || var1 == Material.GOLDEN_SWORD || var1 == Material.DIAMOND_SWORD || var1 == Material.NETHERITE_SWORD;
   }

   private boolean isWeapon(Material var1) {
      return this.isSword(var1) || this.isAxe(var1) || var1 == Material.BOW || var1 == Material.CROSSBOW || var1 == Material.TRIDENT;
   }

   private boolean isArmor(Material var1) {
      String var2 = var1.name();
      return var2.endsWith("_HELMET") || var2.endsWith("_CHESTPLATE") || var2.endsWith("_LEGGINGS") || var2.endsWith("_BOOTS");
   }

   private boolean isBoots(Material var1) {
      return var1.name().endsWith("_BOOTS");
   }

   public void clearCache(UUID var1) {
   }
}


static public class ComprehensiveGemProtectionListener implements Listener {
   private final BlissGems plugin;

   public ComprehensiveGemProtectionListener(BlissGems var1) {
      this.plugin = var1;
   }

   private boolean isProtectionEnabled() {
      return this.plugin.getConfig().getBoolean("gems.prevent-drop", true);
   }

   private void sendProtectionMessage(Player var1) {
      String var2 = this.plugin.getConfigManager().getFormattedMessage("cannot-drop-gem");
      if (var2 != null && !var2.isEmpty()) {
         var1.sendMessage(var2);
      } else {
         var1.sendMessage("§c§lYou cannot drop your gem!");
      }

   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onItemDrop(PlayerDropItemEvent var1) {
      if (this.isProtectionEnabled()) {
         ItemStack var2 = var1.getItemDrop().getItemStack();
         if (CustomItemManager.isUndroppable(var2)) {
            var1.setCancelled(true);
            this.sendProtectionMessage(var1.getPlayer());
         }

      }
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerQuit(PlayerQuitEvent var1) {
      if (this.isProtectionEnabled()) {
         Player var2 = var1.getPlayer();
         ItemStack var3 = var2.getItemOnCursor();
         if (var3 != null && CustomItemManager.isUndroppable(var3)) {
            var2.setItemOnCursor((ItemStack)null);
            if (var2.getInventory().firstEmpty() != -1) {
               var2.getInventory().addItem(new ItemStack[]{var3});
            } else {
               var2.getWorld().dropItemNaturally(var2.getLocation(), var3);
            }
         }

      }
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onInventoryClick(InventoryClickEvent var1) {
      if (this.isProtectionEnabled()) {
         if (var1.getWhoClicked() instanceof Player) {
            Player var2 = (Player)var1.getWhoClicked();
            ItemStack var3 = var1.getCursor();
            ItemStack var4 = var1.getCurrentItem();
            Inventory var5 = var1.getClickedInventory();
            Inventory var6 = var1.getView().getTopInventory();
            if (var6 == null || var6.getType() != InventoryType.ENDER_CHEST || (var3 == null || !CustomItemManager.isUndroppable(var3)) && (var4 == null || !CustomItemManager.isUndroppable(var4) || !var1.isShiftClick())) {
               if (var3 != null && CustomItemManager.isUndroppable(var3) && var5 != null && !this.isPlayerInventory(var5)) {
                  var1.setCancelled(true);
                  this.sendProtectionMessage(var2);
               } else if (var1.isShiftClick() && var4 != null && CustomItemManager.isUndroppable(var4) && var5 != null && this.isPlayerInventory(var5) && var6 != null && !this.isPlayerInventory(var6)) {
                  var1.setCancelled(true);
                  this.sendProtectionMessage(var2);
               } else {
                  if (var1.getClick() == ClickType.NUMBER_KEY) {
                     int var7 = var1.getHotbarButton();
                     if (var7 >= 0 && var7 < 9) {
                        ItemStack var8 = var2.getInventory().getItem(var7);
                        if (var8 != null && CustomItemManager.isUndroppable(var8) && var5 != null && !this.isPlayerInventory(var5)) {
                           var1.setCancelled(true);
                           this.sendProtectionMessage(var2);
                           return;
                        }
                     }
                  }

                  if ((var1.getClick() == ClickType.DROP || var1.getClick() == ClickType.CONTROL_DROP) && var4 != null && CustomItemManager.isUndroppable(var4)) {
                     var1.setCancelled(true);
                     this.sendProtectionMessage(var2);
                  } else {
                     if (var1.getClick() == ClickType.SWAP_OFFHAND) {
                        ItemStack var9 = var2.getInventory().getItemInOffHand();
                        if (var4 != null && CustomItemManager.isUndroppable(var4) || var9 != null && CustomItemManager.isUndroppable(var9)) {
                           var1.setCancelled(true);
                           return;
                        }
                     }

                     if (var1.getClick() == ClickType.DOUBLE_CLICK && var3 != null && CustomItemManager.isUndroppable(var3) && var5 != null && !this.isPlayerInventory(var5)) {
                        var1.setCancelled(true);
                        this.sendProtectionMessage(var2);
                     } else {
                        if (var2.getGameMode() == GameMode.CREATIVE && var1.getClick() == ClickType.CREATIVE && var4 != null && CustomItemManager.isUndroppable(var4)) {
                        }

                     }
                  }
               }
            } else {
               var1.setCancelled(true);
               this.sendProtectionMessage(var2);
            }
         }
      }
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onInventoryDrag(InventoryDragEvent var1) {
      if (this.isProtectionEnabled()) {
         if (var1.getWhoClicked() instanceof Player) {
            ItemStack var2 = var1.getOldCursor();
            if (var2 != null && CustomItemManager.isUndroppable(var2)) {
               Inventory var3 = var1.getView().getTopInventory();
               if (var3 != null && !this.isPlayerInventory(var3)) {
                  int var4 = var3.getSize();

                  for(int var6 : var1.getRawSlots()) {
                     if (var6 < var4) {
                        var1.setCancelled(true);
                        this.sendProtectionMessage((Player)var1.getWhoClicked());
                        return;
                     }
                  }
               }

            }
         }
      }
   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onInventoryClose(InventoryCloseEvent var1) {
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onInventoryMoveItem(InventoryMoveItemEvent var1) {
      if (this.isProtectionEnabled()) {
         ItemStack var2 = var1.getItem();
         if (CustomItemManager.isUndroppable(var2)) {
            var1.setCancelled(true);
         }

      }
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onPlayerInteractEntity(PlayerInteractEntityEvent var1) {
      if (this.isProtectionEnabled()) {
         Player var2 = var1.getPlayer();
         ItemStack var3 = var2.getInventory().getItemInMainHand();
         if (CustomItemManager.isUndroppable(var3)) {
            Entity var4 = var1.getRightClicked();
            if (var4.getType().name().contains("ITEM_FRAME") || var4.getType().name().contains("ARMOR_STAND")) {
               var1.setCancelled(true);
               this.sendProtectionMessage(var2);
            }
         }

         ItemStack var6 = var2.getInventory().getItemInOffHand();
         if (CustomItemManager.isUndroppable(var6)) {
            Entity var5 = var1.getRightClicked();
            if (var5.getType().name().contains("ITEM_FRAME") || var5.getType().name().contains("ARMOR_STAND")) {
               var1.setCancelled(true);
               this.sendProtectionMessage(var2);
            }
         }

      }
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onEntityPickupItem(EntityPickupItemEvent var1) {
      if (this.isProtectionEnabled()) {
         if (!(var1.getEntity() instanceof Player)) {
            ItemStack var2 = var1.getItem().getItemStack();
            if (CustomItemManager.isUndroppable(var2)) {
               var1.setCancelled(true);
            }

         }
      }
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onPlayerInteract(PlayerInteractEvent var1) {
      if (this.isProtectionEnabled()) {
         ItemStack var2 = var1.getItem();
         if (var2 != null && CustomItemManager.isUndroppable(var2) && var1.getClickedBlock() != null && var1.getClickedBlock().getType().name().contains("COMPOSTER")) {
            var1.setCancelled(true);
            this.sendProtectionMessage(var1.getPlayer());
         }

      }
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onWorldChange(PlayerChangedWorldEvent var1) {
      if (this.isProtectionEnabled()) {
         Player var2 = var1.getPlayer();
         this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
            if (var2.isOnline()) {
               this.enforceOneGemOnly(var2);
            }
         }, 1L);
      }
   }

   private void enforceOneGemOnly(Player var1) {
      boolean var2 = false;
      ArrayList var3 = new ArrayList();

      for(int var4 = 0; var4 < var1.getInventory().getSize(); ++var4) {
         ItemStack var5 = var1.getInventory().getItem(var4);
         if (var5 != null) {
            String var6 = CustomItemManager.getIdByItem(var5);
            if (var6 != null && this.plugin.getGemManager().isAnyGem(var6)) {
               if (!var2) {
                  var2 = true;
               } else {
                  var3.add(var4);
               }
            }
         }
      }

      if (!var3.isEmpty()) {
         for(int var8 : var3) {
            var1.getInventory().setItem(var8, (ItemStack)null);
         }

         Logger var10000 = this.plugin.getLogger();
         int var10001 = var3.size();
         var10000.info("Removed " + var10001 + " duplicate gem(s) from " + var1.getName() + " after world change.");
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onItemSpawn(ItemSpawnEvent var1) {
      Item var2 = var1.getEntity();
      ItemStack var3 = var2.getItemStack();
      if (var3 != null && this.isGemLike(var3)) {
         CustomItemManager.markAsUndroppable(var3);
         var2.setItemStack(var3);

         try {
            var2.setUnlimitedLifetime(true);
         } catch (NoSuchMethodError var5) {
            var2.setTicksLived(1);
         }

         var2.setInvulnerable(true);
         var2.setPersistent(true);
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void onItemDespawn(ItemDespawnEvent var1) {
      ItemStack var2 = var1.getEntity().getItemStack();
      if (this.isGemLike(var2)) {
         var1.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST,
      ignoreCancelled = true
   )
   public void onItemMerge(ItemMergeEvent var1) {
      if (this.isGemLike(var1.getEntity().getItemStack()) || this.isGemLike(var1.getTarget().getItemStack())) {
         var1.setCancelled(true);
      }

   }

   private boolean isGemLike(ItemStack var1) {
      if (var1 == null) {
         return false;
      } else if (CustomItemManager.isUndroppable(var1)) {
         return true;
      } else {
         String var2 = CustomItemManager.getIdByItem(var1);
         return var2 != null && this.plugin.getGemManager().isAnyGem(var2);
      }
   }

   private boolean isPlayerInventory(Inventory var1) {
      if (var1 == null) {
         return false;
      } else {
         InventoryType var2 = var1.getType();
         if (var2 == InventoryType.ENDER_CHEST) {
            return false;
         } else {
            return var2 == InventoryType.PLAYER || var2 == InventoryType.CRAFTING;
         }
      }
   }
}


static public class GemDropListener implements Listener {
   private final BlissGems plugin;

   public GemDropListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void onItemDrop(PlayerDropItemEvent var1) {
      if (this.plugin.getConfig().getBoolean("gems.prevent-drop", true)) {
         Player var2 = var1.getPlayer();
         ItemStack var3 = var1.getItemDrop().getItemStack();
         boolean var4 = CustomItemManager.isUndroppable(var3);
         if (var4) {
            var1.setCancelled(true);
            String var5 = this.plugin.getConfigManager().getFormattedMessage("cannot-drop-gem");
            if (var5 != null && !var5.isEmpty()) {
               var2.sendMessage(var5);
            } else {
               var2.sendMessage("§c§lYou cannot drop your gem!");
            }
         }

      }
   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void onInventoryMoveItem(InventoryMoveItemEvent var1) {
      ItemStack var2 = var1.getItem();
      if (CustomItemManager.isUndroppable(var2)) {
         var1.setCancelled(true);
      }

   }
}


static public class GemInteractListener implements Listener {
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


static public class KillTrackingListener implements Listener {
   private final BlissGems plugin;
   private final Map<UUID, Long> lastDamageTime = new HashMap();
   private static final long KILL_WINDOW_MS = 3000L;

   public KillTrackingListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler
   public void onPlayerDamageByPlayer(EntityDamageByEntityEvent var1) {
      if (var1.getEntity().getType() == EntityType.PLAYER && var1.getDamager().getType() == EntityType.PLAYER) {
         Player var2 = (Player)var1.getEntity();
         Player var3 = (Player)var1.getDamager();
         if (this.plugin.getGemManager().getGemType(var3) != null) {
            var2.setMetadata("gem_damage_by", new FixedMetadataValue(this.plugin, var3.getUniqueId()));
            this.lastDamageTime.put(var2.getUniqueId(), System.currentTimeMillis());
         }

      }
   }

   @EventHandler
   public void onPlayerDeath(PlayerDeathEvent var1) {
      Player var2 = var1.getEntity();
      Player var3 = var2.getKiller();
      if (var3 != null) {
         if (this.plugin.getGemManager().getGemType(var3) != null) {
            Long var4 = (Long)this.lastDamageTime.get(var2.getUniqueId());
            if (var4 != null && System.currentTimeMillis() - var4 < 3000L) {
               this.plugin.getStatsManager().recordKill(var3, var2);
            }
         }

         this.plugin.getStatsManager().recordDeath(var2);
      }

      this.lastDamageTime.remove(var2.getUniqueId());
      if (var2.hasMetadata("gem_damage_by")) {
         var2.removeMetadata("gem_damage_by", this.plugin);
      }

   }
}


static public class PassiveListener implements Listener {
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
            if (this.plugin.getGemManager().hasGemTypeInOffhand(var4, GemType.FLUX)) {
               if (this.canUsePassives(var4)) {
                  Entity var5 = var1.getHitEntity();
                  if (var5 != null && var5 instanceof LivingEntity) {
                     double var6 = this.plugin.getConfig().getDouble("gems.passives.flux.shocking-chance", 0.15);
                     if (!(Math.random() > var6)) {
                        LivingEntity var8 = (LivingEntity)var5;
                        int var9 = this.plugin.getGemManager().getTierFromOffhand(var4);
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
      ItemStack var2 = var1.getInventory().getItemInMainHand();
      if (var2 != null) {
         String var3 = CustomItemManager.getIdByItem(var2);
         if (var3 != null && var3.contains("flux_gem")) {
            return true;
         }
      }

      return false;
   }

   @EventHandler
   public void onFluxConduction(PlayerInteractEvent var1) {
      if (var1.getAction() == Action.LEFT_CLICK_AIR || var1.getAction() == Action.LEFT_CLICK_BLOCK) {
         Player var2 = var1.getPlayer();
         if (var2.isSneaking()) {
            boolean var3 = this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX) || this.isHoldingFluxGem(var2);
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
               boolean var4 = this.plugin.getGemManager().hasGemTypeInOffhand(var3, GemType.FLUX) || this.isHoldingFluxGem(var3);
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
      if (this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX)) {
         if (this.canUsePassives(var2)) {
            this.plugin.getFlowStateManager().registerAction(var2, FlowStateManager.ActionType.BLOCK_BREAK);
         }
      }
   }

   @EventHandler
   public void onFluxFlowAttack(EntityDamageByEntityEvent var1) {
      if (var1.getDamager() instanceof Player) {
         Player var2 = (Player)var1.getDamager();
         if (this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX)) {
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
            if (this.plugin.getGemManager().hasGemTypeInOffhand(var4, GemType.FLUX)) {
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
         if (this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX)) {
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
            if (this.plugin.getGemManager().hasGemTypeInOffhand(var2, GemType.FLUX)) {
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


static public class PlayerDeathListener implements Listener {
   private final BlissGems plugin;
   private final Map<UUID, List<ItemStack>> savedGems = new HashMap();

   public PlayerDeathListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerDeath(PlayerDeathEvent var1) {
      Player var2 = var1.getEntity();
      Player var3 = var2.getKiller();
      EnergyState var4 = this.plugin.getEnergyManager().getEnergyState(var2);
      boolean var5 = var4 != EnergyState.BROKEN;
      int var6 = this.plugin.getConfigManager().getEnergyLossOnDeath();
      this.plugin.getEnergyManager().removeEnergy(var2, var6);
      int var7 = this.plugin.getEnergyManager().getEnergy(var2);
      if (var7 <= 0 && this.plugin.getConfigManager().isBanOnZeroEnergyEnabled()) {
         String var8 = this.plugin.getConfigManager().getFormattedMessage("energy-zero-banned");
         if (var8 == null || var8.isEmpty()) {
            var8 = "You have been banned for reaching 0 energy!";
         }

         var2.ban(var8, (Date)null, (String)null);
      }

      if (var3 != null && var5) {
         int var10 = this.plugin.getConfigManager().getEnergyGainOnKill();
         this.plugin.getEnergyManager().addEnergy(var3, var10);
         EnergyState var9 = this.plugin.getEnergyManager().getEnergyState(var3);
         if (var9.isMaxEnergy() && this.plugin.getConfigManager().isEnergyBottleDropEnabled()) {
            this.dropEnergyBottle(var2.getLocation());
         }
      }

      {
         List var11 = var1.getDrops();
         ArrayList var12 = new ArrayList();
         var11.removeIf((var1x) -> {
            if (CustomItemManager.isUndroppable(var1x)) {
               var12.add(var1x.clone());
               return true;
            } else {
               return false;
            }
         });
         if (!var12.isEmpty()) {
            this.savedGems.put(var2.getUniqueId(), var12);
            this.saveGemsToDisk(var2.getUniqueId(), var12);
         }
      }

      this.plugin.getAstraAbilities().cleanup(var2);
      this.plugin.getFireAbilities().cleanup(var2);
      this.plugin.getFluxAbilities().cleanup(var2);
      this.plugin.getLifeAbilities().cleanup(var2);
      if (this.plugin.getSoulManager() != null) {
         this.plugin.getSoulManager().cleanup(var2);
      }

      this.plugin.getGemManager().updateActiveGem(var2);
      if (var3 != null) {
         this.plugin.getGemManager().updateActiveGem(var3);
      }

   }

   @EventHandler
   public void onPlayerRespawn(PlayerRespawnEvent var1) {
      Player var2 = var1.getPlayer();
      UUID var3 = var2.getUniqueId();
      List var4 = null;
      if (this.savedGems.containsKey(var3)) {
         var4 = (List)this.savedGems.remove(var3);
         this.clearGemsFromDisk(var3);
      } else {
         var4 = this.loadGemsFromDisk(var3);
      }

      if (var4 != null && !var4.isEmpty()) {
         boolean var5 = this.plugin.getConfig().getBoolean("gems.single-gem-only", true);
         if (var5 && var4.size() > 1) {
            Logger var10000 = this.plugin.getLogger();
            String var10001 = var2.getName();
            var10000.warning("Player " + var10001 + " had " + var4.size() + " gems saved! Only returning first gem due to single-gem-only config.");
            var2.getInventory().addItem(new ItemStack[]{(ItemStack)var4.get(0)});
         } else {
            for(ItemStack var7 : var4) {
               for(ItemStack var8 : var2.getInventory().addItem(new ItemStack[]{var7}).values()) {
                  var2.getWorld().dropItemNaturally(var2.getLocation(), var8);
               }
            }
         }
      }

      this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
         if (var2.isOnline()) {
            this.enforceOneGemOnly(var2);
            this.plugin.getGemManager().updateActiveGem(var2);
         }

      }, 1L);
   }

   private void dropEnergyBottle(Location var1) {
      ItemStack var2 = CustomItemManager.getItemById("energy_bottle");
      if (var2 != null) {
         var1.getWorld().dropItemNaturally(var1, var2);
      }

   }

   private void saveGemsToDisk(UUID var1, List<ItemStack> var2) {
      File var3 = new File(this.plugin.getDataFolder(), "playerdata");
      if (!var3.exists()) {
         var3.mkdirs();
      }

      File var4 = new File(var3, String.valueOf(var1) + ".yml");
      YamlConfiguration var5;
      if (var4.exists()) {
         var5 = YamlConfiguration.loadConfiguration(var4);
      } else {
         var5 = new YamlConfiguration();
      }

      ((FileConfiguration)var5).set("saved-gems", var2);

      try {
         ((FileConfiguration)var5).save(var4);
      } catch (IOException var7) {
         Logger var10000 = this.plugin.getLogger();
         String var10001 = String.valueOf(var1);
         var10000.severe("Failed to save gems for player " + var10001 + ": " + var7.getMessage());
      }

   }

   private List<ItemStack> loadGemsFromDisk(UUID var1) {
      File var2 = new File(this.plugin.getDataFolder(), "playerdata");
      File var3 = new File(var2, String.valueOf(var1) + ".yml");
      if (!var3.exists()) {
         return null;
      } else {
         YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
         List var5 = (List)((FileConfiguration)var4).get("saved-gems");
         if (var5 != null) {
            ((FileConfiguration)var4).set("saved-gems", (Object)null);

            try {
               ((FileConfiguration)var4).save(var3);
            } catch (IOException var7) {
               this.plugin.getLogger().warning("Failed to clear saved gems from disk for " + String.valueOf(var1));
            }
         }

         return var5;
      }
   }

   private void clearGemsFromDisk(UUID var1) {
      File var2 = new File(this.plugin.getDataFolder(), "playerdata");
      File var3 = new File(var2, String.valueOf(var1) + ".yml");
      if (var3.exists()) {
         YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
         if (((FileConfiguration)var4).contains("saved-gems")) {
            ((FileConfiguration)var4).set("saved-gems", (Object)null);

            try {
               ((FileConfiguration)var4).save(var3);
            } catch (IOException var6) {
               this.plugin.getLogger().warning("Failed to clear saved gems from disk for " + String.valueOf(var1));
            }
         }

      }
   }

   private void enforceOneGemOnly(Player var1) {
      boolean var2 = false;

      for(int var3 = 0; var3 < var1.getInventory().getSize(); ++var3) {
         ItemStack var4 = var1.getInventory().getItem(var3);
         if (var4 != null) {
            String var5 = CustomItemManager.getIdByItem(var4);
            if (var5 != null && this.plugin.getGemManager().isAnyGem(var5)) {
               if (!var2) {
                  var2 = true;
               } else {
                  ItemStack var6 = var1.getInventory().getItem(var3);
                  var1.getInventory().setItem(var3, (ItemStack)null);
                  var1.getWorld().dropItemNaturally(var1.getLocation(), var6);
                  this.plugin.getLogger().info("Moved duplicate gem from " + var1.getName() + " to the ground on respawn.");
               }
            }
         }
      }

   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent var1) {
      this.plugin.getGemManager().updateActiveGem(var1.getPlayer());
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent var1) {
      Player var2 = var1.getPlayer();
      this.plugin.getAstraAbilities().cleanup(var2);
      this.plugin.getFireAbilities().cleanup(var2);
      this.plugin.getFluxAbilities().cleanup(var2);
      this.plugin.getSpeedAbilities().d(var2.getUniqueId());
      this.plugin.getWealthAbilities().cleanup(var2);
      this.plugin.getLifeAbilities().cleanup(var2);
      this.plugin.getSoulManager().cleanup(var2);
      this.plugin.getSoulManager().clearSouls(var2.getUniqueId());
      this.plugin.getEnergyManager().clearCache(var2.getUniqueId());
      this.plugin.getGemManager().clearCache(var2.getUniqueId());
      this.plugin.getAbilityManager().clearCache(var2.getUniqueId());
   }
}


static public class PlayerJoinListener implements Listener {
   private final BlissGems plugin;
   private final Random random;

   public PlayerJoinListener(BlissGems var1) {
      this.plugin = var1;
      this.random = new Random();
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent var1) {
      Player var2 = var1.getPlayer();
      if (!this.hasReceivedFirstGem(var2) && this.plugin.getGemManager().findGemInInventory(var2) == null) {
         if (!this.plugin.getConfigManager().isSmpStarted()) {
            this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
               if (var2.isOnline()) {
                  this.plugin.getConfigManager().sendFormattedMessage(var2, "smp-not-started");
               }

            }, 40L);
         } else {
            GemType var3 = this.getRandomEnabledGem();
            if (var3 != null) {
               this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                  if (var2.isOnline()) {
                     var2.sendMessage("");
                     var2.sendMessage("§d§l§m                                                  ");
                     var2.sendMessage("§d§lWELCOME TO BLISSGEMS!");
                     var2.sendMessage("");
                     var2.sendMessage("§7§oThe ancient gem ritual begins...");
                     var2.sendMessage("§7§oYour destiny is being forged...");
                     var2.sendMessage("§d§l§m                                                  ");
                     var2.sendMessage("");
                     this.plugin.getGemRitualManager().performGemRitual(var2, var3, true, 1);
                     this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                        if (this.plugin.getGemManager().giveGem(var2, (GemType)var3, 1)) {
                           this.markFirstGemReceived(var2);
                           String var3x = this.plugin.getConfigManager().getFormattedMessage("first-gem-received", "gem", var3.getDisplayName());
                           if (var3x != null && !var3x.isEmpty()) {
                              var2.sendMessage(var3x);
                           } else {
                              var2.sendMessage("");
                              String var10001 = var3.getColor();
                              var2.sendMessage("§d§l» §fYour gem has been chosen: " + var10001 + "§l" + var3.getDisplayName() + "§d§l «");
                              var2.sendMessage("");
                           }

                           Logger var10000 = this.plugin.getLogger();
                           String var4 = var2.getName();
                           var10000.info("Gave " + var4 + " their first gem: " + var3.getDisplayName());
                        }

                     }, 20L);
                  }

               }, 40L);
            }
         }
      }

      if (!this.hasBeenGemLockChecked(var2)) {
         boolean var7 = false;

         for(int var4 = 0; var4 < var2.getInventory().getSize(); ++var4) {
            ItemStack var5 = var2.getInventory().getItem(var4);
            if (var5 != null) {
               String var6 = CustomItemManager.getIdByItem(var5);
               if (var6 != null && GemType.isGem(var6) && CustomItemManager.markAsUndroppable(var5)) {
                  var7 = true;
               }
            }
         }

         ItemStack var8 = var2.getInventory().getItemInOffHand();
         if (var8 != null) {
            String var9 = CustomItemManager.getIdByItem(var8);
            if (var9 != null && GemType.isGem(var9) && CustomItemManager.markAsUndroppable(var8)) {
               var7 = true;
            }
         }

         this.markGemLockChecked(var2);
         if (var7) {
            this.plugin.getLogger().info("Fixed undroppable tags on gems for " + var2.getName());
         }
      }

      this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
         if (var2.isOnline()) {
            if (this.plugin.getLifeAbilities() != null) {
               this.plugin.getLifeAbilities().cleanup(var2);
            }

            if (this.plugin.getSoulManager() != null) {
               this.plugin.getSoulManager().cleanup(var2);
            }

         }
      }, 20L);
      this.plugin.getGemManager().updateActiveGem(var2);
      this.plugin.getAbilityManager().loadCooldowns(var2.getUniqueId());
      this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
         if (var2.isOnline()) {
            this.plugin.getPluginMessagingManager().sendGemData(var2);
         }

      }, 20L);
      if (!this.hasSeenBindingTip(var2)) {
         this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
            if (var2.isOnline()) {
               var2.sendMessage("");
               var2.sendMessage("§d§l⚡ Tip: §fYou can customize how your gem abilities are triggered!");
               var2.sendMessage("§7Run §f/bliss ability §7to see your current bindings.");
               var2.sendMessage("§7Change them with §f/bliss set_ability <slot> <input> §7(e.g. §f/bliss set_ability primary left_click§7).");
               var2.sendMessage("");
               this.markBindingTipSeen(var2);
            }
         }, 100L);
      }

   }

   private boolean hasSeenBindingTip(Player var1) {
      File var2 = new File(this.plugin.getDataFolder(), "playerdata");
      File var3 = new File(var2, String.valueOf(var1.getUniqueId()) + ".yml");
      if (!var3.exists()) {
         return false;
      } else {
         YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
         return ((FileConfiguration)var4).getBoolean("seen-binding-tip", false);
      }
   }

   private void markBindingTipSeen(Player var1) {
      File var2 = new File(this.plugin.getDataFolder(), "playerdata");
      if (!var2.exists()) {
         var2.mkdirs();
      }

      File var3 = new File(var2, String.valueOf(var1.getUniqueId()) + ".yml");
      YamlConfiguration var4 = var3.exists() ? YamlConfiguration.loadConfiguration(var3) : new YamlConfiguration();
      ((FileConfiguration)var4).set("seen-binding-tip", true);

      try {
         ((FileConfiguration)var4).save(var3);
      } catch (IOException var6) {
         Logger var10000 = this.plugin.getLogger();
         String var10001 = var1.getName();
         var10000.warning("Failed to mark binding tip seen for " + var10001 + ": " + var6.getMessage());
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

   private boolean hasBeenGemLockChecked(Player var1) {
      File var2 = new File(this.plugin.getDataFolder(), "playerdata");
      File var3 = new File(var2, String.valueOf(var1.getUniqueId()) + ".yml");
      if (!var3.exists()) {
         return false;
      } else {
         YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
         return ((FileConfiguration)var4).getBoolean("gems-lock-checked", false);
      }
   }

   private void markGemLockChecked(Player var1) {
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

      ((FileConfiguration)var4).set("gems-lock-checked", true);

      try {
         ((FileConfiguration)var4).save(var3);
      } catch (IOException var6) {
         Logger var10000 = this.plugin.getLogger();
         String var10001 = var1.getName();
         var10000.warning("Failed to save gem-lock-checked for " + var10001 + ": " + var6.getMessage());
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
         return (GemType)var1.get(this.random.nextInt(var1.size()));
      }
   }
}


static public class RepairKitListener implements Listener {
   private final BlissGems plugin;

   public RepairKitListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent var1) {
      if (var1.getAction() == Action.RIGHT_CLICK_AIR || var1.getAction() == Action.RIGHT_CLICK_BLOCK) {
         Player var2 = var1.getPlayer();
         ItemStack var3 = var1.getItem();
         if (var3 != null) {
            String var4 = CustomItemManager.getIdByItem(var3);
            if (var4 != null && var4.equals("repair_kit")) {
               var1.setCancelled(true);
               var2.sendMessage("§d§oTo use the Repair Kit, drop it on top of a Beacon to create a Pedestal!");
               var2.sendMessage("§d§oThe Pedestal will restore energy to all nearby players.");
            }
         }
      }
   }

   @EventHandler
   public void onPlayerDropItem(PlayerDropItemEvent var1) {
      final Item var2 = var1.getItemDrop();
      final ItemStack var3 = var2.getItemStack();
      final Player var4 = var1.getPlayer();
      String var5 = CustomItemManager.getIdByItem(var3);
      if (var5 != null && var5.equals("repair_kit")) {
         (new BukkitRunnable() {
            public void run() {
               if (var2.isValid() && !var2.isDead()) {
                  Location var1 = var2.getLocation();
                  Block var2x = var1.subtract((double)0.0F, (double)1.0F, (double)0.0F).getBlock();
                  if (var2x.getType() == Material.BEACON) {
                     var2.remove();
                     boolean var3x = RepairKitListener.this.plugin.getRepairKitManager().createPedestal(var2x.getLocation());
                     if (var3x) {
                        var4.sendMessage("§d§l✦ §d§oPedestal created! It will restore energy to nearby players.");

                        for(Entity var5 : var1.getWorld().getNearbyEntities(var1, (double)15.0F, (double)15.0F, (double)15.0F)) {
                           if (var5 instanceof Player && var5 != var4) {
                              Player var6 = (Player)var5;
                              var6.sendMessage("§d§o" + var4.getName() + " created a Repair Kit Pedestal nearby!");
                           }
                        }
                     } else {
                        var4.getInventory().addItem(new ItemStack[]{var3});
                        var4.sendMessage("§c§oCouldn't create pedestal at this location!");
                     }
                  }

               }
            }
         }).runTaskLater(this.plugin, 20L);
      }
   }

   @EventHandler
   public void onItemSpawn(ItemSpawnEvent var1) {
      final Item var2 = var1.getEntity();
      ItemStack var3 = var2.getItemStack();
      String var4 = CustomItemManager.getIdByItem(var3);
      if (var4 != null && var4.equals("repair_kit")) {
         (new BukkitRunnable() {
            public void run() {
               if (var2.isValid() && !var2.isDead()) {
                  Location var1 = var2.getLocation();
                  Block var2x = var1.clone().subtract((double)0.0F, (double)1.0F, (double)0.0F).getBlock();
                  if (var2x.getType() == Material.BEACON) {
                     var2.remove();
                     boolean var3 = RepairKitListener.this.plugin.getRepairKitManager().createPedestal(var2x.getLocation());
                     if (var3) {
                        for(Entity var5 : var1.getWorld().getNearbyEntities(var1, (double)15.0F, (double)15.0F, (double)15.0F)) {
                           if (var5 instanceof Player) {
                              Player var6 = (Player)var5;
                              var6.sendMessage("§d§l✦ §d§oRepair Kit Pedestal activated!");
                           }
                        }
                     }
                  }

               }
            }
         }).runTaskLater(this.plugin, 40L);
      }
   }
}


static public class ReviveBeaconListener implements Listener {
   private final BlissGems plugin;

   public ReviveBeaconListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent var1) {
      Player var2 = var1.getPlayer();
      ItemStack var3 = var1.getItem();
      if (var3 != null) {
         String var4 = CustomItemManager.getIdByItem(var3);
         if (var4 != null && var4.equals("revive_beacon")) {
            if (var1.getAction().toString().contains("RIGHT_CLICK")) {
               var1.setCancelled(true);
               int var5 = this.plugin.getConfig().getInt("revive-beacon.duration", 300);
               double var6 = this.plugin.getConfig().getDouble("revive-beacon.range", (double)10.0F);
               Location var8 = var2.getLocation().clone();
               if (var3.getAmount() > 1) {
                  var3.setAmount(var3.getAmount() - 1);
               } else {
                  var2.getInventory().setItemInMainHand((ItemStack)null);
               }

               int var9 = var5 / 60;
               int var10 = var5 % 60;
               String var11 = var9 > 0 ? String.format("%dm %ds", var9, var10) : String.format("%ds", var10);
               var2.sendMessage("");
               var2.sendMessage("§e§l§m                                      ");
               var2.sendMessage("§e§l§nREVIVE BEACON RITUAL");
               var2.sendMessage("");
               var2.sendMessage("§7§oThe beacon of resurrection awakens...");
               var2.sendMessage("§7§oDuration: §f" + var11);
               var2.sendMessage("§7§oRange: §f" + (int)var6 + " blocks");
               var2.sendMessage("§e§l§m                                      ");
               var2.sendMessage("");
               this.plugin.getGemRitualManager().performReviveBeaconRitual(var2, var8);
               this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                  this.plugin.getReviveBeaconManager().activateBeacon(var2, var8, var5, var6);
                  var2.sendMessage("");
                  var2.sendMessage("§a§l✦ Revive Beacon is now active!");
                  var2.sendMessage("§7If you die within range, you'll be revived and unbanned!");
                  var2.sendMessage("");
               }, 60L);
            }

         }
      }
   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void onPlayerDeath(PlayerDeathEvent var1) {
      Player var2 = var1.getEntity();
      if (this.plugin.getReviveBeaconManager().canRevive(var2)) {
         Location var3 = this.plugin.getReviveBeaconManager().getReviveLocation(var2);
         if (var3 != null) {
            var1.setKeepInventory(true);
            var1.setKeepLevel(true);
            var1.getDrops().clear();
            int var4 = this.plugin.getEnergyManager().getEnergy(var2);
            int var5 = this.plugin.getConfigManager().getEnergyLossOnDeath();
            int var6 = var4 - var5;
            boolean var7 = var6 <= 0 && this.plugin.getConfigManager().isBanOnZeroEnergyEnabled();
            this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
               var2.spigot().respawn();
               if (var7 || var2.isBanned()) {
                  BanList var5 = Bukkit.getBanList(Type.PROFILE);
                  if (var5.isBanned(var2.getName()) || var5.isBanned(var2.getUniqueId().toString())) {
                     var5.pardon(var2.getName());
                     var5.pardon(var2.getUniqueId().toString());
                     var2.sendMessage("§d§l✦ You have been unbanned by the Revive Beacon!");
                  }
               }

               if (var6 <= 0) {
                  int var7x = this.plugin.getConfig().getInt("revive-beacon.restore-energy", 3);
                  this.plugin.getEnergyManager().setEnergy(var2, var7x);
                  var2.sendMessage("§b§l✦ Your energy has been restored to " + var7x + "!");
               }

               var2.teleport(var3);
               double var8 = var2.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
               var2.setHealth(Math.min(var8, this.plugin.getConfig().getDouble("revive-beacon.revive-health", var8 / (double)2.0F)));
               var2.setFoodLevel(20);
               var2.setFireTicks(0);
               var2.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
               var2.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 1));
               var2.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 0));
               var2.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 0));
               var2.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var2.getLocation().clone().add((double)0.0F, (double)1.0F, (double)0.0F), 100, (double)1.0F, (double)2.0F, (double)1.0F, 0.2);
               var2.getWorld().playSound(var2.getLocation(), Sound.ITEM_TOTEM_USE, 1.0F, 1.0F);
               var2.getWorld().playSound(var2.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0F, 1.5F);
               var2.sendMessage("");
               var2.sendMessage("§a§l✦ §f§lYOU HAVE BEEN REVIVED!");
               var2.sendMessage("§7Your Revive Beacon has saved you from death!");
               var2.sendMessage("");
               this.plugin.getReviveBeaconManager().removeBeacon(var2);
            }, 1L);
         }
      }
   }
}


static public class StunListener implements Listener {
   private final BlissGems plugin;

   public StunListener(BlissGems var1) {
      this.plugin = var1;
   }

   private boolean isImmobilized(UUID var1) {
      return c.a(var1) || f.a(var1);
   }

   private boolean isFrozen(UUID var1) {
      return f.a(var1) || c.a(var1);
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerMove(PlayerMoveEvent var1) {
      Player var2 = var1.getPlayer();
      if (this.isFrozen(var2.getUniqueId())) {
         Location var3 = var1.getFrom();
         Location var4 = var1.getTo();
         if (var3.getX() != var4.getX() || var3.getY() != var4.getY() || var3.getZ() != var4.getZ()) {
            Location var5 = var3.clone();
            var5.setYaw(var4.getYaw());
            var5.setPitch(var4.getPitch());
            var1.setTo(var5);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onToggleFlight(PlayerToggleFlightEvent var1) {
      if (this.isImmobilized(var1.getPlayer().getUniqueId())) {
         var1.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerInteract(PlayerInteractEvent var1) {
      Player var2 = var1.getPlayer();
      ItemStack var3 = var1.getItem();
      if (var3 != null) {
         UUID var4 = var2.getUniqueId();
         if (this.isImmobilized(var4)) {
            Material var5 = var3.getType();
            if (var5 == Material.ENDER_PEARL || var5 == Material.CHORUS_FRUIT) {
               var1.setCancelled(true);
               var2.sendMessage(this.plugin.getConfigManager().getFormattedMessage("cannot-use-while-stunned", "item", var5 == Material.ENDER_PEARL ? "ender pearls" : "chorus fruit"));
               return;
            }

            if (this.isFrozen(var4)) {
               if (var5 != Material.GOLDEN_APPLE && var5 != Material.ENCHANTED_GOLDEN_APPLE) {
                  var1.setCancelled(true);
                  if (var3.getType().isEdible() || var5 == Material.POTION || var5 == Material.SPLASH_POTION || var5 == Material.LINGERING_POTION) {
                     var2.sendMessage(this.plugin.getConfigManager().getMessage("can-only-eat-golden-apple-stunned"));
                  }

                  return;
               }
            } else if (var5 != Material.GOLDEN_APPLE && var5 != Material.ENCHANTED_GOLDEN_APPLE && (var3.getType().isEdible() || var5 == Material.POTION || var5 == Material.SPLASH_POTION || var5 == Material.LINGERING_POTION)) {
               var1.setCancelled(true);
               var2.sendMessage(this.plugin.getConfigManager().getMessage("can-only-eat-golden-apple-stunned"));
            }
         }

      }
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerConsume(PlayerItemConsumeEvent var1) {
      Player var2 = var1.getPlayer();
      ItemStack var3 = var1.getItem();
      if (this.isImmobilized(var2.getUniqueId())) {
         Material var4 = var3.getType();
         if (var4 != Material.GOLDEN_APPLE && var4 != Material.ENCHANTED_GOLDEN_APPLE) {
            var1.setCancelled(true);
            var2.sendMessage(this.plugin.getConfigManager().getMessage("can-only-eat-golden-apple-stunned"));
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onProjectileLaunch(ProjectileLaunchEvent var1) {
      ProjectileSource var3 = var1.getEntity().getShooter();
      if (var3 instanceof Player var2) {
         if (this.isImmobilized(var2.getUniqueId())) {
            var1.setCancelled(true);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onInventoryOpen(InventoryOpenEvent var1) {
      HumanEntity var3 = var1.getPlayer();
      if (var3 instanceof Player var2) {
         if (this.isFrozen(var2.getUniqueId())) {
            var1.setCancelled(true);
            var2.sendMessage("§c§lYou cannot open inventories while frozen!");
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onItemDrop(PlayerDropItemEvent var1) {
      if (this.isFrozen(var1.getPlayer().getUniqueId())) {
         var1.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onEntityDamage(EntityDamageByEntityEvent var1) {
      Entity var3 = var1.getDamager();
      if (var3 instanceof Player var2) {
         if (this.isFrozen(var2.getUniqueId())) {
            var1.setCancelled(true);
            var2.sendMessage("§c§lYou cannot attack while frozen!");
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onBlockBreak(BlockBreakEvent var1) {
      if (this.isFrozen(var1.getPlayer().getUniqueId())) {
         var1.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onBlockPlace(BlockPlaceEvent var1) {
      if (this.isFrozen(var1.getPlayer().getUniqueId())) {
         var1.setCancelled(true);
      }

   }
}


static public class SwapHandAbilityListener implements Listener {
   private final BlissGems plugin;

   public SwapHandAbilityListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onSwap(PlayerSwapHandItemsEvent var1) {
      Player var2 = var1.getPlayer();
      ItemStack var3 = var1.getMainHandItem();
      ItemStack var4 = var1.getOffHandItem();
      boolean var5 = this.isGem(var3);
      boolean var6 = this.isGem(var4);
      if (var5 || var6) {
         var1.setCancelled(true);
         if (this.plugin.getBlissCommand() != null) {
            if (this.plugin.getClickActivationManager().isClickActivationEnabled(var2)) {
               AbilityBinding var7 = AbilityBinding.swapHand(var2.isSneaking());
               AbilitySlot var8 = this.plugin.getAbilityBindingManager() != null ? this.plugin.getAbilityBindingManager().getSlot(var2, var7) : null;
               this.plugin.getBlissCommand().triggerSlot(var2, var8);
            }
         }
      }
   }

   private boolean isGem(ItemStack var1) {
      if (var1 == null) {
         return false;
      } else {
         String var2 = CustomItemManager.getIdByItem(var1);
         if (var2 == null) {
            return false;
         } else if (GemType.isGem(var2)) {
            return true;
         } else {
            GemRegistryImpl var3 = this.plugin.getGemRegistry();
            return var3 != null && var3.isRegisteredGem(var2);
         }
      }
   }
}


static public class TeleportListener implements Listener {
   private final BlissGems plugin;

   public TeleportListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler
   public void onPlayerTeleport(PlayerTeleportEvent var1) {
      Player var2 = var1.getPlayer();
      if (var1.getCause() == TeleportCause.SPECTATE && this.plugin.getAstraAbilities().c(var2)) {
         var1.setCancelled(true);
         var2.sendMessage("§d§oYou cannot teleport to players while projecting!");
      } else {
         if (this.plugin.getFireAbilities().a(var2)) {
            this.plugin.getFireAbilities().e(var2);
            var2.sendMessage("§c§oYour fireball charge was cancelled due to teleportation.");
         }

         if (this.plugin.getFluxAbilities().a(var2)) {
            this.plugin.getFluxAbilities().d(var2);
            var2.sendMessage("§b⚡ §oYour flux beam charge was cancelled due to teleportation.");
         }

      }
   }
}


static public class UpgraderListener implements Listener {
   private final BlissGems plugin;

   public UpgraderListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent var1) {
      if (var1.getAction() == Action.RIGHT_CLICK_AIR || var1.getAction() == Action.RIGHT_CLICK_BLOCK) {
         Player var2 = var1.getPlayer();
         ItemStack var3 = var1.getItem();
         if (var3 != null) {
            String var4 = CustomItemManager.getIdByItem(var3);
            if (var4 != null && var4.equals("gem_upgrader")) {
               var1.setCancelled(true);
               if (!this.plugin.getGemManager().hasActiveGem(var2)) {
                  this.plugin.getConfigManager().sendFormattedMessage(var2, "no-gem");
               } else {
                  GemType var5 = this.plugin.getGemManager().getGemType(var2);
                  int var6 = this.plugin.getGemManager().getGemTier(var2);
                  if (var6 != 1) {
                     this.plugin.getConfigManager().sendFormattedMessage(var2, "upgrade-already-tier2");
                  } else {
                     if (this.plugin.getGemManager().upgradeGem(var2, var5)) {
                        ItemStack var7 = var2.getInventory().getItemInMainHand();
                        ItemStack var8 = var2.getInventory().getItemInOffHand();
                        boolean var9 = var7 != null && "gem_upgrader".equals(CustomItemManager.getIdByItem(var7));
                        boolean var10 = var8 != null && "gem_upgrader".equals(CustomItemManager.getIdByItem(var8));
                        if (var3.getAmount() > 1) {
                           var3.setAmount(var3.getAmount() - 1);
                        } else if (var9) {
                           var2.getInventory().setItemInMainHand((ItemStack)null);
                        } else if (var10) {
                           var2.getInventory().setItemInOffHand((ItemStack)null);
                        }

                        if (this.plugin.getConfigManager().shouldPlayUpgradeEffects()) {
                           try {
                              String var11 = this.plugin.getConfigManager().getUpgradeSound();
                              Sound var12 = Sound.valueOf(var11);
                              var2.playSound(var2.getLocation(), var12, 1.0F, 1.5F);
                              String var13 = this.plugin.getConfigManager().getUpgradeParticle();
                              Particle var14 = Particle.valueOf(var13);
                              int var15 = this.plugin.getConfigManager().getUpgradeParticleCount();
                              var2.spawnParticle(var14, var2.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), var15, (double)0.5F, (double)0.5F, (double)0.5F);
                           } catch (IllegalArgumentException var16) {
                              this.plugin.getLogger().warning("Invalid particle or sound in config: " + var16.getMessage());
                           }
                        }

                        this.plugin.getConfigManager().sendFormattedMessage(var2, "upgrade-success");
                        if (this.plugin.getAchievementManager() != null) {
                           this.plugin.getAchievementManager().unlock(var2, Achievement.THE_NEXT_LEVEL);
                        }
                     } else {
                        var2.sendMessage("§cFailed to upgrade gem!");
                     }

                  }
               }
            }
         }
      }
   }
}


static public class VillagerTradeListener implements Listener {
   private final BlissGems plugin;

   public VillagerTradeListener(BlissGems var1) {
      this.plugin = var1;
   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void onVillagerAcquireTrade(VillagerAcquireTradeEvent var1) {
      if (var1.getEntity().getRecipeCount() > 0) {
         Player var2 = this.findNearbyPlayerWithWealth(var1.getEntity().getLocation());
         if (var2 != null) {
            if (this.plugin.getGemManager().hasGemType(var2, GemType.WEALTH)) {
               int var3 = this.plugin.getGemManager().getGemTier(var2);
               double var4 = this.plugin.getConfigManager().getVillagerDiscount(var3);
               MerchantRecipe var6 = var1.getRecipe();
               MerchantRecipe var7 = this.applyDiscount(var6, var4);
               var1.setRecipe(var7);
            }
         }
      }
   }

   private Player findNearbyPlayerWithWealth(Location var1) {
      for(Player var3 : var1.getWorld().getPlayers()) {
         if (var3.getLocation().distance(var1) <= (double)10.0F && this.plugin.getGemManager().hasGemType(var3, GemType.WEALTH)) {
            return var3;
         }
      }

      return null;
   }

   private MerchantRecipe applyDiscount(MerchantRecipe var1, double var2) {
      MerchantRecipe var4 = new MerchantRecipe(var1.getResult(), var1.getUses(), var1.getMaxUses(), var1.hasExperienceReward(), var1.getVillagerExperience(), var1.getPriceMultiplier());

      for(ItemStack var6 : var1.getIngredients()) {
         if (var6 != null) {
            ItemStack var7 = var6.clone();
            int var8 = var6.getAmount();
            int var9 = Math.max(1, (int)((double)var8 * ((double)1.0F - var2)));
            var7.setAmount(var9);
            var4.addIngredient(var7);
         }
      }

      return var4;
   }
}
    }

    static final class P_dev_xoperr_blissgems_a {


static public class a implements GemAbilityHandler {
   private final BlissGems a;
   private final Map<UUID, Horse> b = new HashMap();
   private final Map<UUID, BukkitTask> c = new HashMap();
   private final Set<UUID> d = new HashSet();
   private final Set<UUID> e = new HashSet();
   private final Map<UUID, BukkitTask> f = new HashMap();
   private final Map<UUID, Location> g = new HashMap();
   private final Map<UUID, GameMode> h = new HashMap();
   private final Set<UUID> i = new HashSet();
   private final Map<UUID, BukkitTask> j = new HashMap();
   private final Map<UUID, Integer> k = new HashMap();

   public a(BlissGems var1) {
      this.a = var1;
   }

   public void a(Player var1, int var2) {
      if (var2 == 2 && var1.isSneaking()) {
         this.h(var1);
      } else {
         this.g(var1);
      }

   }

   public void onPrimary(Player var1, int var2) {
      this.g(var1);
   }

   public void onSecondary(Player var1, int var2) {
      this.h(var1);
   }

   public void onTertiary(Player var1, int var2) {
      this.a(var1);
   }

   public void onQuaternary(Player var1, int var2) {
      this.b(var1);
   }

   public void a(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         this.j(var1);
      }
   }

   public void b(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         this.l(var1);
      }
   }

   public boolean c(Player var1) {
      return this.e.contains(var1.getUniqueId());
   }

   public Location d(Player var1) {
      return (Location)this.g.get(var1.getUniqueId());
   }

   public boolean e(Player var1) {
      return this.d.contains(var1.getUniqueId());
   }

   public boolean f(Player var1) {
      return this.i.contains(var1.getUniqueId());
   }

   public void g(Player var1) {
      String var2 = "astra-daggers";
      if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         Location var3 = var1.getEyeLocation();
         Vector var4 = var3.getDirection();

         for(int var5 = 0; var5 < 3; ++var5) {
            this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
               Location var5x = var3.clone().add(var4.clone().multiply(1));
               Vector var6 = var4.clone().rotateAroundAxis(new Vector(0, 1, 0), Math.toRadians((double)(var5 * 15 - 15)));
               boolean var7 = false;

               label53: {
                  for(int var8 = 0; var8 < 30; ++var8) {
                     Location var9 = var5x.clone().add(var6.clone().multiply((double)var8 * (double)0.5F));
                     Particle.DustOptions var10 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 1.0F);
                     var1.getWorld().spawnParticle(Particle.DUST, var9, 3, 0.1, 0.1, 0.1, (double)0.0F, var10, true);
                     var1.getWorld().spawnParticle(Particle.REVERSE_PORTAL, var9, 2, 0.05, 0.05, 0.05, 0.01);
                     var1.getWorld().spawnParticle(Particle.WITCH, var9, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);

                     for(Entity var12 : var9.getWorld().getNearbyEntities(var9, (double)0.5F, (double)0.5F, (double)0.5F)) {
                        if (var12 instanceof LivingEntity var13) {
                           if (var12 != var1) {
                              if (!(var12 instanceof Player)) {
                                 break label53;
                              }

                              Player var14 = (Player)var12;
                              if (var1.canSee(var14) && !this.a.getTrustedPlayersManager().isTrusted(var1, var14)) {
                                 break label53;
                              }
                           }
                        }
                     }

                     if (!var9.getBlock().getType().isSolid()) {
                        ;
                     }
                     break;
                  }

                  if (!var7) {
                     this.k.put(var1.getUniqueId(), 0);
                  }

                  return;
               }

               double var19 = this.a.getConfigManager().getAbilityDamage("astra-daggers");
               var13.damage(var19, var1);
               if (var13 instanceof Player && this.a.getAchievementManager() != null) {
                  UUID var16 = var1.getUniqueId();
                  int var17 = (Integer)this.k.getOrDefault(var16, 0) + 1;
                  this.k.put(var16, var17);
                  this.a.getAchievementManager().setProgress(var1, Achievement.PIERCING_PRECISION, var17);
               }

               Particle.DustOptions var20 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 1.5F);
               var13.getWorld().spawnParticle(Particle.DUST, var13.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 80, 0.9, 0.9, 0.9, (double)0.0F, var20, true);
               var13.getWorld().spawnParticle(Particle.REVERSE_PORTAL, var13.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 60, 0.7, 0.7, 0.7);
               var13.getWorld().spawnParticle(Particle.ENCHANTED_HIT, var13.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 40, 0.7, 0.7, 0.7);
               var13.getWorld().spawnParticle(Particle.WITCH, var13.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F);
               var13.getWorld().spawnParticle(Particle.END_ROD, var13.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 25, (double)0.5F, (double)0.5F, (double)0.5F);
               var7 = true;
            }, (long)var5 * 5L);
         }

         var1.playSound(var1.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, 1.5F);
         this.a.getAbilityManager().useAbility(var1, var2);
         String var7 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Astral Daggers");
         if (var7 != null && !var7.isEmpty()) {
            var1.sendMessage(var7);
         }

      }
   }

   public void h(final Player var1) {
      String var2 = "astra-projection";
      if (this.c(var1)) {
         this.i(var1);
      } else if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         UUID var3 = var1.getUniqueId();
         final Location var4 = var1.getLocation().clone();
         int var5 = this.a.getConfig().getInt("abilities.durations.astra-projection", 10);
         final int var6 = var5 * 20;
         final double var7 = this.a.getConfig().getDouble("abilities.astra-projection.radius", (double)128.0F);
         this.g.put(var3, var4);
         this.h.put(var3, var1.getGameMode());
         this.e.add(var3);
         Particle.DustOptions var9 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 1.0F);
         var4.getWorld().spawnParticle(Particle.DUST, var4.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 60, 0.4, (double)1.0F, 0.4, (double)0.0F, var9, true);
         var4.getWorld().spawnParticle(Particle.REVERSE_PORTAL, var4.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 40, 0.4, (double)1.0F, 0.4);
         var4.getWorld().spawnParticle(Particle.SOUL, var4.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 20, 0.3, (double)0.5F, 0.3, 0.02);
         var1.playSound(var4, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1.0F, 1.5F);
         var1.playSound(var4, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.3F);
         var1.setGameMode(GameMode.SPECTATOR);
         BukkitTask var10 = (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var1.isOnline() && !var1.isDead() && a.this.c(var1)) {
                  ++this.a;
                  if (this.a % 4 == 0) {
                     Location var1x = var1.getLocation();
                     if (!var1x.getWorld().equals(var4.getWorld()) || var1x.distance(var4) > var7) {
                        var1.teleport(var4);
                        var1.sendMessage("§d§oYou cannot travel that far from your body!");
                     }
                  }

                  if (this.a % 20 == 0) {
                     Particle.DustOptions var2 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 0.7F);
                     var4.getWorld().spawnParticle(Particle.DUST, var4.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 8, 0.3, 0.8, 0.3, (double)0.0F, var2, true);
                     var4.getWorld().spawnParticle(Particle.SOUL, var4.clone().add((double)0.0F, 1.2, (double)0.0F), 3, 0.2, 0.3, 0.2, 0.01);
                  }

                  int var3 = var6 - this.a;
                  if (var3 == 60) {
                     var1.sendMessage("§d§o3 seconds remaining...");
                  }

                  if (this.a >= var6) {
                     a.this.i(var1);
                     this.cancel();
                  }

               } else {
                  a.this.i(var1);
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L);
         this.f.put(var3, var10);
         this.a.getAbilityManager().useAbility(var1, var2);
         String var11 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Astral Projection");
         if (var11 != null && !var11.isEmpty()) {
            var1.sendMessage(var11);
         }

         var1.sendMessage("§d§oYour soul leaves your body... (" + var5 + "s)");
      }
   }

   public void i(Player var1) {
      UUID var2 = var1.getUniqueId();
      if (this.e.contains(var2)) {
         this.e.remove(var2);
         BukkitTask var3 = (BukkitTask)this.f.remove(var2);
         if (var3 != null) {
            var3.cancel();
         }

         Location var4 = (Location)this.g.remove(var2);
         GameMode var5 = (GameMode)this.h.remove(var2);
         if (var1.isOnline()) {
            if (var4 != null) {
               var1.teleport(var4);
            }

            var1.setGameMode(var5 != null ? var5 : GameMode.SURVIVAL);
            Location var6 = var1.getLocation();
            Particle.DustOptions var7 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 1.0F);
            var6.getWorld().spawnParticle(Particle.DUST, var6.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 60, 0.4, (double)1.0F, 0.4, (double)0.0F, var7, true);
            var6.getWorld().spawnParticle(Particle.REVERSE_PORTAL, var6.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 40, 0.4, (double)1.0F, 0.4);
            var6.getWorld().spawnParticle(Particle.SOUL, var6.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 15, 0.3, (double)0.5F, 0.3, 0.02);
            var1.playSound(var6, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1.0F, 0.8F);
            var1.sendMessage("§d§oYour soul returns to your body.");
         }

      }
   }

   public void j(final Player var1) {
      String var2 = "astra-drift";
      if (this.e(var1)) {
         this.k(var1);
      } else if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         UUID var3 = var1.getUniqueId();
         Location var4 = var1.getLocation();
         int var5 = this.a.getConfig().getInt("abilities.durations.astra-drift", 15);
         final int var6 = var5 * 20;
         Horse var7 = (Horse)var1.getWorld().spawn(var4, Horse.class, (var1x) -> {
            var1x.setTamed(true);
            var1x.setOwner(var1);
            var1x.setAdult();
            var1x.setInvisible(true);
            var1x.setSilent(true);
            var1x.setInvulnerable(true);
            var1x.setAI(false);
            var1x.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
            var1x.getInventory().setSaddle(new ItemStack(Material.SADDLE));
         });
         var7.addPassenger(var1);
         this.b.put(var3, var7);
         this.d.add(var3);
         var1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, var6 + 20, 0, false, false));
         Particle.DustOptions var8 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 1.3F);
         var4.getWorld().spawnParticle(Particle.DUST, var4.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 60, 0.6, (double)1.0F, 0.6, (double)0.0F, var8, true);
         var4.getWorld().spawnParticle(Particle.REVERSE_PORTAL, var4.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, 0.8, (double)0.5F, 0.02);
         var4.getWorld().spawnParticle(Particle.WITCH, var4, 20, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F);
         var1.playSound(var4, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1.0F, 1.5F);
         var1.playSound(var4, Sound.ENTITY_HORSE_SADDLE, 0.5F, 2.0F);
         BukkitTask var9 = (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var1.isOnline() && !var1.isDead() && a.this.e(var1)) {
                  ++this.a;
                  if (this.a % 8 == 0) {
                     Location var1x = var1.getLocation();
                     Particle.DustOptions var2 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 0.5F);
                     var1x.getWorld().spawnParticle(Particle.DUST, var1x.clone().add((double)0.0F, 0.3, (double)0.0F), 2, 0.3, 0.1, 0.3, (double)0.0F, var2, true);
                     var1x.getWorld().spawnParticle(Particle.END_ROD, var1x.clone().add((double)0.0F, (double)0.5F, (double)0.0F), 1, 0.2, 0.1, 0.2, 0.005);
                  }

                  if (this.a >= var6) {
                     a.this.k(var1);
                     this.cancel();
                  }

               } else {
                  a.this.k(var1);
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L);
         this.c.put(var3, var9);
         String var10 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Dimensional Drift");
         if (var10 != null && !var10.isEmpty()) {
            var1.sendMessage(var10);
         }

         var1.sendMessage("§d§oYou fade into the dimensional rift... (" + var5 + "s)");
      }
   }

   public void k(Player var1) {
      UUID var2 = var1.getUniqueId();
      if (this.d.contains(var2)) {
         this.d.remove(var2);
         BukkitTask var3 = (BukkitTask)this.c.remove(var2);
         if (var3 != null) {
            var3.cancel();
         }

         Horse var4 = (Horse)this.b.remove(var2);
         if (var4 != null) {
            var4.removePassenger(var1);
            var4.remove();
         }

         var1.removePotionEffect(PotionEffectType.INVISIBILITY);
         Location var5 = var1.getLocation();
         Particle.DustOptions var6 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 1.3F);
         var5.getWorld().spawnParticle(Particle.DUST, var5.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 60, 0.6, (double)1.0F, 0.6, (double)0.0F, var6, true);
         var5.getWorld().spawnParticle(Particle.REVERSE_PORTAL, var5.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, 0.8, (double)0.5F, 0.02);
         var1.playSound(var5, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1.0F, 0.8F);
         this.a.getAbilityManager().useAbility(var1, "astra-drift");
         var1.sendMessage("§d§oDimensional Drift ended.");
      }
   }

   public void l(final Player var1) {
      String var2 = "astra-void";
      if (this.f(var1)) {
         var1.sendMessage("§c§oDimensional Void is already active!");
      } else if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         UUID var3 = var1.getUniqueId();
         Location var4 = var1.getLocation().clone();
         final double var5 = this.a.getConfig().getDouble("abilities.astra-void.radius", (double)10.0F);
         int var7 = this.a.getConfig().getInt("abilities.durations.astra-void", 8);
         final int var8 = var7 * 20;
         this.i.add(var3);
         ParticleUtils.drawDome(var4, ParticleUtils.ASTRA_PURPLE, 1.2F, var5);
         var1.playSound(var4, Sound.ENTITY_WARDEN_SONIC_BOOM, 0.8F, 1.5F);
         var1.playSound(var4, Sound.BLOCK_BEACON_DEACTIVATE, 1.0F, 0.5F);

         for(Entity var10 : var4.getWorld().getNearbyEntities(var4, var5, var5, var5)) {
            if (var10 instanceof Player && var10 != var1) {
               Player var11 = (Player)var10;
               if (!this.a.getTrustedPlayersManager().isTrusted(var1, var11)) {
                  var11.sendMessage("§4§l§oYour gem abilities have been nullified!");
                  var11.playSound(var11.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0F, 0.3F);
               }
            }
         }

         BukkitTask var12 = (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var1.isOnline() && !var1.isDead() && this.a < var8) {
                  ++this.a;
                  if (this.a % 20 == 0) {
                     Location var1x = var1.getLocation();

                     for(Entity var3 : var1x.getWorld().getNearbyEntities(var1x, var5, var5, var5)) {
                        if (var3 instanceof Player && var3 != var1) {
                           Player var4 = (Player)var3;
                           if (!a.this.a.getTrustedPlayersManager().isTrusted(var1, var4)) {
                              var4.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 30, 1, false, true));
                              var4.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 30, 0, false, true));
                           }
                        }
                     }
                  }

                  if (this.a % 10 == 0) {
                     Location var12 = var1.getLocation();
                     Particle.DustOptions var13 = new Particle.DustOptions(ParticleUtils.ASTRA_PURPLE, 1.0F);

                     for(int var14 = 0; var14 < 32; ++var14) {
                        double var16 = (double)var14 / (double)32.0F * (double)2.0F * Math.PI;
                        double var6 = Math.cos(var16) * var5;
                        double var8x = Math.sin(var16) * var5;
                        var12.getWorld().spawnParticle(Particle.DUST, var12.clone().add(var6, 0.3, var8x), 2, 0.1, 0.1, 0.1, (double)0.0F, var13, true);
                        var12.getWorld().spawnParticle(Particle.REVERSE_PORTAL, var12.clone().add(var6, 0.8, var8x), 1, 0.1, 0.1, 0.1, 0.01);
                     }

                     for(int var15 = 0; var15 < 4; ++var15) {
                        double var17 = (double)var15 * Math.PI / (double)2.0F;
                        double var18 = Math.cos(var17) * var5;
                        double var19 = Math.sin(var17) * var5;

                        for(double var10 = (double)0.0F; var10 < (double)3.0F; var10 += (double)0.5F) {
                           var12.getWorld().spawnParticle(Particle.SOUL, var12.clone().add(var18, var10, var19), 1, 0.05, 0.1, 0.05, 0.01);
                        }
                     }
                  }

                  if (this.a % 40 == 0) {
                     var1.getLocation().getWorld().playSound(var1.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 0.5F, 0.3F);
                  }

               } else {
                  a.this.n(var1);
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L);
         this.j.put(var3, var12);
         this.a.getAbilityManager().useAbility(var1, var2);
         String var13 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Dimensional Void");
         if (var13 != null && !var13.isEmpty()) {
            var1.sendMessage(var13);
         }

         var1.sendMessage("§d§oDimensional Void active for " + var7 + "s. Enemy abilities nullified in " + (int)var5 + " block radius.");
      }
   }

   private void n(Player var1) {
      UUID var2 = var1.getUniqueId();
      this.i.remove(var2);
      BukkitTask var3 = (BukkitTask)this.j.remove(var2);
      if (var3 != null) {
         var3.cancel();
      }

      if (var1.isOnline()) {
         var1.sendMessage("§d§oDimensional Void faded.");
         var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.5F, 1.5F);
      }

   }

   public boolean m(Player var1) {
      for(UUID var3 : this.i) {
         Player var4 = Bukkit.getPlayer(var3);
         if (var4 != null && var4.isOnline() && var4.getWorld().equals(var1.getWorld())) {
            double var5 = this.a.getConfig().getDouble("abilities.astra-void.radius", (double)10.0F);
            if (var4.getLocation().distance(var1.getLocation()) <= var5 && !this.a.getTrustedPlayersManager().isTrusted(var4, var1)) {
               return true;
            }
         }
      }

      return false;
   }

   private LivingEntity b(Player var1, int var2) {
      RayTraceResult var3 = var1.getWorld().rayTraceEntities(var1.getEyeLocation(), var1.getEyeLocation().getDirection(), (double)var2, (var1x) -> var1x instanceof LivingEntity && var1x != var1);
      return var3 != null ? (LivingEntity)var3.getHitEntity() : null;
   }

   private String a(Location var1, Location var2) {
      Vector var3 = var2.toVector().subtract(var1.toVector()).normalize();
      double var4 = Math.atan2(var3.getZ(), var3.getX());
      double var6 = Math.toRadians((double)var1.getYaw());

      double var8;
      for(var8 = var4 - var6; var8 > Math.PI; var8 -= (Math.PI * 2D)) {
      }

      while(var8 < -Math.PI) {
         var8 += (Math.PI * 2D);
      }

      if (var8 > (-Math.PI / 4D) && var8 <= (Math.PI / 4D)) {
         return "→";
      } else if (var8 > (Math.PI / 4D) && var8 <= 2.356194490192345) {
         return "↓";
      } else {
         return var8 > -2.356194490192345 && var8 <= (-Math.PI / 4D) ? "↑" : "←";
      }
   }

   public void cleanup(Player var1) {
      if (this.c(var1)) {
         this.i(var1);
      }

      if (this.e(var1)) {
         this.k(var1);
      }

      if (this.f(var1)) {
         this.n(var1);
      }

   }
}


static public class b implements GemAbilityHandler {
   private final BlissGems a;
   private final Map<UUID, Integer> b = new HashMap();
   private final Map<UUID, BukkitTask> c = new HashMap();
   private final Map<UUID, ItemDisplay> d = new HashMap();
   private final Map<UUID, Location> e = new HashMap();
   private final Map<UUID, BukkitTask> f = new HashMap();
   private final Set<UUID> g = new HashSet();
   private final Map<UUID, BukkitTask> h = new HashMap();
   private final Map<UUID, Map<Location, Material>> i = new HashMap();
   private final Map<UUID, Map<Location, Material>> j = new HashMap();
   private final Set<UUID> k = new HashSet();
   private final Map<UUID, BukkitTask> l = new HashMap();
   private static final int m = 100;
   private static final int n = 300;
   private static final Material[] o;
   private final Random p = new Random();

   private static boolean a(Material var0) {
      String var1 = var0.name();
      return var1.contains("CHEST") || var1.contains("SHULKER_BOX") || var1.contains("BARREL") || var1.contains("HOPPER") || var1.contains("FURNACE") || var1.contains("BLAST_FURNACE") || var1.contains("SMOKER") || var1.contains("DROPPER") || var1.contains("DISPENSER") || var1.contains("BREWING_STAND") || var0 == Material.JUKEBOX || var0 == Material.LECTERN || var0 == Material.CHISELED_BOOKSHELF;
   }

   public b(BlissGems var1) {
      this.a = var1;
   }

   public boolean a(Player var1) {
      return this.b.containsKey(var1.getUniqueId());
   }

   public int b(Player var1) {
      return (Integer)this.b.getOrDefault(var1.getUniqueId(), 0);
   }

   public boolean c(Player var1) {
      return this.g.contains(var1.getUniqueId());
   }

   public boolean a(Location var1) {
      for(Map var3 : this.i.values()) {
         if (var3.containsKey(var1)) {
            return true;
         }
      }

      for(Location var5 : this.e.values()) {
         if (var5 != null && var5.getWorld().equals(var1.getWorld()) && var5.getBlockX() == var1.getBlockX() && var5.getBlockY() == var1.getBlockY() && var5.getBlockZ() == var1.getBlockZ()) {
            return true;
         }
      }

      return false;
   }

   public void a(Player var1, int var2) {
      if (var2 == 2 && var1.isSneaking()) {
         this.f(var1);
      } else {
         this.d(var1);
      }

   }

   public void onPrimary(Player var1, int var2) {
      this.d(var1);
   }

   public void onSecondary(Player var1, int var2) {
      this.f(var1);
   }

   public void onTertiary(Player var1, int var2) {
      this.h(var1);
   }

   public void onQuaternary(Player var1, int var2) {
      this.i(var1);
   }

   public void d(final Player var1) {
      String var2 = "fire-fireball";
      if (this.a(var1)) {
         this.j(var1);
      } else if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         final UUID var3 = var1.getUniqueId();
         this.b.put(var3, 0);
         var1.playSound(var1.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 1.0F, 0.5F);
         var1.sendMessage("§6§oCharging fireball... Right-click again to fire!");
         this.l(var1);
         this.k(var1);
         BukkitTask var4 = (new BukkitRunnable() {
            int a = 0;
            boolean b = false;
            boolean c = false;

            public void run() {
               if (var1.isOnline() && !var1.isDead() && b.this.b.containsKey(var3)) {
                  ++this.a;
                  int var1x = (Integer)b.this.b.getOrDefault(var3, 0);
                  int var3x = Math.max(1, b.this.a.getConfig().getInt("abilities.fire-fireball.charge-duration-ticks", 300));
                  int var2;
                  if (!this.c) {
                     var2 = Math.min(this.a * 100 / var3x, 100);
                     if (var2 >= 100) {
                        this.c = true;
                        var2 = 100;
                     }
                  } else {
                     Block var4 = var1.getLocation().subtract((double)0.0F, (double)1.0F, (double)0.0F).getBlock();
                     if (var4.getType() != Material.OBSIDIAN && var4.getType() != Material.CRYING_OBSIDIAN) {
                        var2 = var1x - 1;
                     } else {
                        var2 = var1x;
                     }

                     if (var2 <= 0) {
                        var1.sendMessage("§c§oFireball charge fizzled!");
                        var1.playSound(var1.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F);
                        b.this.e(var1);
                        this.cancel();
                        return;
                     }
                  }

                  b.this.b.put(var3, var2);
                  b.this.c(var1, var2);
                  b.this.b(var1, var2);
                  if (var2 == 25 || var2 == 50 || var2 == 75) {
                     var1.playSound(var1.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 0.5F, 1.0F + (float)var2 / 100.0F);
                  }

                  if (var2 >= 100 && !this.b) {
                     var1.playSound(var1.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 1.0F, 1.5F);
                     var1.sendMessage("§6§lFully charged! §eRight-click to fire!");
                     this.b = true;
                  }

               } else {
                  b.this.e(var1);
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L);
         this.c.put(var3, var4);
      }
   }

   private void b(Player var1, int var2) {
      int var3 = var2 / 5;
      StringBuilder var4 = new StringBuilder("§6Fireball: §c");

      for(int var5 = 0; var5 < 20; ++var5) {
         if (var5 < var3) {
            var4.append("█");
         } else {
            var4.append("§8█");
         }
      }

      var4.append(" §e").append(var2).append("%");
      var1.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(var4.toString()));
   }

   private void j(Player var1) {
      UUID var2 = var1.getUniqueId();
      int var3 = (Integer)this.b.getOrDefault(var2, 0);
      BukkitTask var4 = (BukkitTask)this.c.remove(var2);
      if (var4 != null) {
         var4.cancel();
      }

      this.b.remove(var2);
      this.a(var2);
      if (var3 < 10) {
         var1.sendMessage("§c§oNot enough charge!");
      } else {
         double var5 = this.a.getConfig().getDouble("abilities.damage.fire-fireball", (double)9.0F);
         double var7 = (double)var3 / (double)100.0F;
         double var9 = this.a.getConfig().getDouble("abilities.fire-fireball.yield-base", (double)1.5F);
         double var11 = this.a.getConfig().getDouble("abilities.fire-fireball.yield-per-percent", 0.025);
         float var13 = (float)(var9 + var11 * (double)var3);
         Location var14 = var1.getEyeLocation();
         Vector var15 = var14.getDirection();
         Fireball var16 = (Fireball)var1.getWorld().spawn(var14.clone().add(var15.clone().multiply((double)1.5F)), Fireball.class);
         var16.setShooter(var1);
         var16.setVelocity(var15.multiply((double)1.5F + (double)var3 / (double)100.0F));
         var16.setYield(var13);
         var16.setIsIncendiary(true);
         int var17 = 20 + var3 / 2;
         var1.playSound(var1.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 0.5F + (float)var3 / 200.0F);
         var1.spawnParticle(Particle.FLAME, var14, var17, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
         this.a.getAbilityManager().useAbility(var1, "fire-fireball");
         var1.sendMessage("§6§oFired at " + var3 + "% power!");
      }
   }

   public void e(Player var1) {
      UUID var2 = var1.getUniqueId();
      BukkitTask var3 = (BukkitTask)this.c.remove(var2);
      if (var3 != null) {
         var3.cancel();
      }

      this.b.remove(var2);
      this.a(var2);
   }

   private void k(Player var1) {
      Location var2 = var1.getLocation();
      Particle.DustOptions var3 = new Particle.DustOptions(ParticleUtils.FIRE_ORANGE, 1.5F);

      for(int var4 = 0; var4 < 24; ++var4) {
         double var5 = (double)var4 / (double)24.0F * (double)2.0F * Math.PI;
         double var7 = Math.cos(var5) * 1.4;
         double var9 = Math.sin(var5) * 1.4;
         var1.getWorld().spawnParticle(Particle.FLAME, var2.clone().add(var7, 0.2, var9), 2, 0.05, 0.05, 0.05, 0.02);
         var1.getWorld().spawnParticle(Particle.DUST, var2.clone().add(var7, 0.4, var9), 1, 0.05, 0.05, 0.05, (double)0.0F, var3, true);
      }

      var1.getWorld().spawnParticle(Particle.FLAME, var2.clone().add((double)0.0F, 0.6, (double)0.0F), 25, 0.4, 0.4, 0.4, 0.06);
      var1.getWorld().spawnParticle(Particle.LAVA, var2.clone().add((double)0.0F, 0.3, (double)0.0F), 6, (double)0.5F, 0.1, (double)0.5F, (double)0.0F);
   }

   private void l(Player var1) {
      try {
         UUID var2 = var1.getUniqueId();
         this.a(var2);
         Location var3 = this.m(var1);
         ItemDisplay var4 = (ItemDisplay)var1.getWorld().spawnEntity(var3, EntityType.ITEM_DISPLAY);
         var4.setItemStack(new ItemStack(Material.FIRE_CHARGE));
         var4.setBrightness(new Display.Brightness(15, 15));
         var4.setTeleportDuration(2);
         this.a(var4, 0.3F);
         this.d.put(var2, var4);
      } catch (Throwable var5) {
         this.a.getLogger().warning("Failed to spawn fireball charge display: " + var5.getMessage());
      }

   }

   private void c(Player var1, int var2) {
      ItemDisplay var3 = (ItemDisplay)this.d.get(var1.getUniqueId());
      if (var3 != null && var3.isValid()) {
         float var4 = 0.3F + (float)Math.max(0, Math.min(var2, 100)) / 100.0F * 0.7F;
         this.a(var3, var4);
         var3.teleport(this.m(var1));
      }
   }

   private void a(UUID var1) {
      ItemDisplay var2 = (ItemDisplay)this.d.remove(var1);
      if (var2 != null && var2.isValid()) {
         var2.remove();
      }

   }

   private Location m(Player var1) {
      return var1.getLocation().add((double)0.0F, (double)2.5F, (double)0.0F);
   }

   private void a(ItemDisplay var1, float var2) {
      Transformation var3 = var1.getTransformation();
      var1.setTransformation(new Transformation(var3.getTranslation(), var3.getLeftRotation(), new Vector3f(var2, var2, var2), var3.getRightRotation()));
   }

   public void f(final Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "fire-campfire";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            final UUID var3 = var1.getUniqueId();
            if (this.e.containsKey(var3)) {
               this.g(var1);
            }

            Block var4 = var1.getLocation().getBlock();
            if (var4.getType() != Material.AIR) {
               var4 = var4.getRelative(0, 1, 0);
            }

            if (var4.getType() != Material.AIR && var4.getType() != Material.CAVE_AIR) {
               var1.sendMessage("§c§oCannot place campfire here!");
            } else {
               Material var5 = var4.getType();
               var4.setType(Material.CAMPFIRE);
               final Location var6 = var4.getLocation().clone();
               this.e.put(var3, var6);
               final double var7 = this.a.getConfig().getDouble("abilities.fire-campfire.radius", (double)5.0F);
               final double var9 = this.a.getConfig().getDouble("abilities.damage.fire-campfire", (double)2.0F);
               final int var11 = this.a.getConfig().getInt("abilities.fire-campfire.burn-duration", 3);
               final int var12 = this.a.getConfig().getInt("abilities.durations.fire-campfire", 60) * 20;
               var1.playSound(var6, Sound.BLOCK_CAMPFIRE_CRACKLE, 1.0F, 1.0F);
               var1.sendMessage("§6§oPlaced Campfire! Heals you and burns enemies for 1 minute.");
               BukkitTask var13 = (new BukkitRunnable() {
                  int a = 0;

                  public void run() {
                     Block var1x = var6.getBlock();
                     if (var1x.getType() != Material.CAMPFIRE) {
                        b.this.e.remove(var3);
                        b.this.f.remove(var3);
                        var1.sendMessage("§6§oCampfire was destroyed!");
                        this.cancel();
                     } else if (this.a >= var12) {
                        b.this.b(var6);
                        b.this.e.remove(var3);
                        b.this.f.remove(var3);
                        var1.sendMessage("§6§oCampfire expired!");
                        this.cancel();
                     } else {
                        if (this.a % 20 == 0) {
                           for(Entity var3x : var6.getWorld().getNearbyEntities(var6, var7, var7, var7)) {
                              if (var3x instanceof Player) {
                                 Player var4 = (Player)var3x;
                                 if (var4.equals(var1) || b.this.a.getTrustedPlayersManager().isTrusted(var1, var4)) {
                                    var4.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 3));
                                 }
                              }
                           }

                           for(Entity var13 : var6.getWorld().getNearbyEntities(var6, var7, var7, var7)) {
                              if (var13 instanceof LivingEntity && var13 != var1) {
                                 LivingEntity var15 = (LivingEntity)var13;
                                 if (var13 instanceof Player) {
                                    Player var5 = (Player)var13;
                                    if (b.this.a.getTrustedPlayersManager().isTrusted(var1, var5)) {
                                       continue;
                                    }
                                 }

                                 var15.damage(var9, var1);
                                 var15.setFireTicks(var11 * 20);
                                 var15.getWorld().spawnParticle(Particle.FLAME, var15.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 10, 0.3, (double)0.5F, 0.3, 0.02);
                              }
                           }

                           var6.getWorld().playSound(var6, Sound.BLOCK_CAMPFIRE_CRACKLE, 0.5F, 1.0F);
                        }

                        if (this.a % 5 == 0) {
                           byte var12x = 48;
                           Particle.DustOptions var14 = new Particle.DustOptions(ParticleUtils.FIRE_ORANGE, 1.0F);

                           for(int var16 = 0; var16 < var12x; ++var16) {
                              double var17 = (double)var16 / (double)var12x * (double)2.0F * Math.PI;
                              double var7x = Math.cos(var17) * var7;
                              double var9x = Math.sin(var17) * var7;
                              var6.getWorld().spawnParticle(Particle.DUST, var6.clone().add(var7x, 0.3, var9x), 3, 0.1, 0.1, 0.1, (double)0.0F, var14, true);
                              var6.getWorld().spawnParticle(Particle.FLAME, var6.clone().add(var7x, 0.8, var9x), 2, 0.1, 0.1, 0.1, 0.01);
                              if (var16 % 4 == 0) {
                                 var6.getWorld().spawnParticle(Particle.LAVA, var6.clone().add(var7x, 1.2, var9x), 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
                              }
                           }

                           var6.getWorld().spawnParticle(Particle.FLAME, var6.clone().add((double)0.5F, (double)1.0F, (double)0.5F), 15, 0.3, (double)0.5F, 0.3, 0.02);
                           var6.getWorld().spawnParticle(Particle.LAVA, var6.clone().add((double)0.5F, (double)0.5F, (double)0.5F), 8, (double)0.5F, 0.2, (double)0.5F, (double)0.0F);
                        }

                        ++this.a;
                     }
                  }
               }).runTaskTimer(this.a, 0L, 1L);
               this.f.put(var3, var13);
               this.a.getAbilityManager().useAbility(var1, var2);
            }
         }
      }
   }

   private void b(Location var1) {
      Block var2 = var1.getBlock();
      if (var2.getType() == Material.CAMPFIRE) {
         var2.setType(Material.AIR);
         var1.getWorld().playSound(var1, Sound.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F);
      }

   }

   public void g(Player var1) {
      UUID var2 = var1.getUniqueId();
      BukkitTask var3 = (BukkitTask)this.f.remove(var2);
      if (var3 != null) {
         var3.cancel();
      }

      Location var4 = (Location)this.e.remove(var2);
      if (var4 != null) {
         this.b(var4);
      }

   }

   public void h(final Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "fire-crisp";
         if (this.c(var1)) {
            var1.sendMessage("§c§oCrisp is already active!");
         } else if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            final UUID var3 = var1.getUniqueId();
            Location var4 = var1.getLocation().clone();
            final int var5 = this.a.getConfig().getInt("abilities.fire-crisp.radius", 10);
            int var6 = this.a.getConfig().getInt("abilities.durations.fire-crisp", 15);
            final int var7 = var6 * 20;
            this.g.add(var3);
            var1.playSound(var4, Sound.BLOCK_LAVA_AMBIENT, 1.5F, 0.5F);
            var1.playSound(var4, Sound.ENTITY_BLAZE_AMBIENT, 1.0F, 0.7F);
            var1.sendMessage("§6§lCrisp! §eEvaporating water and scorching the earth for " + var6 + "s!");
            this.i.put(var3, new HashMap());
            this.j.put(var3, new HashMap());
            this.a(var4, var5, var3);

            for(int var8 = 0; var8 < 36; ++var8) {
               double var9 = (double)var8 / (double)36.0F * (double)2.0F * Math.PI;

               for(double var11 = (double)0.0F; var11 <= (double)var5; var11 += (double)0.5F) {
                  double var13 = Math.cos(var9) * var11;
                  double var15 = Math.sin(var9) * var11;
                  var4.getWorld().spawnParticle(Particle.FLAME, var4.clone().add(var13, 0.3, var15), 1, 0.05, 0.1, 0.05, 0.02);
               }
            }

            Particle.DustOptions var17 = new Particle.DustOptions(ParticleUtils.FIRE_ORANGE, 1.5F);

            for(int var18 = 0; var18 < 48; ++var18) {
               double var10 = (double)var18 / (double)48.0F * (double)2.0F * Math.PI;
               double var12 = Math.cos(var10) * (double)var5;
               double var14 = Math.sin(var10) * (double)var5;
               var4.getWorld().spawnParticle(Particle.DUST, var4.clone().add(var12, (double)0.5F, var14), 5, 0.1, 0.3, 0.1, (double)0.0F, var17, true);
            }

            BukkitTask var19 = (new BukkitRunnable() {
               int a = 0;

               public void run() {
                  if (var1.isOnline() && !var1.isDead() && this.a < var7) {
                     ++this.a;
                     if (this.a % 10 == 0) {
                        Location var1x = var1.getLocation();
                        b.this.b(var1x, var5, var3);
                        if (this.a % 20 == 0) {
                           for(int var2 = 0; var2 < 8; ++var2) {
                              double var3x = b.this.p.nextDouble() * (double)2.0F * Math.PI;
                              double var5x = b.this.p.nextDouble() * (double)var5;
                              double var7x = Math.cos(var3x) * var5x;
                              double var9 = Math.sin(var3x) * var5x;
                              var1x.getWorld().spawnParticle(Particle.LAVA, var1x.clone().add(var7x, 0.2, var9), 1, 0.1, 0.1, 0.1, (double)0.0F);
                              var1x.getWorld().spawnParticle(Particle.FLAME, var1x.clone().add(var7x, 0.3, var9), 1, 0.1, 0.1, 0.1, 0.01);
                           }

                           var1x.getWorld().playSound(var1x, Sound.BLOCK_FIRE_AMBIENT, 0.4F, 0.8F);
                        }
                     }

                  } else {
                     b.this.n(var1);
                     this.cancel();
                  }
               }
            }).runTaskTimer(this.a, 0L, 1L);
            this.h.put(var3, var19);
            this.a.getAbilityManager().useAbility(var1, var2);
         }
      }
   }

   private void a(Location var1, int var2, UUID var3) {
      this.b(var1, var2, var3);
      Map var4 = (Map)this.i.get(var3);

      for(int var5 = -var2; var5 <= var2; ++var5) {
         for(int var6 = -var2; var6 <= var2; ++var6) {
            if (var5 * var5 + var6 * var6 <= var2 * var2) {
               Location var7 = var1.clone().add((double)var5, (double)0.0F, (double)var6);
               Block var8 = var7.getWorld().getHighestBlockAt(var7);
               if (!a(var8.getType()) && var8.getType().isSolid() && !var8.getType().name().startsWith("NETHER") && var8.getType() != Material.MAGMA_BLOCK && var8.getType() != Material.SOUL_SAND) {
                  Location var9 = var8.getLocation();
                  if (var4 != null) {
                     var4.put(var9, var8.getType());
                  }

                  Material var10 = o[this.p.nextInt(o.length)];
                  var8.setType(var10);
               }
            }
         }
      }

   }

   private void b(Location var1, int var2, UUID var3) {
      Map var4 = (Map)this.j.get(var3);

      for(int var5 = -var2; var5 <= var2; ++var5) {
         for(int var6 = -var2; var6 <= var2; ++var6) {
            for(int var7 = -var2; var7 <= var2; ++var7) {
               if (var5 * var5 + var6 * var6 + var7 * var7 <= var2 * var2) {
                  Block var8 = var1.clone().add((double)var5, (double)var6, (double)var7).getBlock();
                  Material var9 = var8.getType();
                  if (var9 == Material.WATER || var9 == Material.BUBBLE_COLUMN || var9 == Material.KELP || var9 == Material.KELP_PLANT || var9 == Material.SEAGRASS || var9 == Material.TALL_SEAGRASS) {
                     if (var4 != null) {
                        var4.putIfAbsent(var8.getLocation(), var9);
                     }

                     var1.getWorld().spawnParticle(Particle.CLOUD, var8.getLocation().add((double)0.5F, (double)0.5F, (double)0.5F), 3, 0.2, 0.2, 0.2, 0.02);
                     var8.setType(Material.AIR);
                  }
               }
            }
         }
      }

   }

   private void n(Player var1) {
      UUID var2 = var1.getUniqueId();
      boolean var3 = this.g.remove(var2);
      BukkitTask var4 = (BukkitTask)this.h.remove(var2);
      if (var4 != null) {
         var4.cancel();
      }

      Map var5 = (Map)this.i.remove(var2);
      if (var5 != null) {
         for(Map.Entry var7 : var5.entrySet()) {
            Block var8 = ((Location)var7.getKey()).getBlock();
            String var9 = var8.getType().name();
            if (var9.startsWith("NETHER") || var8.getType() == Material.MAGMA_BLOCK || var8.getType() == Material.SOUL_SAND) {
               var8.setType((Material)var7.getValue());
            }
         }
      }

      Map var10 = (Map)this.j.remove(var2);
      if (var10 != null) {
         for(Map.Entry var12 : var10.entrySet()) {
            Block var13 = ((Location)var12.getKey()).getBlock();
            if (var13.getType() == Material.AIR) {
               var13.setType((Material)var12.getValue());
            }
         }
      }

      if (var3 && var1.isOnline()) {
         var1.sendMessage("§6§oCrisp faded. Terrain restored.");
      }

   }

   public void i(final Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "fire-meteor-shower";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            final UUID var3 = var1.getUniqueId();
            Location var4 = this.d(var1, 50);
            if (var4 == null) {
               var1.sendMessage("§c§oNo valid target area found!");
            } else {
               int var5 = this.a.getConfig().getInt("abilities.durations.fire-meteor-shower", 8);
               final int var6 = var5 * 20;
               final double var7 = this.a.getConfig().getDouble("abilities.fire-meteor-shower.radius", (double)8.0F);
               final double var9 = this.a.getConfig().getDouble("abilities.damage.fire-meteor-shower", (double)5.0F);
               final int var11 = this.a.getConfig().getInt("abilities.fire-meteor-shower.interval-ticks", 10);
               this.k.add(var3);
               var1.playSound(var1.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 0.3F);
               var1.sendMessage("§6§lMeteor Shower! §eFire rains down for " + var5 + "s!");
               Particle.DustOptions var12 = new Particle.DustOptions(ParticleUtils.FIRE_ORANGE, 1.2F);

               for(int var13 = 0; var13 < 48; ++var13) {
                  double var14 = (double)var13 / (double)48.0F * (double)2.0F * Math.PI;
                  double var16 = Math.cos(var14) * var7;
                  double var18 = Math.sin(var14) * var7;
                  var4.getWorld().spawnParticle(Particle.DUST, var4.clone().add(var16, (double)0.5F, var18), 3, 0.1, 0.2, 0.1, (double)0.0F, var12, true);
               }

               final Location var20 = var4.clone();
               BukkitTask var21 = (new BukkitRunnable() {
                  int a = 0;

                  public void run() {
                     if (var1.isOnline() && !var1.isDead() && this.a < var6) {
                        ++this.a;
                        if (this.a % var11 == 0) {
                           double var1x = b.this.p.nextDouble() * (double)2.0F * Math.PI;
                           double var3x = Math.sqrt(b.this.p.nextDouble()) * var7;
                           double var5 = Math.cos(var1x) * var3x;
                           double var7x = Math.sin(var1x) * var3x;
                           int var9x = 20 + b.this.p.nextInt(10);
                           Location var10 = var20.clone().add(var5, (double)var9x, var7x);
                           Location var11x = var20.clone().add(var5, (double)0.0F, var7x);
                           Block var12 = var20.getWorld().getHighestBlockAt(var11x);
                           var11x.setY((double)var12.getY() + (double)0.5F);
                           Vector var13 = var11x.toVector().subtract(var10.toVector()).normalize();
                           b.this.a.getServer().getScheduler().runTaskLater(b.this.a, () -> {
                              if (var1.isOnline()) {
                                 double var8 = var10.distance(var11x);

                                 for(double var10x = (double)0.0F; var10x < var8; var10x += 0.8) {
                                    Location var12 = var10.clone().add(var13.clone().multiply(var10x));
                                    var20.getWorld().spawnParticle(Particle.FLAME, var12, 30, 0.3, 0.3, 0.3, 0.03);
                                    var20.getWorld().spawnParticle(Particle.LAVA, var12, 10, 0.15, 0.15, 0.15, (double)0.0F);
                                 }

                                 var20.getWorld().spawnParticle(Particle.FLAME, var11x, 400, (double)3.0F, (double)1.5F, (double)3.0F, 0.1);
                                 var20.getWorld().spawnParticle(Particle.LAVA, var11x, 150, (double)1.5F, 0.6, (double)1.5F, (double)0.0F);
                                 var20.getWorld().spawnParticle(Particle.EXPLOSION, var11x, 30, 0.9, 0.3, 0.9, (double)0.0F);
                                 var20.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, var11x, 100, (double)1.5F, 0.9, (double)1.5F, 0.05);
                                 var20.getWorld().playSound(var11x, Sound.ENTITY_GENERIC_EXPLODE, 0.6F, 1.5F);
                                 var20.getWorld().playSound(var11x, Sound.BLOCK_LAVA_POP, 1.0F, 0.8F);
                                 double var16 = (double)2.5F;

                                 for(Entity var13x : var20.getWorld().getNearbyEntities(var11x, var16, var16, var16)) {
                                    if (var13x instanceof LivingEntity && var13x != var1) {
                                       LivingEntity var14 = (LivingEntity)var13x;
                                       if (var13x instanceof Player) {
                                          Player var15 = (Player)var13x;
                                          if (b.this.a.getTrustedPlayersManager().isTrusted(var1, var15)) {
                                             continue;
                                          }
                                       }

                                       var14.damage(var9, var1);
                                       var14.setFireTicks(60);
                                       var14.getWorld().spawnParticle(Particle.FLAME, var14.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 150, 1.2, 1.8, 1.2, 0.05);
                                    }
                                 }

                                 float var18 = (float)b.this.a.getConfig().getDouble("abilities.fire-meteor-shower.yield", (double)2.0F);
                                 boolean var19 = b.this.a.getConfig().getBoolean("abilities.fire-meteor-shower.break-blocks", true);
                                 if (var18 > 0.0F) {
                                    var11x.getWorld().createExplosion(var11x, var18, true, var19, var1);
                                 }

                                 Block var20x = var11x.getBlock();
                                 if (var20x.getType() == Material.AIR || var20x.getType() == Material.CAVE_AIR) {
                                    Block var21 = var20x.getRelative(0, -1, 0);
                                    if (var21.getType().isSolid()) {
                                       var20x.setType(Material.FIRE);
                                    }
                                 }

                              }
                           }, 0L);
                        }

                        if (this.a % 20 == 0) {
                           Particle.DustOptions var14 = new Particle.DustOptions(ParticleUtils.FIRE_ORANGE, 0.8F);

                           for(int var2 = 0; var2 < 24; ++var2) {
                              double var15 = (double)var2 / (double)24.0F * (double)2.0F * Math.PI;
                              double var16 = Math.cos(var15) * var7;
                              double var17 = Math.sin(var15) * var7;
                              var20.getWorld().spawnParticle(Particle.DUST, var20.clone().add(var16, 0.3, var17), 20, 0.3, 0.3, 0.3, (double)0.0F, var14, true);
                           }
                        }

                     } else {
                        b.this.k.remove(var3);
                        b.this.l.remove(var3);
                        if (var1.isOnline()) {
                           var1.sendMessage("§6§oMeteor Shower ended.");
                        }

                        this.cancel();
                     }
                  }
               }).runTaskTimer(this.a, 0L, 1L);
               this.l.put(var3, var21);
               this.a.getAbilityManager().useAbility(var1, var2);
            }
         }
      }
   }

   private Location d(Player var1, int var2) {
      RayTraceResult var3 = var1.getWorld().rayTraceBlocks(var1.getEyeLocation(), var1.getEyeLocation().getDirection(), (double)var2);
      if (var3 != null && var3.getHitBlock() != null) {
         return var3.getHitBlock().getLocation().add((double)0.5F, (double)1.0F, (double)0.5F);
      } else {
         Location var4 = var1.getEyeLocation().add(var1.getEyeLocation().getDirection().multiply(20));
         Block var5 = var1.getWorld().getHighestBlockAt(var4);
         return var5.getLocation().add((double)0.5F, (double)1.0F, (double)0.5F);
      }
   }

   public void cleanup(Player var1) {
      this.e(var1);
      this.g(var1);
      this.n(var1);
      UUID var2 = var1.getUniqueId();
      this.i.remove(var2);
      this.j.remove(var2);
      this.k.remove(var2);
      BukkitTask var3 = (BukkitTask)this.l.remove(var2);
      if (var3 != null) {
         var3.cancel();
      }

   }

   static {
      o = new Material[]{Material.NETHERRACK, Material.NETHER_BRICKS, Material.NETHER_BRICK_FENCE, Material.MAGMA_BLOCK, Material.SOUL_SAND};
   }
}


static public class c implements GemAbilityHandler {
   private final BlissGems a;
   private static final Set<UUID> b = new HashSet();
   private final Map<UUID, Integer> c = new HashMap();
   private final Map<UUID, BukkitTask> d = new HashMap();
   private final Map<UUID, Long> e = new HashMap();
   private static final int f = 100;
   private static final int g = 300;

   public c(BlissGems var1) {
      this.a = var1;
   }

   public static boolean a(UUID var0) {
      return b.contains(var0);
   }

   public boolean a(Player var1) {
      return this.c.containsKey(var1.getUniqueId());
   }

   public int b(Player var1) {
      return (Integer)this.c.getOrDefault(var1.getUniqueId(), 0);
   }

   public void a(Player var1, int var2) {
      if (var2 == 2 && var1.isSneaking()) {
         this.e(var1);
      } else {
         this.c(var1);
      }

   }

   public void onPrimary(Player var1, int var2) {
      this.c(var1);
   }

   public void onSecondary(Player var1, int var2) {
      this.e(var1);
   }

   public void onTertiary(Player var1, int var2) {
      this.f(var1);
   }

   public void onQuaternary(Player var1, int var2) {
      this.g(var1);
   }

   public void c(final Player var1) {
      String var2 = "flux-beam";
      if (this.a(var1)) {
         long var5 = (Long)this.e.getOrDefault(var1.getUniqueId(), 0L);
         if (System.currentTimeMillis() - var5 >= 100L) {
            this.h(var1);
         }
      } else if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         final UUID var3 = var1.getUniqueId();
         this.c.put(var3, 0);
         this.e.put(var3, System.currentTimeMillis());
         var1.playSound(var1.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.5F, 1.5F);
         var1.sendMessage("§b⚡ §oCharging Flux Beam... Right-click again to fire!");
         BukkitTask var4 = (new BukkitRunnable() {
            int a = 0;
            boolean b = false;

            public void run() {
               if (var1.isOnline() && !var1.isDead() && c.this.c.containsKey(var3)) {
                  ++this.a;
                  int var1x = Math.min(this.a * 100 / 300, 100);
                  c.this.c.put(var3, var1x);
                  c.this.b(var1, var1x);
                  Location var2 = var1.getLocation();
                  Particle.DustOptions var3x = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 1.0F);
                  double var4 = 1.4;

                  for(int var6 = 0; var6 < 8; ++var6) {
                     double var7 = (double)var6 / (double)8.0F * (double)2.0F * Math.PI + (double)this.a * 0.1;
                     double var9 = Math.cos(var7) * var4;
                     double var11 = Math.sin(var7) * var4;
                     var1.getWorld().spawnParticle(Particle.DUST, var2.clone().add(var9, 0.2, var11), 3, 0.1, 0.1, 0.1, (double)0.0F, var3x, true);
                     var1.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var2.clone().add(var9 * 0.8, 0.8, var11 * 0.8), 2, 0.05, 0.05, 0.05, 0.02);
                  }

                  double var19 = (double)this.a * 0.3 % (Math.PI * 2D);
                  double var8 = (double)1.0F;

                  for(int var10 = 0; var10 < 3; ++var10) {
                     double var22 = (double)var10 * 0.4;
                     double var13 = Math.cos(var19 + (double)var10) * var8;
                     double var15 = Math.sin(var19 + (double)var10) * var8;
                     var1.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var2.clone().add(var13, var22, var15), 1, 0.05, 0.05, 0.05, (double)0.0F);
                  }

                  if (this.a % 5 == 0) {
                     int var20 = 5 + var1x / 15;
                     Particle.DustOptions var23 = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 0.9F);
                     var1.getWorld().spawnParticle(Particle.DUST, var2.clone().add((double)0.0F, 0.8, (double)0.0F), var20 * 2, 1.2, (double)0.5F, 1.2, (double)0.0F, var23, true);
                     var1.getWorld().spawnParticle(Particle.SMOKE, var2.clone().add((double)0.0F, (double)0.5F, (double)0.0F), var20, (double)1.5F, 0.4, (double)1.5F, 0.02);

                     for(int var12 = 0; var12 < 8; ++var12) {
                        double var24 = (double)var12 / (double)8.0F * (double)2.0F * Math.PI;
                        double var25 = Math.cos(var24) * 1.8;
                        double var17 = Math.sin(var24) * 1.8;
                        var1.getWorld().spawnParticle(Particle.END_ROD, var1.getLocation().add(var25, 0.1, var17), 2, 0.1, 0.1, 0.1, (double)0.0F);
                     }
                  }

                  if (var1x == 25 || var1x == 50 || var1x == 75) {
                     var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 0.3F, 1.0F + (float)var1x / 100.0F);
                  }

                  if (var1x >= 100 && !this.b) {
                     var1.playSound(var1.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 2.0F);
                     var1.sendMessage("§b⚡§l Fully charged! §eRight-click to fire!");
                     this.b = true;
                  }

                  if (var1x >= 100 && this.a % 3 == 0) {
                     Particle.DustOptions var21 = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 1.0F);
                     var1.getWorld().spawnParticle(Particle.DUST, var2.clone().add((double)0.0F, 0.6, (double)0.0F), 30, 1.8, 0.4, 1.8, (double)0.0F, var21, true);
                     var1.getWorld().spawnParticle(Particle.SMOKE, var2.clone().add((double)0.0F, 0.3, (double)0.0F), 20, (double)1.5F, 0.3, (double)1.5F, 0.08);
                     var1.getWorld().spawnParticle(Particle.END_ROD, var1.getLocation().add((double)0.0F, 0.1, (double)0.0F), 15, (double)2.0F, 0.1, (double)2.0F, (double)0.0F);
                  }

               } else {
                  c.this.d(var1);
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, 1L);
         this.d.put(var3, var4);
      }
   }

   private void b(Player var1, int var2) {
      int var3 = var2 / 5;
      StringBuilder var4 = new StringBuilder("§b⚡ Flux Beam: §e");

      for(int var5 = 0; var5 < 20; ++var5) {
         if (var5 < var3) {
            var4.append("█");
         } else {
            var4.append("§8█");
         }
      }

      var4.append(" §b").append(var2).append("%");
      var1.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(var4.toString()));
   }

   private void h(Player var1) {
      UUID var2 = var1.getUniqueId();
      int var3 = (Integer)this.c.getOrDefault(var2, 0);
      if (var3 >= 100 && this.a.getAchievementManager() != null) {
         this.a.getAchievementManager().unlock(var1, Achievement.OVERCHARGED);
      }

      BukkitTask var4 = (BukkitTask)this.d.remove(var2);
      if (var4 != null) {
         var4.cancel();
      }

      this.c.remove(var2);
      this.e.remove(var2);
      if (var3 < 10) {
         var1.sendMessage("§c§oNot enough charge!");
      } else {
         LivingEntity var5 = this.e(var1, 30);
         if (var5 == null) {
            this.a.getAbilityManager().useAbility(var1, "flux-beam");
            var1.sendMessage("§cNo target found!");
         } else {
            if (var5 instanceof Player) {
               Player var6 = (Player)var5;
               if (this.a.getTrustedPlayersManager().isTrusted(var1, var6)) {
                  int var7 = this.a.getConfig().getInt("abilities.damage.flux-beam-max-armor-damage", 150);
                  int var31 = (int)((double)var3 / (double)100.0F * (double)var7);
                  this.d(var6, var31);
                  String var40 = var6.getName();
                  var1.sendMessage("§a§lRestored " + var40 + "'s armor! (+" + var31 + " durability)");
                  this.a(var1.getEyeLocation(), var6.getEyeLocation().add((double)0.0F, (double)1.0F, (double)0.0F));
                  return;
               }
            }

            double var30 = this.a.getConfig().getDouble("abilities.damage.flux-beam-base", (double)5.0F);
            double var8 = (double)1.0F + (double)var3 / (double)50.0F;
            double var10 = var30 * var8;
            int var12 = this.a.getConfig().getInt("abilities.damage.flux-beam-max-armor-damage", 150);
            int var13 = (int)((double)var3 / (double)100.0F * (double)var12);
            if (var5 instanceof Player) {
               Player var14 = (Player)var5;
               if (var14.getGameMode() == GameMode.CREATIVE || var14.getGameMode() == GameMode.SPECTATOR) {
                  var1.sendMessage("§cCannot hit players in creative/spectator mode!");
                  return;
               }
            }

            double var32 = var5.getHealth();
            boolean var16 = var32 <= var10;
            boolean var17 = false;
            if (var16 && var5 instanceof Player) {
               Player var18 = (Player)var5;
               ItemStack var19 = var18.getInventory().getItemInMainHand();
               ItemStack var20 = var18.getInventory().getItemInOffHand();
               if (var19.getType() == Material.TOTEM_OF_UNDYING) {
                  var17 = true;
                  var18.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
               } else if (var20.getType() == Material.TOTEM_OF_UNDYING) {
                  var17 = true;
                  var18.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
               }

               if (var17) {
                  var18.setHealth((double)1.0F);
                  var18.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 800, 1));
                  var18.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 1));
                  var18.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 800, 0));
                  var18.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, var18.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 100, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.5F);
                  var18.playSound(var18.getLocation(), Sound.ITEM_TOTEM_USE, 1.0F, 1.0F);
                  var1.sendMessage("§e⚡ Totem of Undying activated!");
               }
            }

            if (!var17) {
               double var33 = Math.max((double)0.0F, var32 - var10);
               var5.setHealth(var33);
            }

            if (var5 instanceof Player) {
               Player var34 = (Player)var5;
               this.c(var34, var13);
               var34.sendTitle("", "§c§l-" + String.format("%.1f", var10) + " HP", 5, 15, 10);
               Player.Spigot var10000 = var34.spigot();
               ChatMessageType var10001 = ChatMessageType.ACTION_BAR;
               String var10004 = String.format("%.1f", var10);
               var10000.sendMessage(var10001, new TextComponent("§c§l⚡ FLUX BEAM HIT! -" + var10004 + " HP §7(Armor: -" + var13 + ")"));
            }

            this.a(var1.getEyeLocation(), var5.getEyeLocation().add((double)0.0F, (double)1.0F, (double)0.0F));
            Location var35 = var5.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F);
            Particle.DustOptions var36 = new Particle.DustOptions(Color.fromRGB(255, 50, 50), 1.5F);
            Particle.DustOptions var37 = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 1.3F);
            var5.getWorld().spawnParticle(Particle.DUST, var35, 150, 1.2, (double)1.5F, 1.2, (double)0.0F, var37, true);
            var5.getWorld().spawnParticle(Particle.DUST, var35, 100, 0.9, 1.2, 0.9, (double)0.0F, var36, true);
            var5.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var35, 200, (double)1.0F, 1.3, (double)1.0F, 0.15);
            var5.getWorld().spawnParticle(Particle.CRIT, var35, 80, 0.8, (double)1.0F, 0.8, 0.3);
            var5.getWorld().spawnParticle(Particle.ENCHANTED_HIT, var35, 100, 0.9, 1.2, 0.9, 0.2);
            var5.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, var35, 60, 0.7, (double)1.0F, 0.7, 0.08);
            var5.getWorld().spawnParticle(Particle.EXPLOSION, var35, 5, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F);

            for(int var21 = 0; var21 < 32; ++var21) {
               double var22 = (double)var21 / (double)32.0F * (double)2.0F * Math.PI;
               double var24 = (double)1.5F;
               double var26 = Math.cos(var22) * var24;
               double var28 = Math.sin(var22) * var24;
               var5.getWorld().spawnParticle(Particle.DUST, var5.getLocation().add(var26, (double)0.5F, var28), 5, 0.1, 0.3, 0.1, (double)0.0F, var36, true);
               var5.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var5.getLocation().add(var26, (double)0.5F, var28), 3, 0.1, 0.2, 0.1, 0.05);
            }

            var1.playSound(var1.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.5F, 1.8F);
            var5.getWorld().playSound(var5.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.5F, 1.5F);
            var5.getWorld().playSound(var5.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.8F, 1.5F);
            var5.getWorld().playSound(var5.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0F, 0.8F);
            this.a.getAbilityManager().useAbility(var1, "flux-beam");
            String var38 = var5 instanceof Player ? ((Player)var5).getName() : var5.getType().name();
            var1.sendMessage("§b⚡§l FLUX BEAM HIT! §r§o" + var38 + " §7(" + var3 + "% charge)");
            String var39 = String.format("%.1f", var10);
            var1.sendMessage("§c  ❤ Damage: " + var39 + " HP §8| §7Armor: -" + var13);
         }
      }
   }

   private void a(Location var1, Location var2) {
      double var3 = var1.distance(var2);
      int var5 = (int)(var3 * (double)8.0F);
      Particle.DustOptions var6 = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 1.2F);
      Particle.DustOptions var7 = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 0.8F);

      for(int var8 = 0; var8 <= var5; ++var8) {
         double var9 = (double)var8 / (double)var5;
         double var11 = var1.getX() + (var2.getX() - var1.getX()) * var9;
         double var13 = var1.getY() + (var2.getY() - var1.getY()) * var9;
         double var15 = var1.getZ() + (var2.getZ() - var1.getZ()) * var9;
         new Location(var1.getWorld(), var11, var13, var15);
         var1.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var11, var13, var15, 8, 0.08, 0.08, 0.08, 0.03);
         var1.getWorld().spawnParticle(Particle.DUST, var11, var13, var15, 6, 0.1, 0.1, 0.1, (double)0.0F, var6, true);
         var1.getWorld().spawnParticle(Particle.DUST, var11, var13, var15, 4, 0.15, 0.15, 0.15, (double)0.0F, var7, true);
         var1.getWorld().spawnParticle(Particle.END_ROD, var11, var13, var15, 3, 0.05, 0.05, 0.05, 0.01);
         var1.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, var11, var13, var15, 2, 0.08, 0.08, 0.08, 0.01);
         if (var8 % 3 == 0) {
            double var18 = (double)var8 * (double)0.5F;
            double var20 = (double)0.25F;

            for(int var22 = 0; var22 < 3; ++var22) {
               double var23 = var18 + (double)var22 * Math.PI * (double)2.0F / (double)3.0F;
               double var25 = Math.cos(var23) * var20;
               double var27 = Math.sin(var23) * var20;
               var1.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var11 + var25, var13, var15 + var27, 1, 0.02, 0.02, 0.02, (double)0.0F);
            }
         }
      }

   }

   private void c(Player var1, int var2) {
      if (var2 > 0) {
         for(ItemStack var6 : var1.getInventory().getArmorContents()) {
            if (var6 != null && var6.getType() != Material.AIR) {
               ItemMeta var7 = var6.getItemMeta();
               if (var7 instanceof Damageable) {
                  Damageable var8 = (Damageable)var7;
                  int var9 = var8.getDamage() + var2;
                  short var10 = var6.getType().getMaxDurability();
                  if (var9 >= var10) {
                     var6.setAmount(0);
                  } else {
                     var8.setDamage(var9);
                     var6.setItemMeta(var7);
                  }
               }
            }
         }

      }
   }

   private void d(Player var1, int var2) {
      if (var2 > 0) {
         for(ItemStack var6 : var1.getInventory().getArmorContents()) {
            if (var6 != null && var6.getType() != Material.AIR) {
               ItemMeta var7 = var6.getItemMeta();
               if (var7 instanceof Damageable) {
                  Damageable var8 = (Damageable)var7;
                  int var9 = Math.max(0, var8.getDamage() - var2);
                  var8.setDamage(var9);
                  var6.setItemMeta(var7);
               }
            }
         }

         var1.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 50, (double)0.5F, (double)0.5F, (double)0.5F);
         var1.playSound(var1.getLocation(), Sound.BLOCK_ANVIL_USE, 0.5F, 2.0F);
      }
   }

   public void d(Player var1) {
      UUID var2 = var1.getUniqueId();
      BukkitTask var3 = (BukkitTask)this.d.remove(var2);
      if (var3 != null) {
         var3.cancel();
      }

      this.c.remove(var2);
      this.e.remove(var2);
   }

   public void cleanup(Player var1) {
      this.d(var1);
   }

   public void e(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "flux-ground";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            LivingEntity var3 = this.e(var1, 20);
            if (var3 == null) {
               var1.sendMessage("§cNo target found!");
            } else {
               if (var3 instanceof Player) {
                  Player var4 = (Player)var3;
                  if (this.a.getTrustedPlayersManager().isTrusted(var1, var4)) {
                     var1.sendMessage("§c§lYou cannot stun " + var4.getName() + " because they are trusted!");
                     return;
                  }
               }

               int var14 = this.a.getConfig().getInt("abilities.durations.flux-ground-freeze", 5);
               int var5 = var14 * 20;
               var3.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, var5, 255, false, true));
               var3.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, var5, 255, false, true));
               var3.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, var5, 250, false, true));
               var3.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, var5, 1, false, true));
               if (var3 instanceof Player) {
                  b.add(var3.getUniqueId());
                  Bukkit.getScheduler().runTaskLater(this.a, () -> b.remove(var3.getUniqueId()), (long)var5);
               }

               Particle.DustOptions var6 = new Particle.DustOptions(ParticleUtils.FLUX_DARK_CYAN, 1.5F);
               var3.getWorld().spawnParticle(Particle.DUST, var3.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 200, (double)1.0F, 1.8, (double)1.0F, (double)0.0F, var6, true);
               var3.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var3.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 100, 0.7, 1.2, 0.7);
               var3.getWorld().spawnParticle(Particle.ENCHANTED_HIT, var3.getLocation(), 60, 0.7, 0.2, 0.7);
               var3.getWorld().spawnParticle(Particle.SMOKE, var3.getLocation().add((double)0.0F, (double)0.5F, (double)0.0F), 40, (double)0.5F, (double)0.5F, (double)0.5F);

               for(int var7 = 0; var7 < 24; ++var7) {
                  double var8 = (double)var7 / (double)24.0F * (double)2.0F * Math.PI;
                  double var10 = Math.cos(var8) * 1.8;
                  double var12 = Math.sin(var8) * 1.8;
                  var3.getWorld().spawnParticle(Particle.DUST, var3.getLocation().add(var10, 0.1, var12), 8, 0.15, 0.15, 0.15, (double)0.0F, var6, true);
                  var3.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var3.getLocation().add(var10, (double)0.5F, var12), 3, 0.1, 0.1, 0.1, (double)0.0F);
               }

               var3.getWorld().playSound(var3.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0F, 0.5F);
               var3.getWorld().playSound(var3.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.8F, 1.5F);
               var1.playSound(var1.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 1.2F);
               var1.spawnParticle(Particle.ELECTRIC_SPARK, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 50, 1.2, 1.2, 1.2);
               this.a.getAbilityManager().useAbility(var1, var2);
               String var15 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Ground");
               if (var15 != null && !var15.isEmpty()) {
                  var1.sendMessage(var15);
               }

               if (var3 instanceof Player) {
                  Player var16 = (Player)var3;
                  var16.sendMessage("§c§lYou have been grounded for " + var14 + " seconds! You cannot move or jump!");
               }

               String var17 = var3 instanceof Player ? ((Player)var3).getName() : var3.getType().name();
               var1.sendMessage("§b⚡ §oGrounded " + var17 + " for " + var14 + " seconds!");
            }
         }
      }
   }

   public void f(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "flux-flashbang";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            double var3 = this.a.getConfig().getDouble("abilities.flux-flashbang.radius", (double)8.0F);
            int var5 = this.a.getConfigManager().getAbilityDuration("flux-flashbang") * 20;
            Location var6 = var1.getLocation();
            int var7 = 0;

            for(Entity var9 : var1.getWorld().getNearbyEntities(var6, var3, var3, var3)) {
               if (var9 instanceof LivingEntity && !var9.equals(var1)) {
                  LivingEntity var10 = (LivingEntity)var9;
                  if (var10 instanceof Player) {
                     Player var11 = (Player)var10;
                     if (this.a.getTrustedPlayersManager().isTrusted(var1, var11)) {
                        continue;
                     }
                  }

                  var10.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, var5, 0, false, true));
                  var10.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, var5, 1, false, true));
                  Particle.DustOptions var14 = new Particle.DustOptions(Color.WHITE, 1.5F);
                  var10.getWorld().spawnParticle(Particle.DUST, var10.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 80, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var14, true);
                  ++var7;
                  if (var10 instanceof Player) {
                     ((Player)var10).sendMessage("§f§lFLASHBANGED! §7You are blinded!");
                  }
               }
            }

            Particle.DustOptions var12 = new Particle.DustOptions(Color.WHITE, 2.0F);
            var6.getWorld().spawnParticle(Particle.DUST, var6.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 200, var3 / (double)2.0F, var3 / (double)2.0F, var3 / (double)2.0F, (double)0.0F, var12, true);
            var6.getWorld().spawnParticle(Particle.FIREWORK, var6.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 50, var3 / (double)3.0F, var3 / (double)3.0F, var3 / (double)3.0F, 0.2);
            var6.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var6.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 80, var3 / (double)2.0F, var3 / (double)2.0F, var3 / (double)2.0F, 0.1);
            var1.getWorld().playSound(var6, Sound.ENTITY_GENERIC_EXPLODE, 1.5F, 1.8F);
            var1.getWorld().playSound(var6, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 2.0F);
            this.a.getAbilityManager().useAbility(var1, var2);
            String var13 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Flashbang");
            if (var13 != null && !var13.isEmpty()) {
               var1.sendMessage(var13);
            }

            var1.sendMessage("§b⚡ §oFlashbang! Blinded " + var7 + " enemies!");
         }
      }
   }

   public void g(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "flux-kinetic-burst";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            double var3 = this.a.getConfig().getDouble("abilities.flux-kinetic-burst.radius", (double)6.0F);
            double var5 = this.a.getConfig().getDouble("abilities.flux-kinetic-burst.knockback", (double)2.5F);
            Location var7 = var1.getLocation();
            int var8 = 0;

            for(Entity var10 : var1.getWorld().getNearbyEntities(var7, var3, var3, var3)) {
               if (!var10.equals(var1)) {
                  if (var10 instanceof Player) {
                     Player var11 = (Player)var10;
                     if (this.a.getTrustedPlayersManager().isTrusted(var1, var11)) {
                        continue;
                     }
                  }

                  Vector var14 = var10.getLocation().toVector().subtract(var7.toVector());
                  if (var14.lengthSquared() > (double)0.0F) {
                     var14.normalize();
                  }

                  var14.setY((double)0.5F);
                  var14.multiply(var5);
                  var10.setVelocity(var14);
                  ++var8;
               }
            }

            var1.getWorld().spawnParticle(Particle.SONIC_BOOM, var7.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 10, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F);
            Particle.DustOptions var12 = new Particle.DustOptions(ParticleUtils.FLUX_CYAN, 1.5F);
            var1.getWorld().spawnParticle(Particle.DUST, var7.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 150, var3 / (double)2.0F, var3 / (double)2.0F, var3 / (double)2.0F, (double)0.0F, var12, true);
            var1.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var7.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 100, var3 / (double)2.0F, var3 / (double)2.0F, var3 / (double)2.0F, 0.2);
            ParticleUtils.drawRing(var7, ParticleUtils.FLUX_CYAN, 1.2F, var3 * 0.33, 0.1, 32);
            ParticleUtils.drawRing(var7, ParticleUtils.FLUX_CYAN, 1.2F, var3 * 0.66, 0.1, 32);
            ParticleUtils.drawRing(var7, ParticleUtils.FLUX_CYAN, 1.2F, var3, 0.1, 32);
            var1.getWorld().playSound(var7, Sound.ENTITY_WARDEN_SONIC_BOOM, 1.5F, 0.8F);
            var1.getWorld().playSound(var7, Sound.ENTITY_GENERIC_EXPLODE, 1.2F, 0.5F);
            this.a.getAbilityManager().useAbility(var1, var2);
            String var13 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Kinetic Burst");
            if (var13 != null && !var13.isEmpty()) {
               var1.sendMessage(var13);
            }

            var1.sendMessage("§b⚡ §oKinetic Burst! Knocked back " + var8 + " entities!");
         }
      }
   }

   private LivingEntity e(Player var1, int var2) {
      org.bukkit.util.RayTraceResult var3 = var1.getWorld().rayTraceEntities(var1.getEyeLocation(), var1.getEyeLocation().getDirection(), (double)var2, 0.3, (var1x) -> var1x instanceof LivingEntity && var1x != var1);
      return var3 != null && var3.getHitEntity() instanceof LivingEntity ? (LivingEntity)var3.getHitEntity() : null;
   }
}


static public class d implements GemAbilityHandler {
   private final BlissGems a;

   public d(BlissGems var1) {
      this.a = var1;
   }

   public void a(Player var1, int var2) {
      if (var2 == 2 && var1.isSneaking()) {
         this.b(var1);
      } else {
         this.a(var1);
      }

   }

   public void onPrimary(Player var1, int var2) {
      this.a(var1);
   }

   public void onSecondary(Player var1, int var2) {
      this.b(var1);
   }

   public void onTertiary(Player var1, int var2) {
      this.c(var1);
   }

   public void onQuaternary(Player var1, int var2) {
      this.d(var1);
   }

   public void a(Player var1) {
      String var2 = "life-drainer";
      if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         LivingEntity var3 = this.b(var1, 15);
         if (var3 == null) {
            var1.sendMessage("§cNo target found!");
         } else {
            int var4 = this.a.getConfigManager().getAbilityDuration("life-drainer");
            var3.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, var4 * 20, 1, false, true));
            Particle.DustOptions var5 = new Particle.DustOptions(ParticleUtils.LIFE_PINK, 1.5F);
            var3.getWorld().spawnParticle(Particle.DUST, var3.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var5, true);
            var3.getWorld().spawnParticle(Particle.SCULK_SOUL, var3.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 20, (double)0.5F, (double)0.5F, (double)0.5F);
            var1.playSound(var1.getLocation(), Sound.ENTITY_WITHER_HURT, 1.0F, 1.5F);
            this.a.getAbilityManager().useAbility(var1, var2);
            this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Heart Drainer");
         }
      }
   }

   public void b(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "life-circle-of-life";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            int var3 = this.a.getConfigManager().getAbilityDuration("life-circle");
            int var4 = var3 * 20;
            double var5 = this.a.getConfig().getDouble("abilities.life-circle.radius", (double)8.0F);
            double var7 = this.a.getConfig().getDouble("abilities.life-circle.max-health-increase", (double)4.0F);
            double var9 = this.a.getConfig().getDouble("abilities.life-circle.max-health-decrease", (double)4.0F);
            Location var11 = var1.getLocation().clone();
            HashSet var12 = new HashSet();
            NamespacedKey var13 = new NamespacedKey(this.a, "circle-of-life");
            int[] var14 = new int[]{0};
            BukkitTask var15 = this.a.getServer().getScheduler().runTaskTimer(this.a, () -> {
               if (var1.isOnline() && !var1.isDead() && var14[0] < var4) {
                  if (var1.getLocation().distance(var11) <= var5) {
                     this.a(var1, var13, var7, var12);
                     var1.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1, true, true));
                  } else {
                     this.a(var1, var13, var12);
                  }

                  for(Entity var14x : var11.getWorld().getNearbyEntities(var11, var5, var5, var5)) {
                     if (var14x != var1) {
                        if (!(var14x instanceof Player)) {
                           if (var14x instanceof LivingEntity) {
                              ((LivingEntity)var14x).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 0, false, true));
                           }
                        } else {
                           Player var15 = (Player)var14x;
                           boolean var16 = this.a.getTrustedPlayersManager().isTrusted(var1, var15);
                           if (var16) {
                              this.a(var15, var13, var7, var12);
                              var15.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1, true, true));
                           } else {
                              this.a(var15, var13, -var9, var12);
                              var15.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 0, false, true));
                           }
                        }
                     }
                  }

                  HashSet var23 = new HashSet();

                  for(UUID var26 : new HashSet(var12)) {
                     if (!var26.equals(var1.getUniqueId())) {
                        Player var28 = this.a.getServer().getPlayer(var26);
                        if (var28 == null || !var28.isOnline() || var28.getLocation().distance(var11) > var5) {
                           if (var28 != null && var28.isOnline()) {
                              this.a(var28, var13);
                           }

                           var23.add(var26);
                        }
                     }
                  }

                  var12.removeAll(var23);
                  Particle.DustOptions var25 = new Particle.DustOptions(ParticleUtils.LIFE_PINK_ALT, 1.0F);
                  var1.getWorld().spawnParticle(Particle.DUST, var11.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)4.0F, (double)0.5F, (double)4.0F, (double)0.0F, var25, true);
                  var1.getWorld().spawnParticle(Particle.HEART, var11.clone().add((double)0.0F, (double)1.0F, (double)0.0F), 20, (double)4.0F, (double)0.5F, (double)4.0F);
                  var1.getWorld().spawnParticle(Particle.SCULK_SOUL, var11.clone().add((double)0.0F, (double)0.5F, (double)0.0F), 30, (double)4.0F, 0.2, (double)4.0F);
                  var1.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, var11.clone().add((double)0.0F, 0.2, (double)0.0F), 15, (double)3.0F, 0.1, (double)3.0F);
                  byte var27 = 32;

                  for(int var29 = 0; var29 < var27; ++var29) {
                     double var17 = (double)var29 / (double)var27 * (double)2.0F * Math.PI;
                     double var19 = Math.cos(var17) * var5;
                     double var21 = Math.sin(var17) * var5;
                     var1.getWorld().spawnParticle(Particle.DUST, var11.clone().add(var19, 0.2, var21), 2, 0.1, 0.1, 0.1, (double)0.0F, var25, true);
                     var1.getWorld().spawnParticle(Particle.HEART, var11.clone().add(var19, (double)1.0F, var21), 1, 0.05, 0.05, 0.05, (double)0.0F);
                     var1.getWorld().spawnParticle(Particle.SCULK_SOUL, var11.clone().add(var19, 1.8, var21), 1, 0.05, 0.05, 0.05, (double)0.0F);
                  }

                  var14[0] += 20;
               }
            }, 0L, 20L);
            this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
               var15.cancel();

               for(UUID var5 : var12) {
                  Player var6 = this.a.getServer().getPlayer(var5);
                  if (var6 != null && var6.isOnline()) {
                     this.a(var6, var13);
                  }
               }

               var12.clear();
            }, (long)var4);
            this.a.getAbilityManager().useAbility(var1, var2);
            this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Circle of Life");
         }
      }
   }

   private void a(Player var1, NamespacedKey var2, double var3, Set<UUID> var5) {
      AttributeInstance var6 = var1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
      if (var6 != null) {
         boolean var7 = var6.getModifiers().stream().anyMatch((var2x) -> this.a(var2x, var2));
         if (!var7) {
            String var10003 = var2.toString();
            var6.addModifier(new AttributeModifier(UUID.nameUUIDFromBytes((var10003 + ":" + String.valueOf(var1.getUniqueId())).getBytes()), var2.toString(), var3, Operation.ADD_NUMBER));
            var5.add(var1.getUniqueId());
         }
      }
   }

   private void a(Player var1, NamespacedKey var2, Set<UUID> var3) {
      this.a(var1, var2);
      var3.remove(var1.getUniqueId());
   }

   private void a(Player var1, NamespacedKey var2) {
      AttributeInstance var3 = var1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
      if (var3 != null) {
         ArrayList var4 = new ArrayList();

         for(AttributeModifier var6 : var3.getModifiers()) {
            if (this.a(var6, var2)) {
               var4.add(var6);
            }
         }

         for(AttributeModifier var8 : var4) {
            var3.removeModifier(var8);
         }

      }
   }

   private boolean a(AttributeModifier var1, NamespacedKey var2) {
      try {
         NamespacedKey var3 = var1.getKey();
         if (var3 != null && var3.equals(var2)) {
            return true;
         }
      } catch (Throwable var4) {
      }

      String var5 = var1.getName();
      return var5 != null && (var5.equals(var2.toString()) || var5.equals(var2.getKey()));
   }

   public void c(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "life-vitality-vortex";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            int var3 = this.a.getConfigManager().getAbilityDuration("life-vitality-vortex");
            int var4 = var3 * 20;
            Biome var5 = var1.getLocation().getBlock().getBiome();
            String var6 = var5.name().toUpperCase();
            PotionEffect[] var7 = this.a(var6, var4);

            for(PotionEffect var11 : var7) {
               var1.addPotionEffect(var11);
            }

            Particle.DustOptions var12 = new Particle.DustOptions(ParticleUtils.LIFE_PINK, 1.5F);
            var1.getWorld().spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 50, (double)1.0F, (double)1.0F, (double)1.0F, (double)0.0F, var12, true);
            var1.getWorld().spawnParticle(Particle.HEART, var1.getLocation().add((double)0.0F, (double)1.5F, (double)0.0F), 15, (double)0.5F, (double)0.5F, (double)0.5F);
            var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0F, 1.5F);
            this.a.getAbilityManager().useAbility(var1, var2);
            this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Vitality Vortex");
         }
      }
   }

   private PotionEffect[] a(String var1, int var2) {
      if (!var1.contains("FOREST") && !var1.contains("PLAINS") && !var1.contains("MEADOW")) {
         if (!var1.contains("DESERT") && !var1.contains("BADLANDS") && !var1.contains("SAVANNA")) {
            if (!var1.contains("OCEAN") && !var1.contains("RIVER") && !var1.contains("BEACH") && !var1.contains("SWAMP")) {
               if (!var1.contains("TAIGA") && !var1.contains("SNOWY") && !var1.contains("FROZEN") && !var1.contains("ICE")) {
                  if (var1.contains("JUNGLE")) {
                     return new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, var2, 1, true, true), new PotionEffect(PotionEffectType.JUMP_BOOST, var2, 0, true, true)};
                  } else if (!var1.contains("NETHER") && !var1.contains("CRIMSON") && !var1.contains("WARPED") && !var1.contains("BASALT") && !var1.contains("SOUL_SAND")) {
                     if (var1.contains("END") && !var1.contains("ENDER")) {
                        return new PotionEffect[]{new PotionEffect(PotionEffectType.SLOW_FALLING, var2, 0, true, true), new PotionEffect(PotionEffectType.NIGHT_VISION, var2, 0, true, true)};
                     } else {
                        return !var1.contains("MOUNTAIN") && !var1.contains("STONY") && !var1.contains("CAVE") && !var1.contains("DRIPSTONE") && !var1.contains("LUSH") && !var1.contains("DEEP_DARK") ? new PotionEffect[]{new PotionEffect(PotionEffectType.REGENERATION, var2, 0, true, true), new PotionEffect(PotionEffectType.SPEED, var2, 0, true, true)} : new PotionEffect[]{new PotionEffect(PotionEffectType.HASTE, var2, 1, true, true), new PotionEffect(PotionEffectType.NIGHT_VISION, var2, 0, true, true)};
                     }
                  } else {
                     return new PotionEffect[]{new PotionEffect(PotionEffectType.FIRE_RESISTANCE, var2, 0, true, true), new PotionEffect(PotionEffectType.STRENGTH, var2, 1, true, true)};
                  }
               } else {
                  return new PotionEffect[]{new PotionEffect(PotionEffectType.RESISTANCE, var2, 0, true, true), new PotionEffect(PotionEffectType.SPEED, var2, 0, true, true)};
               }
            } else {
               return new PotionEffect[]{new PotionEffect(PotionEffectType.WATER_BREATHING, var2, 0, true, true), new PotionEffect(PotionEffectType.DOLPHINS_GRACE, var2, 0, true, true)};
            }
         } else {
            return new PotionEffect[]{new PotionEffect(PotionEffectType.FIRE_RESISTANCE, var2, 0, true, true), new PotionEffect(PotionEffectType.STRENGTH, var2, 0, true, true)};
         }
      } else {
         return new PotionEffect[]{new PotionEffect(PotionEffectType.REGENERATION, var2, 1, true, true), new PotionEffect(PotionEffectType.SATURATION, var2, 0, true, true)};
      }
   }

   public void d(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "life-heart-lock";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            int var3 = this.a.getConfig().getInt("abilities.life-heart-lock.range", 15);
            LivingEntity var4 = this.b(var1, var3);
            if (var4 == null) {
               var1.sendMessage("§cNo target found!");
            } else {
               if (var4 instanceof Player) {
                  Player var5 = (Player)var4;
                  if (this.a.getTrustedPlayersManager().isTrusted(var1, var5)) {
                     var1.sendMessage("§c§oYou cannot Heart Lock a trusted player!");
                     return;
                  }
               }

               int var18 = this.a.getConfigManager().getAbilityDuration("life-heart-lock");
               int var6 = var18 * 20;
               AttributeInstance var7 = var4.getAttribute(Attribute.GENERIC_MAX_HEALTH);
               if (var7 == null) {
                  var1.sendMessage("§cCannot Heart Lock this target!");
               } else {
                  double var8 = var4.getHealth();
                  double var10 = var7.getValue();
                  double var12 = var8 - var10;
                  NamespacedKey var14 = new NamespacedKey(this.a, "heart-lock");
                  ArrayList var15 = new ArrayList();

                  for(AttributeModifier var17 : var7.getModifiers()) {
                     if (this.a(var17, var14)) {
                        var15.add(var17);
                     }
                  }

                  for(AttributeModifier var21 : var15) {
                     var7.removeModifier(var21);
                  }

                  String var10003 = var14.toString();
                  var7.addModifier(new AttributeModifier(UUID.nameUUIDFromBytes((var10003 + ":" + String.valueOf(var4.getUniqueId())).getBytes()), var14.toString(), var12, Operation.ADD_NUMBER));
                  this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
                     if (var4.isValid()) {
                        AttributeInstance var3 = var4.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                        if (var3 != null) {
                           ArrayList var4x = new ArrayList();

                           for(AttributeModifier var6 : var3.getModifiers()) {
                              if (this.a(var6, var14)) {
                                 var4x.add(var6);
                              }
                           }

                           for(AttributeModifier var9 : var4x) {
                              var3.removeModifier(var9);
                           }

                           double var8 = var3.getValue();
                           if (var4.getHealth() > var8) {
                              var4.setHealth(var8);
                           }
                        }
                     }

                  }, (long)var6);
                  Particle.DustOptions var20 = new Particle.DustOptions(ParticleUtils.LIFE_PINK, 1.5F);
                  Particle.DustOptions var22 = new Particle.DustOptions(Color.fromRGB(150, 0, 100), 1.2F);
                  var4.getWorld().spawnParticle(Particle.DUST, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var20, true);
                  var4.getWorld().spawnParticle(Particle.DUST, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 20, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var22, true);
                  var4.getWorld().playSound(var4.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0F, 0.5F);
                  var1.playSound(var1.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.5F, 0.5F);
                  this.a.getAbilityManager().useAbility(var1, var2);
                  this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Heart Lock");
                  if (var4 instanceof Player) {
                     ((Player)var4).sendMessage("§d§oYour max health has been locked!");
                  }

               }
            }
         }
      }
   }

   public void cleanup(Player var1) {
      AttributeInstance var2 = var1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
      if (var2 != null) {
         ArrayList var3 = new ArrayList();

         for(AttributeModifier var5 : var2.getModifiers()) {
            if (this.a(var5)) {
               var3.add(var5);
            }
         }

         for(AttributeModifier var8 : var3) {
            var2.removeModifier(var8);
         }

         double var7 = var2.getValue();
         if (var1.getHealth() > var7) {
            var1.setHealth(var7);
         }

         if (!var3.isEmpty()) {
            Logger var10000 = this.a.getLogger();
            int var10001 = var3.size();
            var10000.info("[LifeAbilities] Removed " + var10001 + " stale life-modifier(s) from " + var1.getName());
         }

      }
   }

   private boolean a(AttributeModifier var1) {
      String var2 = var1.getName();
      if (var2 != null) {
         String var3 = var2.toLowerCase();
         if (var3.contains("blissgems")) {
            return true;
         }

         if (var3.contains("circle-of-life") || var3.contains("heart-lock")) {
            return true;
         }

         if (var3.contains("circle_of_life") || var3.contains("heart_lock")) {
            return true;
         }
      }

      try {
         NamespacedKey var7 = var1.getKey();
         if (var7 != null) {
            if ("blissgems".equals(var7.getNamespace())) {
               return true;
            }

            String var4 = var7.getKey();
            if (var4 != null) {
               String var5 = var4.toLowerCase();
               if (!var5.contains("circle-of-life") && !var5.contains("heart-lock")) {
                  if (!var5.contains("circle_of_life") && !var5.contains("heart_lock")) {
                     return false;
                  }

                  return true;
               }

               return true;
            }
         }
      } catch (Throwable var6) {
      }

      return false;
   }

   private LivingEntity b(Player var1, int var2) {
      return var1.getWorld().rayTraceEntities(var1.getEyeLocation(), var1.getEyeLocation().getDirection(), (double)var2, (var1x) -> var1x instanceof LivingEntity && var1x != var1) != null ? (LivingEntity)var1.getWorld().rayTraceEntities(var1.getEyeLocation(), var1.getEyeLocation().getDirection(), (double)var2, (var1x) -> var1x instanceof LivingEntity && var1x != var1).getHitEntity() : null;
   }
}


static public class e implements GemAbilityHandler {
   private final BlissGems a;
   private final Set<UUID> b = new HashSet();

   public e(BlissGems var1) {
      this.a = var1;
   }

   public boolean a(Player var1) {
      return this.b.contains(var1.getUniqueId());
   }

   public void b(Player var1) {
      this.b.remove(var1.getUniqueId());
   }

   public void a(Player var1, int var2) {
      if (var2 == 2 && var1.isSneaking()) {
         this.d(var1);
      } else {
         this.c(var1);
      }

   }

   public void onPrimary(Player var1, int var2) {
      this.c(var1);
   }

   public void onSecondary(Player var1, int var2) {
      this.d(var1);
   }

   public void onTertiary(Player var1, int var2) {
      this.e(var1);
   }

   public void c(Player var1) {
      String var2 = "puff-dash";
      if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         Vector var3 = var1.getLocation().getDirection();
         var3.setY(0.3);
         var3.multiply((double)2.5F);
         var1.setVelocity(var3);
         int var4 = this.a.getGemManager().getGemTier(var1);
         if (var4 >= 2) {
            this.a.getServer().getScheduler().runTaskLater(this.a, () -> {
               for(Entity var3 : var1.getNearbyEntities((double)2.0F, (double)2.0F, (double)2.0F)) {
                  if (var3 instanceof LivingEntity var4) {
                     if (!(var3 instanceof Player)) {
                        double var5 = this.a.getConfigManager().getAbilityDamage("puff-dash");
                        var4.damage(var5, var1);
                     }
                  }
               }

            }, 5L);
         }

         var1.playSound(var1.getLocation(), Sound.ENTITY_BREEZE_SHOOT, 1.0F, 1.5F);
         Particle.DustOptions var5 = new Particle.DustOptions(ParticleUtils.PUFF_WHITE, 1.5F);
         var1.spawnParticle(Particle.DUST, var1.getLocation(), 30, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var5, true);
         var1.spawnParticle(Particle.CLOUD, var1.getLocation(), 20, (double)0.5F, (double)0.5F, (double)0.5F, 0.1);
         this.a.getAbilityManager().useAbility(var1, var2);
         String var6 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Dash");
         if (var6 != null && !var6.isEmpty()) {
            var1.sendMessage(var6);
         }

      }
   }

   public void d(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "puff-breezy-bash";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            Vector var3 = var1.getVelocity();
            var3.setY((double)2.0F);
            var1.setVelocity(var3);
            var1.playSound(var1.getLocation(), Sound.ENTITY_BREEZE_JUMP, 1.0F, 1.0F);
            Particle.DustOptions var4 = new Particle.DustOptions(ParticleUtils.PUFF_WHITE, 1.5F);
            var1.spawnParticle(Particle.DUST, var1.getLocation(), 50, (double)1.0F, (double)1.0F, (double)1.0F, (double)0.0F, var4, true);
            var1.spawnParticle(Particle.CLOUD, var1.getLocation(), 40, (double)1.0F, (double)1.0F, (double)1.0F, 0.2);
            UUID var5 = var1.getUniqueId();
            this.b.add(var5);
            this.a.getServer().getScheduler().runTaskLater(this.a, () -> this.b.remove(var5), 200L);
            this.a.getAbilityManager().useAbility(var1, var2);
            String var6 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Breezy Bash");
            if (var6 != null && !var6.isEmpty()) {
               var1.sendMessage(var6);
            }

         }
      }
   }

   public void e(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "puff-group-bash";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            double var3 = this.a.getConfig().getDouble("abilities.puff-group-bash.radius", (double)10.0F);
            double var5 = this.a.getConfig().getDouble("abilities.puff-group-bash.knockback", (double)2.5F);
            int var7 = 0;

            for(Entity var9 : var1.getNearbyEntities(var3, var3, var3)) {
               if (var9 instanceof Player) {
                  Player var10 = (Player)var9;
                  if (!this.a.getTrustedPlayersManager().isTrusted(var1, var10)) {
                     Vector var11 = var10.getLocation().toVector().subtract(var1.getLocation().toVector());
                     if (var11.lengthSquared() < 0.01) {
                        var11 = new Vector(1, 0, 0);
                     }

                     var11.normalize().multiply(var5);
                     var11.setY((double)1.5F);
                     var10.setVelocity(var11);
                     var10.sendMessage("§f§oA gust of wind blows you away!");
                     ++var7;
                  }
               }
            }

            Particle.DustOptions var12 = new Particle.DustOptions(ParticleUtils.PUFF_WHITE, 1.5F);
            var1.getWorld().spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 60, (double)2.0F, (double)1.0F, (double)2.0F, (double)0.0F, var12, true);
            var1.getWorld().spawnParticle(Particle.CLOUD, var1.getLocation().add((double)0.0F, (double)0.5F, (double)0.0F), 50, (double)2.0F, (double)0.5F, (double)2.0F, 0.1);
            var1.playSound(var1.getLocation(), Sound.ENTITY_BREEZE_WIND_BURST, 1.0F, 0.8F);
            this.a.getAbilityManager().useAbility(var1, var2);
            String var13 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Group Breezy Bash");
            if (var13 != null && !var13.isEmpty()) {
               var1.sendMessage(var13);
            }

            if (var7 > 0) {
               var1.sendMessage("§f§o" + var7 + " player(s) knocked away!");
            }

         }
      }
   }
}


static public class f implements GemAbilityHandler {
   private final BlissGems a;
   private final Set<UUID> b = new HashSet();
   private final Map<UUID, BukkitTask> c = new HashMap();
   private static final Set<UUID> d = new HashSet();

   public f(BlissGems var1) {
      this.a = var1;
   }

   public static boolean a(UUID var0) {
      return d.contains(var0);
   }

   public static void b(UUID var0) {
      d.add(var0);
   }

   public static void c(UUID var0) {
      d.remove(var0);
   }

   public void a(Player var1, int var2) {
      if (var2 == 2 && var1.isSneaking()) {
         this.d(var1);
      } else {
         this.c(var1);
      }

   }

   public void onPrimary(Player var1, int var2) {
      this.a(var1, var2);
   }

   public void onSecondary(Player var1, int var2) {
      this.d(var1);
   }

   public void onTertiary(Player var1, int var2) {
      this.a(var1);
   }

   public void cleanup(Player var1) {
      this.d(var1.getUniqueId());
   }

   public void a(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         this.e(var1);
      }
   }

   public boolean b(Player var1) {
      return this.b.contains(var1.getUniqueId());
   }

   public void c(final Player var1) {
      String var2 = "speed-blur";
      if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         final int var3 = this.a.getConfig().getInt("abilities.blur.strikes", 3);
         int var4 = this.a.getConfig().getInt("abilities.blur.strike-delay", 8);
         final double var5 = this.a.getConfigManager().getAbilityDamage("blur");
         final double var7 = this.a.getConfig().getDouble("abilities.blur.knockback", (double)1.5F);
         final Location var9 = var1.getTargetBlock((Set)null, 20).getLocation().add((double)0.5F, (double)1.0F, (double)0.5F);
         Particle.DustOptions var10 = new Particle.DustOptions(ParticleUtils.SPEED_YELLOW, 2.0F);
         var1.playSound(var1.getLocation(), Sound.ENTITY_BREEZE_SHOOT, 1.0F, 2.0F);
         var1.spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 150, 0.8, 0.8, 0.8, (double)0.0F, var10, true);
         var1.spawnParticle(Particle.ELECTRIC_SPARK, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 80, 0.8, 0.8, 0.8);
         var1.spawnParticle(Particle.GUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, (double)0.5F, (double)0.5F);
         (new BukkitRunnable() {
            int a = 0;

            public void run() {
               if (var1.isOnline() && this.a < var3) {
                  double var1x = (Math.random() - (double)0.5F) * (double)3.0F;
                  double var3x = (Math.random() - (double)0.5F) * (double)3.0F;
                  Location var5x = var9.clone().add(var1x, (double)0.0F, var3x);
                  var5x.getWorld().strikeLightningEffect(var5x);
                  Particle.DustOptions var6 = new Particle.DustOptions(ParticleUtils.SPEED_YELLOW, 2.5F);
                  var5x.getWorld().spawnParticle(Particle.DUST, var5x, 250, (double)1.5F, (double)2.0F, (double)1.5F, (double)0.0F, var6, true);
                  var5x.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var5x, 150, 1.2, (double)1.5F, 1.2, 0.1);
                  var5x.getWorld().spawnParticle(Particle.EXPLOSION, var5x, 8, (double)1.0F, (double)1.0F, (double)1.0F);
                  var5x.getWorld().spawnParticle(Particle.GUST, var5x, 60, (double)1.0F, (double)1.5F, (double)1.0F);

                  for(int var7x = 0; var7x < 32; ++var7x) {
                     double var8 = (double)var7x / (double)32.0F * (double)2.0F * Math.PI;
                     double var10 = Math.cos(var8) * (double)3.0F;
                     double var12 = Math.sin(var8) * (double)3.0F;
                     var5x.getWorld().spawnParticle(Particle.DUST, var5x.clone().add(var10, 0.2, var12), 8, 0.2, 0.1, 0.2, (double)0.0F, var6, true);
                  }

                  for(Entity var15 : var5x.getWorld().getNearbyEntities(var5x, (double)3.5F, (double)3.5F, (double)3.5F)) {
                     if (var15 instanceof LivingEntity) {
                        LivingEntity var9x = (LivingEntity)var15;
                        if (!var15.equals(var1)) {
                           if (var15 instanceof Player) {
                              Player var16 = (Player)var15;
                              if (f.this.a.getTrustedPlayersManager().isTrusted(var1, var16)) {
                                 continue;
                              }
                           }

                           var9x.damage(var5, var1);
                           Location var11 = var5x.clone();
                           (new BukkitRunnable() {
                              // $FF: synthetic field
                              final LivingEntity a;
                              // $FF: synthetic field
                              final Location b;
                              // $FF: synthetic field
                              final f c;

                              {
                                 this.c = var1;
                                 this.a = var2;
                                 this.b = var3;
                              }

                              public void run() {
                                 if (this.a.isValid() && !this.a.isDead()) {
                                    Vector var1 = this.a.getLocation().toVector().subtract(this.b.toVector()).normalize().multiply(this.c.f).setY((double)0.5F);
                                    this.a.setVelocity(var1);
                                 }

                              }
                           }).runTaskLater(f.this.a, 1L);
                           var9x.getWorld().spawnParticle(Particle.DUST, var9x.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var6, true);
                           var9x.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var9x.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 25, (double)0.5F, (double)0.5F, (double)0.5F);
                        }
                     }
                  }

                  var5x.getWorld().playSound(var5x, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.5F, 1.0F);
                  var5x.getWorld().playSound(var5x, Sound.ENTITY_GENERIC_EXPLODE, 0.8F, 1.2F);
                  ++this.a;
               } else {
                  this.cancel();
               }
            }
         }).runTaskTimer(this.a, 0L, (long)var4);
         this.a.getAbilityManager().useAbility(var1, var2);
         String var11 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Blur");
         if (var11 != null && !var11.isEmpty()) {
            var1.sendMessage(var11);
         }

         var1.sendMessage("§e§oBlur unleashes " + var3 + " successive lightning strikes!");
      }
   }

   public void d(final Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "speed-storm";
         if (this.b(var1)) {
            this.f(var1);
         } else if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            UUID var3 = var1.getUniqueId();
            int var4 = this.a.getConfig().getInt("abilities.durations.speed-storm", 10);
            final int var5 = var4 * 20;
            final double var6 = this.a.getConfig().getDouble("abilities.speed-storm.radius", (double)8.0F);
            this.b.add(var3);
            Particle.DustOptions var8 = new Particle.DustOptions(ParticleUtils.SPEED_YELLOW, 2.0F);
            var1.playSound(var1.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.5F, 1.5F);
            var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0F, 1.8F);
            var1.spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 300, (double)2.0F, (double)2.0F, (double)2.0F, (double)0.0F, var8, true);
            var1.spawnParticle(Particle.ELECTRIC_SPARK, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 200, (double)2.0F, (double)2.0F, (double)2.0F);
            var1.spawnParticle(Particle.GUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 100, (double)1.5F, (double)1.5F, (double)1.5F);
            var1.spawnParticle(Particle.EXPLOSION, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 10, (double)1.0F, (double)1.0F, (double)1.0F);
            BukkitTask var9 = (new BukkitRunnable() {
               int a = 0;

               public void run() {
                  if (var1.isOnline() && !var1.isDead() && this.a < var5) {
                     ++this.a;
                     Location var1x = var1.getLocation();
                     if (this.a % 20 == 0) {
                        for(Entity var3 : var1x.getWorld().getNearbyEntities(var1x, var6, var6, var6)) {
                           if (var3 instanceof Player) {
                              Player var4 = (Player)var3;
                              boolean var5x = f.this.a.getTrustedPlayersManager().isTrusted(var1, var4);
                              if (!var5x && !var4.equals(var1)) {
                                 f.b(var4.getUniqueId());
                                 var4.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 255, false, true));
                                 var4.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 40, 255, false, true));
                                 var4.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 40, 128, false, true));
                                 var4.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 1, false, true));
                                 UUID var6x = var4.getUniqueId();
                                 (new BukkitRunnable() {
                                    // $FF: synthetic field
                                    final UUID a;
                                    // $FF: synthetic field
                                    final f b;

                                    {
                                       this.b = var1;
                                       this.a = var2;
                                    }

                                    public void run() {
                                       Player var1 = this.b.e.a.getServer().getPlayer(this.a);
                                       if (var1 != null && var1.isOnline()) {
                                          boolean var2 = false;

                                          for(UUID var4 : this.b.e.b) {
                                             Player var5 = this.b.e.a.getServer().getPlayer(var4);
                                             if (var5 != null && var5.isOnline() && var1.getLocation().distance(var5.getLocation()) <= this.b.d) {
                                                var2 = true;
                                                break;
                                             }
                                          }

                                          if (!var2) {
                                             f.c(this.a);
                                          }

                                       } else {
                                          f.c(this.a);
                                       }
                                    }
                                 }).runTaskLater(f.this.a, 42L);
                              } else {
                                 var4.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2, false, true));
                                 var4.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 40, 1, false, true));
                                 f.c(var4.getUniqueId());
                              }
                           }
                        }
                     }

                     if (this.a % 5 == 0) {
                        Particle.DustOptions var12 = new Particle.DustOptions(ParticleUtils.SPEED_YELLOW, 1.8F);

                        for(int var13 = 0; var13 < 40; ++var13) {
                           double var16 = (double)var13 / (double)40.0F * (double)2.0F * Math.PI;
                           double var18 = Math.cos(var16) * var6;
                           double var8 = Math.sin(var16) * var6;
                           var1x.getWorld().spawnParticle(Particle.DUST, var1x.clone().add(var18, 0.3, var8), 5, 0.2, 0.1, 0.2, (double)0.0F, var12, true);
                           var1x.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var1x.clone().add(var18, (double)0.5F, var8), 2, 0.1, 0.1, 0.1, 0.02);
                        }

                        for(int var14 = 0; var14 < 8; ++var14) {
                           double var17 = (double)var14 * Math.PI / (double)4.0F;
                           double var19 = Math.cos(var17) * var6;
                           double var20 = Math.sin(var17) * var6;

                           for(double var10 = (double)0.0F; var10 < (double)4.0F; var10 += (double)0.5F) {
                              var1x.getWorld().spawnParticle(Particle.DUST, var1x.clone().add(var19, var10, var20), 3, 0.1, 0.1, 0.1, (double)0.0F, var12, true);
                           }
                        }

                        for(double var15 = (double)0.0F; var15 < (double)3.0F; var15 += 0.3) {
                           var1x.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var1x.clone().add((double)0.0F, var15, (double)0.0F), 4, 0.2, 0.1, 0.2, 0.01);
                        }
                     }

                     if (this.a % 40 == 0) {
                        var1x.getWorld().playSound(var1x, Sound.BLOCK_BEACON_AMBIENT, 0.6F, 1.8F);
                     }

                  } else {
                     f.this.f(var1);
                     this.cancel();
                  }
               }
            }).runTaskTimer(this.a, 0L, 1L);
            this.c.put(var3, var9);
            this.a.getAbilityManager().useAbility(var1, var2);
            String var10 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Speed Storm");
            if (var10 != null && !var10.isEmpty()) {
               var1.sendMessage(var10);
            }

            var1.sendMessage("§e§oSpeed Storm active for " + var4 + "s! Allies gain Speed + Haste, enemies freeze!");
         }
      }
   }

   private void f(Player var1) {
      UUID var2 = var1.getUniqueId();
      this.b.remove(var2);
      BukkitTask var3 = (BukkitTask)this.c.remove(var2);
      if (var3 != null) {
         var3.cancel();
      }

      double var4 = this.a.getConfig().getDouble("abilities.speed-storm.radius", (double)8.0F);
      if (var1.isOnline()) {
         for(Entity var7 : var1.getLocation().getWorld().getNearbyEntities(var1.getLocation(), var4 + (double)5.0F, var4 + (double)5.0F, var4 + (double)5.0F)) {
            if (var7 instanceof Player) {
               Player var8 = (Player)var7;
               if (!var8.equals(var1)) {
                  UUID var9 = var8.getUniqueId();
                  if (a(var9)) {
                     boolean var10 = false;

                     for(UUID var12 : this.b) {
                        Player var13 = this.a.getServer().getPlayer(var12);
                        if (var13 != null && var13.isOnline() && !var13.equals(var1) && var8.getLocation().distance(var13.getLocation()) <= var4) {
                           var10 = true;
                           break;
                        }
                     }

                     if (!var10) {
                        c(var9);
                        var8.sendMessage("§e§oYou are no longer frozen.");
                     }
                  }
               }
            }
         }

         var1.sendMessage("§e§oSpeed Storm faded.");
         var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.6F, 1.5F);
      }

   }

   public void e(Player var1) {
      String var2 = "speed-terminal";
      if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
         int var3 = this.a.getConfig().getInt("abilities.durations.terminal-velocity", 10) * 20;
         var1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, var3, 2, false, true));
         var1.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, var3, 1, false, true));
         Particle.DustOptions var4 = new Particle.DustOptions(ParticleUtils.SPEED_YELLOW, 2.0F);
         var1.playSound(var1.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.5F, 2.0F);
         var1.playSound(var1.getLocation(), Sound.ENTITY_BREEZE_SHOOT, 1.5F, 2.0F);
         var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0F, 2.0F);
         var1.spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 300, (double)1.5F, (double)2.0F, (double)1.5F, (double)0.0F, var4, true);
         var1.spawnParticle(Particle.ELECTRIC_SPARK, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 200, (double)1.5F, (double)2.0F, (double)1.5F);
         var1.spawnParticle(Particle.GUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 100, (double)1.0F, (double)1.5F, (double)1.0F);
         var1.spawnParticle(Particle.EXPLOSION, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 8, (double)1.0F, (double)1.0F, (double)1.0F);
         var1.spawnParticle(Particle.HAPPY_VILLAGER, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 60, (double)1.0F, (double)1.0F, (double)1.0F);

         for(int var5 = 0; var5 < 32; ++var5) {
            double var6 = (double)var5 / (double)32.0F * (double)2.0F * Math.PI;
            double var8 = Math.cos(var6) * (double)2.5F;
            double var10 = Math.sin(var6) * (double)2.5F;
            var1.getWorld().spawnParticle(Particle.DUST, var1.getLocation().add(var8, (double)0.5F, var10), 8, 0.2, 0.1, 0.2, (double)0.0F, var4, true);
            var1.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, var1.getLocation().add(var8, (double)1.0F, var10), 4, 0.1, 0.1, 0.1);
         }

         this.a.getAbilityManager().useAbility(var1, var2);
         String var12 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Terminal Velocity");
         if (var12 != null && !var12.isEmpty()) {
            var1.sendMessage(var12);
         }

         var1.sendMessage("§e§l⚡ TERMINAL VELOCITY §7- Speed III + Haste II for " + var3 / 20 + " seconds!");
      }
   }

   public void d(UUID var1) {
      if (this.c.containsKey(var1)) {
         ((BukkitTask)this.c.get(var1)).cancel();
         this.c.remove(var1);
      }

      this.b.remove(var1);
      c(var1);
   }

   public static void a() {
      d.clear();
   }
}


static public class g implements GemAbilityHandler {
   private final BlissGems a;
   private static final Set<PotionEffectType> b;
   private final Map<UUID, UUID> c = new HashMap();
   private final Map<UUID, BukkitTask> d = new HashMap();
   private final Map<UUID, Map<UUID, Integer>> e = new HashMap();

   public g(BlissGems var1) {
      this.a = var1;
   }

   public void a(Player var1, int var2) {
      if (var2 >= 2 && var1.isSneaking()) {
         this.b(var1);
      } else {
         this.a(var1);
      }

   }

   public void onPrimary(Player var1, int var2) {
      this.a(var1);
   }

   public void onSecondary(Player var1, int var2) {
      this.b(var1);
   }

   public void onTertiary(Player var1, int var2) {
      this.c(var1);
   }

   public void a(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "strength-nullify";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            int var3 = this.a.getConfig().getInt("abilities.strength-nullify.range", 20);
            LivingEntity var4 = this.b(var1, var3);
            if (var4 == null) {
               var1.sendMessage("§cNo target found!");
            } else {
               if (var4 instanceof Player) {
                  Player var5 = (Player)var4;
                  if (this.a.getTrustedPlayersManager().isTrusted(var1, var5)) {
                     var1.sendMessage("§cYou cannot nullify a trusted player!");
                     return;
                  }
               }

               int var9 = 0;

               for(PotionEffect var7 : var4.getActivePotionEffects()) {
                  if (!var7.isAmbient() && b.contains(var7.getType())) {
                     var4.removePotionEffect(var7.getType());
                     ++var9;
                  }
               }

               Particle.DustOptions var10 = new Particle.DustOptions(ParticleUtils.STRENGTH_RED, 1.5F);
               var4.getWorld().spawnParticle(Particle.DUST, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, 0.8, (double)0.5F, (double)0.0F, var10, true);
               var4.getWorld().spawnParticle(Particle.SMOKE, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, 0.8, (double)0.5F, 0.05);
               var4.getWorld().spawnParticle(Particle.SWEEP_ATTACK, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 10, (double)0.5F, (double)0.5F, (double)0.5F);
               var1.playSound(var1.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 0.8F, 1.2F);
               var4.getWorld().playSound(var4.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 0.8F, 1.2F);
               this.a.getAbilityManager().useAbility(var1, var2);
               String var11 = var4 instanceof Player ? ((Player)var4).getName() : "target";
               String var8 = this.a.getConfigManager().getFormattedMessage("ability-activated", "ability", "Nullify");
               if (var8 != null) {
                  var1.sendMessage(var8 + " §7(Stripped " + var9 + " effects from " + var11 + ")");
               }

               if (var4 instanceof Player) {
                  ((Player)var4).sendMessage("§c§oYour potion effects have been nullified!");
               }

            }
         }
      }
   }

   public void b(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "strength-frailer";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            int var3 = this.a.getConfig().getInt("abilities.strength-frailer.range", 15);
            LivingEntity var4 = this.b(var1, var3);
            if (var4 == null) {
               var1.sendMessage("§cNo target found!");
            } else {
               if (var4 instanceof Player) {
                  Player var5 = (Player)var4;
                  if (this.a.getTrustedPlayersManager().isTrusted(var1, var5)) {
                     var1.sendMessage("§cYou cannot use Frailer on a trusted player!");
                     return;
                  }
               }

               int var10 = this.a.getConfig().getInt("abilities.durations.strength-frailer-weakness", 20) * 20;
               int var6 = this.a.getConfig().getInt("abilities.durations.strength-frailer-wither", 40) * 20;
               int var7 = this.a.getConfig().getInt("abilities.durations.strength-frailer-slowness", 20) * 20;
               int var8 = this.a.getConfig().getInt("abilities.strength-frailer.slowness-level", 2);
               var4.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, var10, 0, false, true));
               var4.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, var6, 0, false, true));
               var4.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, var7, Math.max(0, var8 - 1), false, true));
               Particle.DustOptions var9 = new Particle.DustOptions(ParticleUtils.STRENGTH_RED, 1.5F);
               var4.getWorld().spawnParticle(Particle.DUST, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, 0.8, (double)0.5F, (double)0.0F, var9, true);
               var4.getWorld().spawnParticle(Particle.SMOKE, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 25, (double)0.5F, 0.8, (double)0.5F, 0.05);
               var4.getWorld().spawnParticle(Particle.CRIMSON_SPORE, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F);
               var1.playSound(var1.getLocation(), Sound.ENTITY_WITHER_HURT, 1.0F, 0.8F);
               var4.getWorld().playSound(var4.getLocation(), Sound.ENTITY_WITHER_HURT, 1.0F, 0.8F);
               this.a.getAbilityManager().useAbility(var1, var2);
               this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Frailer Power");
               if (var4 instanceof Player) {
                  ((Player)var4).sendMessage("§c§oYou have been weakened by Frailer Power!");
               }

            }
         }
      }
   }

   public void c(final Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "strength-shadow-stalker";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            if (this.c.containsKey(var1.getUniqueId())) {
               this.e(var1);
               var1.sendMessage("§c§oStopped tracking.");
            } else {
               ItemStack var3 = var1.getInventory().getItemInMainHand();
               if (var3 != null && var3.getType() != Material.AIR) {
                  final UUID var4 = null;
                  if (var3.getType() == Material.PLAYER_HEAD && var3.hasItemMeta()) {
                     SkullMeta var5 = (SkullMeta)var3.getItemMeta();
                     if (var5.getOwningPlayer() != null) {
                        var4 = var5.getOwningPlayer().getUniqueId();
                     }
                  }

                  if (var4 == null) {
                     var1.sendMessage("§c§oThis is not a valid player head!");
                  } else if (var4.equals(var1.getUniqueId())) {
                     var1.sendMessage("§c§oYou cannot track yourself!");
                  } else {
                     Player var13 = Bukkit.getPlayer(var4);
                     if (var13 != null && var13.isOnline()) {
                        if (var3.getAmount() > 1) {
                           var3.setAmount(var3.getAmount() - 1);
                        } else {
                           var1.getInventory().setItemInMainHand((ItemStack)null);
                        }

                        int var6 = this.a.getConfig().getInt("abilities.strength-shadow-stalker.duration", 60);
                        final int var7 = var6 * 20;
                        final int var8 = this.a.getConfig().getInt("abilities.strength-shadow-stalker.invisibility-max-range", 2500);
                        final UUID var9 = var1.getUniqueId();
                        this.c.put(var9, var4);
                        if (this.a.getAchievementManager() != null) {
                           Map var11 = (Map)this.e.computeIfAbsent(var9, (var0) -> new HashMap());
                           int var12 = (Integer)var11.getOrDefault(var4, 0) + 1;
                           var11.put(var4, var12);
                           if (var12 >= 17) {
                              this.a.getAchievementManager().unlock(var1, Achievement.ITS_RABBIT_SEASON);
                           }
                        }

                        var13.sendMessage("§c§l⚠ §c§oYou are being hunted...");
                        var13.playSound(var13.getLocation(), Sound.ENTITY_WARDEN_NEARBY_CLOSER, 0.7F, 1.5F);
                        Particle.DustOptions var14 = new Particle.DustOptions(ParticleUtils.STRENGTH_RED, 1.5F);
                        var1.getWorld().spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var14, true);
                        var1.playSound(var1.getLocation(), Sound.ENTITY_WARDEN_SNIFF, 1.0F, 1.2F);
                        BukkitTask var15 = (new BukkitRunnable() {
                           int a = 0;

                           public void run() {
                              Player var1x = Bukkit.getPlayer(var9);
                              Player var2 = Bukkit.getPlayer(var4);
                              if (var1x != null && var1x.isOnline() && var2 != null && var2.isOnline() && this.a < var7 && g.this.c.containsKey(var9)) {
                                 ++this.a;
                                 if (this.a % 20 == 0) {
                                    boolean var3 = var1x.getWorld().equals(var2.getWorld());
                                    if (var3) {
                                       double var4x = var1x.getLocation().distance(var2.getLocation());
                                       boolean var6 = var2.hasPotionEffect(PotionEffectType.INVISIBILITY);
                                       if (var6 && var4x > (double)var8) {
                                          TextComponent var14 = new TextComponent("§c§lTRACKING §7… §c" + var2.getName() + " §7(invisible, too far)");
                                          var1x.spigot().sendMessage(ChatMessageType.ACTION_BAR, var14);
                                       } else {
                                          String var7x = g.this.a(var1x.getLocation(), var2.getLocation());
                                          TextComponent var8x = new TextComponent("§c§lTRACKING §7" + var7x + " §c" + var2.getName() + " §7" + (int)var4x + "m");
                                          var1x.spigot().sendMessage(ChatMessageType.ACTION_BAR, var8x);

                                          for(UUID var10 : g.this.a.getTrustedPlayersManager().getTrustedPlayers(var1x)) {
                                             Player var11 = Bukkit.getPlayer(var10);
                                             if (var11 != null && var11.isOnline() && var11.getWorld().equals(var1x.getWorld()) && var11.getLocation().distance(var1x.getLocation()) < (double)50.0F) {
                                                TextComponent var12 = new TextComponent("§8[§cTRACK§8] §7" + var7x + " §c" + var2.getName() + " §7" + (int)var4x + "m");
                                                var11.spigot().sendMessage(ChatMessageType.ACTION_BAR, var12);
                                             }
                                          }
                                       }

                                       if (this.a % 40 == 0) {
                                          Particle.DustOptions var15 = new Particle.DustOptions(ParticleUtils.STRENGTH_RED, 0.6F);
                                          var1x.getWorld().spawnParticle(Particle.DUST, var1x.getLocation().add((double)0.0F, 2.2, (double)0.0F), 3, 0.15, 0.1, 0.15, (double)0.0F, var15, true);
                                       }
                                    } else {
                                       TextComponent var13 = new TextComponent("§c§lTRACKING §7… §c" + var2.getName() + " §7(different dimension)");
                                       var1x.spigot().sendMessage(ChatMessageType.ACTION_BAR, var13);
                                    }
                                 }

                              } else {
                                 g.this.e(var1x != null ? var1x : var1);
                                 this.cancel();
                              }
                           }
                        }).runTaskTimer(this.a, 0L, 1L);
                        this.d.put(var9, var15);
                        this.a.getAbilityManager().useAbility(var1, var2);
                        String var10001 = var13.getName();
                        var1.sendMessage("§c§l☠ Shadow Stalker §7Tracking §c" + var10001 + " §7for " + var6 + " seconds.");
                     } else {
                        var1.sendMessage("§c§oTarget player is not online!");
                     }
                  }
               } else {
                  var1.sendMessage("§c§oHold a player head!");
               }
            }
         }
      }
   }

   private void e(Player var1) {
      if (var1 != null) {
         UUID var2 = var1.getUniqueId();
         UUID var3 = (UUID)this.c.remove(var2);
         BukkitTask var4 = (BukkitTask)this.d.remove(var2);
         if (var4 != null) {
            var4.cancel();
         }

         if (var1.isOnline()) {
            var1.sendMessage("§c§oShadow Stalker tracking ended.");
         }

         if (var3 != null) {
            Player var5 = Bukkit.getPlayer(var3);
            if (var5 != null && var5.isOnline()) {
               var5.sendMessage("§a§oYou are no longer being hunted.");
            }
         }

      }
   }

   public boolean d(Player var1) {
      return this.c.containsKey(var1.getUniqueId());
   }

   private LivingEntity b(Player var1, int var2) {
      RayTraceResult var3 = var1.getWorld().rayTraceEntities(var1.getEyeLocation(), var1.getEyeLocation().getDirection(), (double)var2, (var1x) -> var1x instanceof LivingEntity && var1x != var1);
      return var3 != null ? (LivingEntity)var3.getHitEntity() : null;
   }

   private String a(Location var1, Location var2) {
      Vector var3 = var2.toVector().subtract(var1.toVector()).normalize();
      double var4 = Math.atan2(var3.getZ(), var3.getX());
      double var6 = Math.toRadians((double)var1.getYaw());

      double var8;
      for(var8 = var4 - var6; var8 > Math.PI; var8 -= (Math.PI * 2D)) {
      }

      while(var8 < -Math.PI) {
         var8 += (Math.PI * 2D);
      }

      if (var8 > (-Math.PI / 4D) && var8 <= (Math.PI / 4D)) {
         return "→";
      } else if (var8 > (Math.PI / 4D) && var8 <= 2.356194490192345) {
         return "↓";
      } else {
         return var8 > -2.356194490192345 && var8 <= (-Math.PI / 4D) ? "↑" : "←";
      }
   }

   public void cleanup(Player var1) {
      this.e(var1);
   }

   static {
      b = Set.of(PotionEffectType.SPEED, PotionEffectType.STRENGTH, PotionEffectType.REGENERATION, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.WATER_BREATHING, PotionEffectType.INVISIBILITY, PotionEffectType.NIGHT_VISION, PotionEffectType.ABSORPTION, PotionEffectType.HEALTH_BOOST, PotionEffectType.RESISTANCE, PotionEffectType.HASTE, PotionEffectType.JUMP_BOOST, PotionEffectType.CONDUIT_POWER, PotionEffectType.DOLPHINS_GRACE, PotionEffectType.LUCK, PotionEffectType.HERO_OF_THE_VILLAGE, PotionEffectType.SLOW_FALLING, PotionEffectType.SATURATION);
   }
}


static public class h implements GemAbilityHandler {
   private final BlissGems a;
   private final Map<UUID, Inventory> b;
   private final Map<UUID, Boolean> c;
   private final File d;
   private static final Set<UUID> e = new HashSet();
   private static final Map<UUID, ItemStack> f = new HashMap();
   private final Set<UUID> g = new HashSet();
   private static final String h = "amp_orig_";
   private static final String i = "ae_orig_";
   private static final Set<UUID> j = new HashSet();

   public h(BlissGems var1) {
      this.a = var1;
      this.b = new HashMap();
      this.c = new HashMap();
      this.d = new File(var1.getDataFolder(), "pockets");
      if (!this.d.exists()) {
         this.d.mkdirs();
      }

   }

   public void a(Player var1, int var2) {
      if (var2 == 2 && var1.isSneaking()) {
         this.d(var1);
      } else {
         this.b(var1);
      }

   }

   public void onPrimary(Player var1, int var2) {
      this.b(var1);
   }

   public void onSecondary(Player var1, int var2) {
      this.d(var1);
   }

   public void onTertiary(Player var1, int var2) {
      this.c(var1);
   }

   public void onQuaternary(Player var1, int var2) {
      this.e(var1);
   }

   public void a(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         Inventory var2 = (Inventory)this.b.computeIfAbsent(var1.getUniqueId(), (var2x) -> {
            Inventory var3 = Bukkit.createInventory((InventoryHolder)null, 9, "§6§lPockets");
            this.a(var1.getUniqueId(), var3);
            return var3;
         });
         var1.openInventory(var2);
         var1.playSound(var1.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
      }
   }

   public void b(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var3 = "wealth-unfortunate";
         if (this.a.getAbilityManager().canUseAbility(var1, var3)) {
            RayTraceResult var4 = var1.getWorld().rayTraceEntities(var1.getEyeLocation(), var1.getEyeLocation().getDirection(), (double)15.0F, (var1x) -> var1x instanceof Player && var1x != var1);
            Entity var2;
            if (var4 != null && (var2 = var4.getHitEntity()) instanceof Player) {
               Player var5 = (Player)var2;
               int var6 = this.a.getConfigManager().getAbilityDuration("wealth-unfortunate");
               UUID var7 = var5.getUniqueId();
               e.add(var7);
               Bukkit.getScheduler().runTaskLater(this.a, () -> {
                  e.remove(var7);
                  if (var5.isOnline()) {
                     var5.sendMessage("§a§oUnfortunate has worn off.");
                  }

               }, (long)var6 * 20L);
               Particle.DustOptions var8 = new Particle.DustOptions(ParticleUtils.WEALTH_GREEN, 1.5F);
               var5.getWorld().spawnParticle(Particle.DUST, var5.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var8, true);
               var5.getWorld().spawnParticle(Particle.SMOKE, var5.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 20, (double)0.5F, (double)0.5F, (double)0.5F);
               var1.playSound(var1.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 1.0F, 0.8F);
               var5.sendMessage("§c§oYou've been afflicted with Unfortunate! Actions disabled for " + var6 + "s!");
               this.a.getAbilityManager().useAbility(var1, var3);
               this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Unfortunate");
            } else {
               var1.sendMessage("§cNo player target found!");
            }
         }
      }
   }

   public void c(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "wealth-item-lock";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            RayTraceResult var3 = var1.getWorld().rayTraceEntities(var1.getEyeLocation(), var1.getEyeLocation().getDirection(), (double)15.0F, (var1x) -> var1x instanceof Player && var1x != var1);
            if (var3 != null && var3.getHitEntity() instanceof Player) {
               Player var4 = (Player)var3.getHitEntity();
               ItemStack var5 = var4.getInventory().getItemInMainHand();
               if (var5 != null && !var5.getType().isAir()) {
                  UUID var6 = var4.getUniqueId();
                  f.put(var6, var5.clone());
                  int var7 = this.a.getConfig().getInt("abilities.durations.wealth-item-lock", 10);
                  Bukkit.getScheduler().runTaskLater(this.a, () -> {
                     f.remove(var6);
                     if (var4.isOnline()) {
                        var4.sendMessage("§a§oItem Lock has worn off.");
                     }

                  }, (long)var7 * 20L);
                  Particle.DustOptions var8 = new Particle.DustOptions(ParticleUtils.WEALTH_GREEN, 1.5F);
                  var4.getWorld().spawnParticle(Particle.DUST, var4.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 30, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var8, true);
                  var4.playSound(var4.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1.0F, 0.5F);
                  String var9 = var5.getType().name().toLowerCase().replace('_', ' ');
                  if (var5.hasItemMeta() && var5.getItemMeta().hasDisplayName()) {
                     var9 = var5.getItemMeta().getDisplayName();
                  }

                  var4.sendMessage("§c§oYour " + var9 + " has been locked for " + var7 + "s!");
                  this.a.getAbilityManager().useAbility(var1, var2);
                  this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Item Lock");
               } else {
                  var1.sendMessage("§cTarget isn't holding an item!");
               }
            } else {
               var1.sendMessage("§cNo player target found!");
            }
         }
      }
   }

   public void d(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "wealth-rich-rush";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            int var3 = this.a.getConfigManager().getAbilityDuration("wealth-rich-rush");
            UUID var4 = var1.getUniqueId();
            var1.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, var3 * 20, 2, false, true));
            var1.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, var3 * 20, 3, false, true));
            j.add(var4);
            Bukkit.getScheduler().runTaskLater(this.a, () -> {
               j.remove(var4);
               Player var1 = Bukkit.getPlayer(var4);
               if (var1 != null && var1.isOnline()) {
                  var1.sendMessage("§e§oRich Rush has worn off.");
               }

            }, (long)var3 * 20L);
            Particle.DustOptions var5 = new Particle.DustOptions(ParticleUtils.WEALTH_GREEN, 1.5F);
            var1.playSound(var1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
            var1.spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 50, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F, var5, true);
            var1.spawnParticle(Particle.TOTEM_OF_UNDYING, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 40, (double)0.5F, (double)0.5F, (double)0.5F);
            this.a.getAbilityManager().useAbility(var1, var2);
            this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Rich Rush");
            var1.sendMessage("§6§lRich Rush! §eMob and ore drops doubled for " + var3 + "s!");
         }
      }
   }

   public static boolean a(UUID var0) {
      return j.contains(var0);
   }

   public void e(Player var1) {
      if (this.a.getGemManager().getGemTier(var1) < 2) {
         var1.sendMessage("§c§oThis ability requires Tier 2!");
      } else {
         String var2 = "wealth-amplification";
         if (this.a.getAbilityManager().canUseAbility(var1, var2)) {
            UUID var3 = var1.getUniqueId();
            if (this.g.contains(var3)) {
               var1.sendMessage("§c§oAmplification is already active!");
            } else {
               int var4 = this.a.getConfigManager().getAbilityDuration("wealth-amplification");
               boolean var5 = false;
               ItemStack[] var6 = var1.getInventory().getArmorContents();

               for(int var7 = 0; var7 < var6.length; ++var7) {
                  if (this.c(var6[var7])) {
                     var5 = true;
                  }
               }

               var1.getInventory().setArmorContents(var6);
               ItemStack var10 = var1.getInventory().getItemInMainHand();
               if (this.c(var10)) {
                  var5 = true;
               }

               ItemStack var8 = var1.getInventory().getItemInOffHand();
               if (this.c(var8)) {
                  var5 = true;
               }

               this.g.add(var3);
               Bukkit.getScheduler().runTaskLater(this.a, () -> {
                  this.g.remove(var3);
                  Player var2 = Bukkit.getPlayer(var3);
                  if (var2 != null && var2.isOnline()) {
                     this.g(var2);
                     var2.sendMessage("§e§oAmplification has worn off. Enchantments restored.");
                  }
               }, (long)var4 * 20L);
               Particle.DustOptions var9 = new Particle.DustOptions(ParticleUtils.WEALTH_GREEN, 1.5F);
               var1.playSound(var1.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0F, 1.5F);
               var1.spawnParticle(Particle.DUST, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 100, (double)0.5F, (double)1.0F, (double)0.5F, (double)0.0F, var9, true);
               var1.spawnParticle(Particle.ENCHANT, var1.getLocation().add((double)0.0F, (double)1.0F, (double)0.0F), 80, (double)0.5F, (double)1.0F, (double)0.5F);
               if (this.a.getAchievementManager() != null && var5) {
                  this.a.getAchievementManager().unlock(var1, Achievement.BOUNDARY_BREAK);
               }

               this.a.getAbilityManager().useAbility(var1, var2);
               this.a.getConfigManager().sendFormattedMessage(var1, "ability-activated", "ability", "Amplification");
            }
         }
      }
   }

   private boolean c(ItemStack var1) {
      if (var1 != null && !var1.getType().isAir() && var1.hasItemMeta()) {
         Map var2 = var1.getEnchantments();
         if (var2.isEmpty()) {
            return false;
         } else {
            ItemMeta var3 = var1.getItemMeta();
            if (var3 == null) {
               return false;
            } else {
               PersistentDataContainer var4 = var3.getPersistentDataContainer();
               boolean var5 = false;

               for(Map.Entry var7 : var2.entrySet()) {
                  Enchantment var8 = (Enchantment)var7.getKey();
                  int var9 = (Integer)var7.getValue();
                  NamespacedKey var10 = new NamespacedKey(this.a, "ae_orig_" + var8.getKey().getKey());
                  if (!var4.has(var10, PersistentDataType.INTEGER)) {
                     NamespacedKey var11 = new NamespacedKey(this.a, "amp_orig_" + var8.getKey().getKey());
                     if (!var4.has(var11, PersistentDataType.INTEGER)) {
                        var4.set(var11, PersistentDataType.INTEGER, var9);
                        var3.addEnchant(var8, var9 + 1, true);
                        var5 = true;
                     }
                  }
               }

               if (var5) {
                  var1.setItemMeta(var3);
               }

               return var5;
            }
         }
      } else {
         return false;
      }
   }

   private void g(Player var1) {
      for(int var2 = 0; var2 < var1.getInventory().getSize(); ++var2) {
         ItemStack var3 = var1.getInventory().getItem(var2);
         if (var3 != null && !var3.getType().isAir()) {
            a(var3);
         }
      }

      ItemStack[] var5 = var1.getInventory().getArmorContents();
      boolean var6 = false;

      for(int var4 = 0; var4 < var5.length; ++var4) {
         if (var5[var4] != null && !var5[var4].getType().isAir() && a(var5[var4])) {
            var6 = true;
         }
      }

      if (var6) {
         var1.getInventory().setArmorContents(var5);
      }

      ItemStack var7 = var1.getInventory().getItemInOffHand();
      if (!var7.getType().isAir()) {
         a(var7);
      }

   }

   public static boolean a(ItemStack var0) {
      if (var0 != null && !var0.getType().isAir() && var0.hasItemMeta()) {
         ItemMeta var1 = var0.getItemMeta();
         if (var1 == null) {
            return false;
         } else {
            PersistentDataContainer var2 = var1.getPersistentDataContainer();
            boolean var3 = false;

            for(Enchantment var7 : Enchantment.values()) {
               NamespacedKey var8 = a(var0, var7);
               if (var8 != null && var2.has(var8, PersistentDataType.INTEGER)) {
                  int var9 = (Integer)var2.get(var8, PersistentDataType.INTEGER);
                  if (var9 == 0) {
                     var1.removeEnchant(var7);
                  } else {
                     var1.addEnchant(var7, var9, true);
                  }

                  var2.remove(var8);
                  var3 = true;
               }
            }

            if (var3) {
               var0.setItemMeta(var1);
            }

            return var3;
         }
      } else {
         return false;
      }
   }

   public static boolean b(ItemStack var0) {
      if (var0 != null && var0.hasItemMeta()) {
         PersistentDataContainer var1 = var0.getItemMeta().getPersistentDataContainer();

         for(Enchantment var5 : Enchantment.values()) {
            NamespacedKey var6 = a(var0, var5);
            if (var6 != null && var1.has(var6, PersistentDataType.INTEGER)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static NamespacedKey a(ItemStack var0, Enchantment var1) {
      return !var0.hasItemMeta() ? null : NamespacedKey.fromString("blissgems:amp_orig_" + var1.getKey().getKey());
   }

   public static boolean b(UUID var0) {
      return e.contains(var0);
   }

   public static boolean a(UUID var0, double var1) {
      return e.contains(var0) && Math.random() < var1;
   }

   public static boolean c(UUID var0) {
      return f.containsKey(var0);
   }

   public static ItemStack d(UUID var0) {
      return (ItemStack)f.get(var0);
   }

   public void cleanup(Player var1) {
      UUID var2 = var1.getUniqueId();
      if (this.g.remove(var2)) {
         this.g(var1);
      }

      e.remove(var2);
      f.remove(var2);
      j.remove(var2);
   }

   public Inventory e(UUID var1) {
      return (Inventory)this.b.get(var1);
   }

   public boolean f(Player var1) {
      return (Boolean)this.c.getOrDefault(var1.getUniqueId(), false);
   }

   public void a(Player var1, boolean var2) {
      this.c.put(var1.getUniqueId(), var2);
   }

   public void f(UUID var1) {
      Inventory var2 = (Inventory)this.b.get(var1);
      if (var2 != null) {
         File var3 = new File(this.d, var1.toString() + ".yml");
         YamlConfiguration var4 = new YamlConfiguration();

         for(int var5 = 0; var5 < var2.getSize(); ++var5) {
            ItemStack var6 = var2.getItem(var5);
            if (var6 != null && !var6.getType().isAir()) {
               ((FileConfiguration)var4).set("items." + var5, var6);
            }
         }

         try {
            ((FileConfiguration)var4).save(var3);
         } catch (IOException var7) {
            Logger var10000 = this.a.getLogger();
            String var10001 = String.valueOf(var1);
            var10000.severe("Failed to save pockets inventory for " + var10001 + ": " + var7.getMessage());
         }

      }
   }

   private void a(UUID var1, Inventory var2) {
      File var3 = new File(this.d, var1.toString() + ".yml");
      if (var3.exists()) {
         YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
         if (((FileConfiguration)var4).contains("items")) {
            for(String var6 : ((FileConfiguration)var4).getConfigurationSection("items").getKeys(false)) {
               try {
                  int var7 = Integer.parseInt(var6);
                  ItemStack var8 = ((FileConfiguration)var4).getItemStack("items." + var6);
                  if (var8 != null && var7 >= 0 && var7 < var2.getSize()) {
                     var2.setItem(var7, var8);
                  }
               } catch (NumberFormatException var9) {
                  this.a.getLogger().warning("Invalid slot key in pockets data: " + var6);
               }
            }
         }

      }
   }

   public void a() {
      for(UUID var2 : this.b.keySet()) {
         this.f(var2);
      }

   }
}
    }

    static final class P_dev_xoperr_blissgems_core_b {


static public class a {
   private static final EquipmentSlot[] a;
   private final Plugin b;
   private final Map<String, dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_a.b> c;
   private final NamespacedKey d;
   private BukkitTask e;
   private boolean f;

   public a(Plugin var1) {
      this.b = var1;
      this.c = new HashMap();
      this.d = new NamespacedKey(var1, "auto_enchant");
      this.f = true;
      this.c();
   }

   public void a(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_a.b var1) {
      if (var1 != null && var1.a() != null) {
         this.c.put(var1.a(), var1);
      }
   }

   public void a(String var1) {
      this.c.remove(var1);
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_a.b b(String var1) {
      return (dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_a.b)this.c.get(var1);
   }

   public boolean c(String var1) {
      return this.c.containsKey(var1);
   }

   public void a(Player var1) {
      if (this.f && var1 != null) {
         Set var2 = this.c(var1);

         for(EquipmentSlot var6 : a) {
            this.a(var1, var6, var2);
         }

      }
   }

   private Set<String> c(Player var1) {
      HashSet var2 = new HashSet();

      for(ItemStack var6 : var1.getInventory().getContents()) {
         if (var6 != null && dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_c.a.c(var6)) {
            String var7 = dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_c.a.d(var6);
            if (var7 != null && this.c.containsKey(var7)) {
               var2.add(var7);
            }
         }
      }

      return var2;
   }

   private void a(Player var1, EquipmentSlot var2, Set<String> var3) {
      ItemStack var4 = var1.getInventory().getItem(var2);
      if (var4 != null && !var4.getType().isAir()) {
         HashMap var5 = new HashMap();

         for(String var7 : var3) {
            dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_a.b var8 = (dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_a.b)this.c.get(var7);
            if (var8 != null && var8.b(var2)) {
               Map var9 = var8.a(var2);

               for(Map.Entry var11 : var9.entrySet()) {
                  Enchantment var12 = (Enchantment)var11.getKey();
                  int var13 = (Integer)var11.getValue();
                  var5.merge(var12, var13, Math::max);
               }
            }
         }

         this.a(var4, var5);
         this.b(var4, var5);
      }
   }

   private void a(ItemStack var1, Map<Enchantment, Integer> var2) {
      ItemMeta var3 = var1.getItemMeta();
      if (var3 != null) {
         PersistentDataContainer var4 = var3.getPersistentDataContainer();
         Map var5 = var3.getEnchants();

         for(Enchantment var7 : new HashSet(var5.keySet())) {
            NamespacedKey var8 = new NamespacedKey(this.b, "auto_" + var7.getKey().getKey());
            if (var4.has(var8, PersistentDataType.BYTE) && !var2.containsKey(var7)) {
               var3.removeEnchant(var7);
               var4.remove(var8);
            }
         }

         var1.setItemMeta(var3);
      }
   }

   private void b(ItemStack var1, Map<Enchantment, Integer> var2) {
      if (!var2.isEmpty()) {
         ItemMeta var3 = var1.getItemMeta();
         if (var3 != null) {
            PersistentDataContainer var4 = var3.getPersistentDataContainer();
            boolean var5 = false;

            for(Map.Entry var7 : var2.entrySet()) {
               Enchantment var8 = (Enchantment)var7.getKey();
               int var9 = (Integer)var7.getValue();
               int var10 = var3.getEnchantLevel(var8);
               NamespacedKey var11 = new NamespacedKey(this.b, "auto_" + var8.getKey().getKey());
               boolean var12 = var4.has(var11, PersistentDataType.BYTE);
               if (var10 == 0) {
                  var3.addEnchant(var8, var9, true);
                  var4.set(var11, PersistentDataType.BYTE, (byte)1);
                  var5 = true;
               } else if (var12 && var10 != var9) {
                  var3.removeEnchant(var8);
                  var3.addEnchant(var8, var9, true);
                  var5 = true;
               }
            }

            if (var5) {
               var1.setItemMeta(var3);
            }

         }
      }
   }

   public void b(Player var1) {
      for(EquipmentSlot var5 : a) {
         ItemStack var6 = var1.getInventory().getItem(var5);
         if (var6 != null) {
            this.a(var6);
         }
      }

   }

   private void a(ItemStack var1) {
      ItemMeta var2 = var1.getItemMeta();
      if (var2 != null) {
         PersistentDataContainer var3 = var2.getPersistentDataContainer();
         Map var4 = var2.getEnchants();
         boolean var5 = false;

         for(Enchantment var7 : new HashSet(var4.keySet())) {
            NamespacedKey var8 = new NamespacedKey(this.b, "auto_" + var7.getKey().getKey());
            if (var3.has(var8, PersistentDataType.BYTE)) {
               var2.removeEnchant(var7);
               var3.remove(var8);
               var5 = true;
            }
         }

         if (var5) {
            var1.setItemMeta(var2);
         }

      }
   }

   private void c() {
      this.e = this.b.getServer().getScheduler().runTaskTimer(this.b, () -> {
         if (this.f) {
            for(Player var2 : this.b.getServer().getOnlinePlayers()) {
               this.a(var2);
            }
         }

      }, 20L, 10L);
   }

   public void a(boolean var1) {
      this.f = var1;
      if (!var1) {
         for(Player var3 : this.b.getServer().getOnlinePlayers()) {
            this.b(var3);
         }
      }

   }

   public boolean a() {
      return this.f;
   }

   public void b() {
      if (this.e != null) {
         this.e.cancel();
      }

      for(Player var2 : this.b.getServer().getOnlinePlayers()) {
         this.b(var2);
      }

      this.c.clear();
   }

   static {
      a = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HAND, EquipmentSlot.OFF_HAND};
   }
}


static public class b {
   private final Plugin a;
   private final List<dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.a> b;

   public b(Plugin var1) {
      this.a = var1;
      this.b = new ArrayList();
      this.d();
   }

   public dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.a a(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.c var1, Location var2) {
      if (var2 != null && var2.getWorld() != null) {
         ItemDisplay var3 = (ItemDisplay)var2.getWorld().spawn(var2, ItemDisplay.class, (var1x) -> {
            ItemStack var2 = var1.k();
            var1x.setItemStack(var2);
            var1x.setBillboard(var1.j());
            var1x.setBrightness(new Display.Brightness(var1.e(), var1.f()));
            var1x.setViewRange(var1.g());
            var1x.setInterpolationDuration(var1.h());
            var1x.setInterpolationDelay(var1.i());
            float var3 = var1.d();
            Transformation var4 = new Transformation(new Vector3f(0.0F, 0.0F, 0.0F), new AxisAngle4f(0.0F, 0.0F, 0.0F, 1.0F), new Vector3f(var3, var3, var3), new AxisAngle4f(0.0F, 0.0F, 0.0F, 1.0F));
            var1x.setTransformation(var4);
         });
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.a var4 = new dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.a(var3, var1.a());
         this.b.add(var4);
         return var4;
      } else {
         throw new IllegalArgumentException("Location and world cannot be null");
      }
   }

   public void a() {
      for(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.a var2 : new ArrayList(this.b)) {
         var2.d();
      }

      this.b.clear();
   }

   public int b() {
      this.e();
      return this.b.size();
   }

   public void c() {
      this.a();
   }

   private void d() {
      this.a.getServer().getScheduler().runTaskTimer(this.a, this::e, 100L, 100L);
   }

   private void e() {
      Iterator var1 = this.b.iterator();

      while(var1.hasNext()) {
         dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.a var2 = (dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.a)var1.next();
         if (!var2.b()) {
            var1.remove();
         }
      }

   }
}


static public class c {
   private final Plugin a;
   private final NamespacedKey b;
   private final NamespacedKey c;
   private final NamespacedKey d;

   public c(Plugin var1) {
      this.a = var1;
      this.b = new NamespacedKey(var1, "is_gem");
      this.c = new NamespacedKey(var1, "gem_id");
      this.d = new NamespacedKey(var1, "gem_tier");
   }

   public boolean a(ItemStack var1) {
      if (var1 != null && var1.getType() != Material.AIR) {
         if (this.c(var1)) {
            return false;
         } else {
            ItemMeta var2 = var1.hasItemMeta() ? var1.getItemMeta() : this.a.getServer().getItemFactory().getItemMeta(var1.getType());
            if (var2 == null) {
               return false;
            } else {
               PersistentDataContainer var3 = var2.getPersistentDataContainer();
               var3.set(this.b, PersistentDataType.BYTE, (byte)1);
               var1.setItemMeta(var2);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean a(ItemStack var1, String var2, int var3) {
      if (var1 != null && var1.getType() != Material.AIR) {
         ItemMeta var4 = var1.hasItemMeta() ? var1.getItemMeta() : this.a.getServer().getItemFactory().getItemMeta(var1.getType());
         if (var4 == null) {
            return false;
         } else {
            PersistentDataContainer var5 = var4.getPersistentDataContainer();
            var5.set(this.b, PersistentDataType.BYTE, (byte)1);
            if (var2 != null) {
               var5.set(this.c, PersistentDataType.STRING, var2);
            }

            var5.set(this.d, PersistentDataType.INTEGER, var3);
            var1.setItemMeta(var4);
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean b(ItemStack var1) {
      if (var1 != null && var1.getType() != Material.AIR) {
         if (!var1.hasItemMeta()) {
            return false;
         } else {
            ItemMeta var2 = var1.getItemMeta();
            if (var2 == null) {
               return false;
            } else {
               PersistentDataContainer var3 = var2.getPersistentDataContainer();
               if (!var3.has(this.b, PersistentDataType.BYTE)) {
                  return false;
               } else {
                  var3.remove(this.b);
                  var3.remove(this.c);
                  var3.remove(this.d);
                  var1.setItemMeta(var2);
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   public boolean c(ItemStack var1) {
      if (var1 != null && var1.getType() != Material.AIR) {
         if (!var1.hasItemMeta()) {
            return false;
         } else {
            ItemMeta var2 = var1.getItemMeta();
            if (var2 == null) {
               return false;
            } else {
               PersistentDataContainer var3 = var2.getPersistentDataContainer();
               return var3.has(this.b, PersistentDataType.BYTE) && (Byte)var3.get(this.b, PersistentDataType.BYTE) == 1;
            }
         }
      } else {
         return false;
      }
   }

   public String d(ItemStack var1) {
      if (!this.c(var1)) {
         return null;
      } else {
         ItemMeta var2 = var1.getItemMeta();
         if (var2 == null) {
            return null;
         } else {
            PersistentDataContainer var3 = var2.getPersistentDataContainer();
            return (String)var3.get(this.c, PersistentDataType.STRING);
         }
      }
   }

   public int e(ItemStack var1) {
      if (!this.c(var1)) {
         return 0;
      } else {
         ItemMeta var2 = var1.getItemMeta();
         if (var2 == null) {
            return 0;
         } else {
            PersistentDataContainer var3 = var2.getPersistentDataContainer();
            Integer var4 = (Integer)var3.get(this.d, PersistentDataType.INTEGER);
            return var4 != null ? var4 : 0;
         }
      }
   }
}


static public class d {
   private final Plugin a;
   private final boolean b;
   private final boolean c;

   public d(Plugin var1) {
      this.a = var1;
      this.b = this.c();
      this.c = var1.getConfig().getBoolean("worldguard.enabled", true);
      if (this.b) {
         var1.getLogger().info("WorldGuard detected - region protection enabled");
      } else {
         var1.getLogger().info("WorldGuard not found - region protection disabled");
      }

   }

   private boolean c() {
      try {
         Class.forName("com.sk89q.worldguard.WorldGuard");
         return this.a.getServer().getPluginManager().getPlugin("WorldGuard") != null;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   public boolean a(Player var1) {
      return this.a(var1.getLocation());
   }

   public boolean a(Location var1) {
      if (this.b && this.c) {
         try {
            RegionManager var2 = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(var1.getWorld()));
            if (var2 == null) {
               return false;
            } else {
               ApplicableRegionSet var3 = var2.getApplicableRegions(BukkitAdapter.asBlockVector(var1));
               return this.a(var3);
            }
         } catch (Exception var4) {
            this.a.getLogger().warning("Error checking WorldGuard regions: " + var4.getMessage());
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean a(ApplicableRegionSet var1) {
      String var2 = this.a.getConfig().getString("worldguard.mode", "blacklist");
      List var3 = this.a.getConfig().getStringList("worldguard.regions");

      for(ProtectedRegion var5 : var1) {
         String var6 = var5.getId();
         if (var2.equalsIgnoreCase("whitelist")) {
            if (!var3.contains(var6)) {
               return true;
            }
         } else if (var2.equalsIgnoreCase("blacklist") && var3.contains(var6)) {
            return true;
         }
      }

      return false;
   }

   public String a() {
      return this.a.getConfig().getString("worldguard.disabled-message", "&cGem abilities are disabled in this region!");
   }

   public boolean b() {
      return this.b;
   }
}


static public class e {
   private final Plugin a;
   private final List<dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_e.b> b;
   private BukkitTask c;

   public e(Plugin var1) {
      this.a = var1;
      this.b = new ArrayList();
      this.b();
   }

   public void a(Player var1, String var2) {
      if (var1 != null && var2 != null) {
         var1.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(var2));
      }
   }

   public void a(Player var1) {
      if (var1 != null) {
         var1.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
      }
   }

   public void a(Player var1, String var2, String var3, int var4, int var5, int var6) {
      if (var1 != null && var2 != null) {
         var1.sendTitle(var2, var3, var4, var5, var6);
      }
   }

   public void b(Player var1) {
      if (var1 != null) {
         var1.resetTitle();
      }
   }

   public void a(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_e.b var1) {
      if (var1 != null) {
         this.b.add(var1);
         this.b.sort(Comparator.comparingInt(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_e.b::a).reversed());
      }
   }

   public void b(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_e.b var1) {
      this.b.remove(var1);
   }

   private void b() {
      this.c = this.a.getServer().getScheduler().runTaskTimer(this.a, this::c, 0L, 5L);
   }

   private void c() {
      for(Player var2 : this.a.getServer().getOnlinePlayers()) {
         this.c(var2);
      }

   }

   private void c(Player var1) {
      ItemStack var2 = var1.getInventory().getItemInMainHand();
      ItemStack var3 = var1.getInventory().getItemInOffHand();

      for(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_e.b var5 : this.b) {
         if (var5.b(var1, var2, var3)) {
            String var6 = var5.a(var1, var2, var3);
            if (var6 != null) {
               this.a(var1, var6);
               return;
            }
         }
      }

   }

   public void a() {
      if (this.c != null) {
         this.c.cancel();
      }

      this.b.clear();
   }
}
    }

    static final class P_dev_xoperr_blissgems_core_listeners {


static public class InventoryInteractListener implements Listener {
   private final c protectionManager;
   private final Set<InventoryType> restrictedInventories;
   private final Set<InventoryType> specialToolInventories;

   public InventoryInteractListener(c var1) {
      this.protectionManager = var1;
      this.restrictedInventories = new HashSet(Arrays.asList(InventoryType.CHEST, InventoryType.ENDER_CHEST, InventoryType.DISPENSER, InventoryType.DROPPER, InventoryType.HOPPER, InventoryType.FURNACE, InventoryType.BREWING, InventoryType.BARREL, InventoryType.SHULKER_BOX, InventoryType.BLAST_FURNACE, InventoryType.SMOKER, InventoryType.LECTERN, InventoryType.STONECUTTER, InventoryType.COMPOSTER, InventoryType.CHISELED_BOOKSHELF));
      this.specialToolInventories = new HashSet(Arrays.asList(InventoryType.GRINDSTONE, InventoryType.ENCHANTING, InventoryType.ANVIL, InventoryType.SMITHING, InventoryType.MERCHANT, InventoryType.LOOM, InventoryType.CARTOGRAPHY));
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onInventoryClick(InventoryClickEvent var1) {
      if (var1.getWhoClicked() instanceof Player) {
         Player var2 = (Player)var1.getWhoClicked();
         ItemStack var3 = var1.getCurrentItem();
         ItemStack var4 = var1.getCursor();
         if (var1.getClickedInventory() == null || var1.getClickedInventory().getType() == InventoryType.PLAYER || var4 == null || var4.getType().isAir() || !this.protectionManager.c(var4) || !this.restrictedInventories.contains(var1.getClickedInventory().getType()) && !this.specialToolInventories.contains(var1.getClickedInventory().getType())) {
            if (var1.getClickedInventory() != null && var1.isShiftClick() && var1.getClickedInventory().getType() == InventoryType.PLAYER && var3 != null && !var3.getType().isAir() && var1.getView().getTopInventory().getType() != InventoryType.CRAFTING && this.protectionManager.c(var3) && (this.restrictedInventories.contains(var1.getView().getTopInventory().getType()) || this.specialToolInventories.contains(var1.getView().getTopInventory().getType()))) {
               var1.setCancelled(true);
            }

         } else {
            var1.setCancelled(true);
         }
      }
   }
}


static public class ItemDropListener implements Listener {
   private final c protectionManager;

   public ItemDropListener(c var1) {
      this.protectionManager = var1;
   }

   @EventHandler(
      priority = EventPriority.HIGH,
      ignoreCancelled = true
   )
   public void onItemDrop(PlayerDropItemEvent var1) {
      Player var2 = var1.getPlayer();
      ItemStack var3 = var1.getItemDrop().getItemStack();
      if (this.protectionManager.c(var3)) {
         var1.setCancelled(true);
      }

   }
}
    }

    static final class P_dev_xoperr_blissgems_core_a_b {


static public class a {
   private final ItemDisplay a;
   private final String b;
   private boolean c;

   public a(ItemDisplay var1, String var2) {
      this.a = var1;
      this.b = var2;
      this.c = true;
   }

   public String a() {
      return this.b;
   }

   public boolean b() {
      return this.c && this.a != null && this.a.isValid();
   }

   public Location c() {
      return this.a != null ? this.a.getLocation() : null;
   }

   public void a(Location var1) {
      if (this.a != null && this.c) {
         this.a.teleport(var1);
      }

   }

   public void a(float var1) {
      if (this.a != null && this.c) {
         Transformation var2 = this.a.getTransformation();
         this.a.setTransformation(new Transformation(var2.getTranslation(), var2.getLeftRotation(), new Vector3f(var1, var1, var1), var2.getRightRotation()));
      }

   }

   public void a(int var1, int var2) {
      if (this.a != null && this.c) {
         this.a.setBrightness(new Display.Brightness(var1, var2));
      }

   }

   public void b(float var1) {
      if (this.a != null && this.c) {
         this.a.setViewRange(var1);
      }

   }

   public void a(int var1) {
      if (this.a != null && this.c) {
         this.a.setInterpolationDuration(var1);
      }

   }

   public void b(int var1) {
      if (this.a != null && this.c) {
         this.a.setInterpolationDelay(var1);
      }

   }

   public void d() {
      if (this.a != null && this.c) {
         this.a.remove();
         this.c = false;
      }

   }

   public ItemDisplay e() {
      return this.a;
   }
}


static public class b {
   private static dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.b a;

   public static void a(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.b var0) {
      a = var0;
   }

   public static c a(String var0) {
      c();
      return new c(var0);
   }

   static a a(c var0, Location var1) {
      c();
      return a.a(var0, var1);
   }

   public static void a() {
      c();
      a.a();
   }

   public static int b() {
      c();
      return a.b();
   }

   private static void c() {
      if (a == null) {
         throw new IllegalStateException("ParticleAPI has not been initialized! Ensure XoperrCore is loaded.");
      }
   }
}


static public class c {
   private final String a;
   private Material b;
   private int c;
   private float d;
   private int e;
   private int f;
   private float g;
   private int h;
   private int i;
   private Display.Billboard j;

   public c(String var1) {
      this.b = Material.PAPER;
      this.c = 0;
      this.d = 1.0F;
      this.e = 15;
      this.f = 15;
      this.g = 32.0F;
      this.h = 0;
      this.i = 0;
      this.j = Billboard.CENTER;
      this.a = var1;
   }

   public c a(Material var1) {
      this.b = var1;
      return this;
   }

   public c a(int var1) {
      this.c = var1;
      return this;
   }

   public c a(float var1) {
      this.d = var1;
      return this;
   }

   public c a(int var1, int var2) {
      this.e = Math.max(0, Math.min(15, var1));
      this.f = Math.max(0, Math.min(15, var2));
      return this;
   }

   public c b(float var1) {
      this.g = var1;
      return this;
   }

   public c b(int var1) {
      this.h = var1;
      return this;
   }

   public c c(int var1) {
      this.i = var1;
      return this;
   }

   public c a(Display.Billboard var1) {
      this.j = var1;
      return this;
   }

   public a a(Location var1) {
      return dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_a_b.b.a(this, var1);
   }

   public String a() {
      return this.a;
   }

   public Material b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public float d() {
      return this.d;
   }

   public int e() {
      return this.e;
   }

   public int f() {
      return this.f;
   }

   public float g() {
      return this.g;
   }

   public int h() {
      return this.h;
   }

   public int i() {
      return this.i;
   }

   public Display.Billboard j() {
      return this.j;
   }

   public ItemStack k() {
      ItemStack var1 = new ItemStack(this.b);
      if (this.c > 0) {
         ItemMeta var2 = var1.getItemMeta();
         if (var2 != null) {
            var2.setCustomModelData(this.c);
            var1.setItemMeta(var2);
         }
      }

      return var1;
   }
}
    }

    static final class P_dev_xoperr_blissgems_core_a_c {


static public class a {
   private static c a;

   public static void a(c var0) {
      a = var0;
   }

   public static boolean a(ItemStack var0) {
      a();
      return a.a(var0);
   }

   public static boolean b(ItemStack var0) {
      a();
      return a.b(var0);
   }

   public static boolean c(ItemStack var0) {
      a();
      return a.c(var0);
   }

   public static boolean a(ItemStack var0, String var1, int var2) {
      a();
      return a.a(var0, var1, var2);
   }

   public static String d(ItemStack var0) {
      a();
      return a.d(var0);
   }

   public static int e(ItemStack var0) {
      a();
      return a.e(var0);
   }

   private static void a() {
      if (a == null) {
         throw new IllegalStateException("GemProtectionAPI has not been initialized! Ensure XoperrCore is loaded.");
      }
   }
}
    }

    static final class P_dev_xoperr_blissgems_core_a_e {


static public class a {
   private static e a;

   public static void a(e var0) {
      a = var0;
   }

   public static void a(Player var0, String var1) {
      a();
      a.a(var0, var1);
   }

   public static void a(Player var0) {
      a();
      a.a(var0);
   }

   public static void a(Player var0, String var1, String var2, int var3, int var4, int var5) {
      a();
      a.a(var0, var1, var2, var3, var4, var5);
   }

   public static void b(Player var0) {
      a();
      a.b(var0);
   }

   public static void a(b var0) {
      a();
      a.a(var0);
   }

   public static void b(b var0) {
      a();
      a.b(var0);
   }

   private static void a() {
      if (a == null) {
         throw new IllegalStateException("InventoryTextAPI has not been initialized! Ensure XoperrCore is loaded.");
      }
   }
}


static public interface b {
   String a(Player var1, ItemStack var2, ItemStack var3);

   default boolean b(Player var1, ItemStack var2, ItemStack var3) {
      return true;
   }

   default int a() {
      return 0;
   }
}
    }

    static final class P_dev_xoperr_blissgems_core_a_a {


static public class a {
   private static dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.a a;

   public static void a(dev.xoperr.blissgems.BlissGems.BlissGems.P_dev_xoperr_blissgems_core_b.a var0) {
      a = var0;
   }

   public static void a(b var0) {
      b();
      a.a(var0);
   }

   public static void a(String var0) {
      b();
      a.a(var0);
   }

   public static b b(String var0) {
      b();
      return a.b(var0);
   }

   public static boolean c(String var0) {
      b();
      return a.c(var0);
   }

   public static void a(Player var0) {
      b();
      a.a(var0);
   }

   public static void b(Player var0) {
      b();
      a.b(var0);
   }

   public static void a(boolean var0) {
      b();
      a.a(var0);
   }

   public static boolean a() {
      b();
      return a.a();
   }

   private static void b() {
      if (a == null) {
         throw new IllegalStateException("AutoEnchantAPI has not been initialized! Ensure XoperrCore is loaded.");
      }
   }
}


static public class b {
   private final String a;
   private final Map<EquipmentSlot, Map<Enchantment, Integer>> b;
   private boolean c;
   private int d;

   public b(String var1) {
      this.a = var1;
      this.b = new EnumMap(EquipmentSlot.class);
      this.c = false;
      this.d = 0;
   }

   public b a(EquipmentSlot var1, Enchantment var2, int var3) {
      ((Map)this.b.computeIfAbsent(var1, (var0) -> new HashMap())).put(var2, var3);
      return this;
   }

   public b a(Enchantment var1, int var2) {
      this.a(EquipmentSlot.HEAD, var1, var2);
      this.a(EquipmentSlot.CHEST, var1, var2);
      this.a(EquipmentSlot.LEGS, var1, var2);
      this.a(EquipmentSlot.FEET, var1, var2);
      return this;
   }

   public b b(Enchantment var1, int var2) {
      this.a(EquipmentSlot.HAND, var1, var2);
      return this;
   }

   public b a(boolean var1) {
      this.c = var1;
      return this;
   }

   public b a(int var1) {
      this.d = var1;
      return this;
   }

   public String a() {
      return this.a;
   }

   public Map<EquipmentSlot, Map<Enchantment, Integer>> b() {
      return Collections.unmodifiableMap(this.b);
   }

   public Map<Enchantment, Integer> a(EquipmentSlot var1) {
      return (Map)this.b.getOrDefault(var1, Collections.emptyMap());
   }

   public boolean c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public boolean b(EquipmentSlot var1) {
      return this.b.containsKey(var1) && !((Map)this.b.get(var1)).isEmpty();
   }
}
    }

    static final class P_dev_xoperr_blissgems_core_a_d {


static public class a {
   private static d a;

   public static void a(d var0) {
      a = var0;
   }

   public static boolean a(Player var0) {
      c();
      return a.a(var0);
   }

   public static boolean a(Location var0) {
      c();
      return a.a(var0);
   }

   public static String a() {
      c();
      return a.a();
   }

   public static boolean b() {
      c();
      return a.b();
   }

   private static void c() {
      if (a == null) {
         throw new IllegalStateException("RegionAPI has not been initialized!");
      }
   }
}
    }

    static final class P_dev_xoperr_blissgems_utils {

static public enum AbilityBinding {
   RIGHT_CLICK("right_click", "Right Click", false),
   SHIFT_RIGHT_CLICK("shift_right_click", "Shift + Right Click", true),
   LEFT_CLICK("left_click", "Left Click", false),
   SHIFT_LEFT_CLICK("shift_left_click", "Shift + Left Click", true),
   SWAP_HAND("swap_hand", "F (Swap Offhand)", false),
   SHIFT_SWAP_HAND("shift_swap_hand", "Shift + F", true);

   private final String a;
   private final String b;
   private final boolean c;

   private AbilityBinding(String var3, String var4, boolean var5) {
      this.a = var3;
      this.b = var4;
      this.c = var5;
   }

   public String getId() {
      return this.a;
   }

   public String getDisplayName() {
      return this.b;
   }

   public boolean requiresSneak() {
      return this.c;
   }

   public static AbilityBinding fromId(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.toLowerCase().replace('-', '_');

         for(AbilityBinding var5 : values()) {
            if (var5.a.equals(var1)) {
               return var5;
            }
         }

         return null;
      }
   }

   public static AbilityBinding rightClick(boolean var0) {
      return var0 ? SHIFT_RIGHT_CLICK : RIGHT_CLICK;
   }

   public static AbilityBinding leftClick(boolean var0) {
      return var0 ? SHIFT_LEFT_CLICK : LEFT_CLICK;
   }

   public static AbilityBinding swapHand(boolean var0) {
      return var0 ? SHIFT_SWAP_HAND : SWAP_HAND;
   }

   // $FF: synthetic method
   private static AbilityBinding[] a() {
      return new AbilityBinding[]{RIGHT_CLICK, SHIFT_RIGHT_CLICK, LEFT_CLICK, SHIFT_LEFT_CLICK, SWAP_HAND, SHIFT_SWAP_HAND};
   }
}

static public enum AbilitySlot {
   PRIMARY("primary", "Primary"),
   SECONDARY("secondary", "Secondary"),
   TERTIARY("tertiary", "Tertiary"),
   QUATERNARY("quaternary", "Quaternary");

   private final String a;
   private final String b;

   private AbilitySlot(String var3, String var4) {
      this.a = var3;
      this.b = var4;
   }

   public String getId() {
      return this.a;
   }

   public String getDisplayName() {
      return this.b;
   }

   public static AbilitySlot fromId(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.toLowerCase();

         for(AbilitySlot var5 : values()) {
            if (var5.a.equals(var1)) {
               return var5;
            }
         }

         if (var1.equals("main")) {
            return PRIMARY;
         } else {
            return null;
         }
      }
   }

   // $FF: synthetic method
   private static AbilitySlot[] a() {
      return new AbilitySlot[]{PRIMARY, SECONDARY, TERTIARY, QUATERNARY};
   }
}

static public enum Achievement {
   SHATTERED("Shattered", "Reach the Broken energy stage", 1),
   ZIP_AWAY("Zip Away", "Travel across 1,914 copper blocks using Conduction", 1914),
   PIERCING_PRECISION("Piercing Precision", "Hit a player with 5 consecutive Phantom Daggers", 5),
   SAVED_BY_THE_DICE("Saved By The Dice", "Avoid a fatal blow with Phasing", 1),
   OVERFLOWING("Overflowing", "Reach the Pristine +5 energy stage", 1),
   ALMOST_FELL_FROM_GRACE("Almost Fell From Grace", "Prevent 30,000 points of fall damage with Puff Gem", 30000),
   GOOD_AS_NEW("Good As New!", "Draw 10 energy from the Energy Pool using a Repair Kit", 10),
   REAWAKENING("Reawakening", "Successfully complete a Restoration Ritual", 1),
   ITS_RABBIT_SEASON("It's Rabbit Season", "Track the same player with Shadow Stalker 17 times", 17),
   TIME_FOR_A_CHANGE("Time For A Change", "Use a Trader to switch gem types", 1),
   THE_NEXT_LEVEL("The Next Level", "Use an Upgrader to obtain a Tier 2 gem", 1),
   OVERCHARGED("Overcharged", "Charge a Flux Gem to 100W", 1),
   BOUNDARY_BREAK("Boundary Break", "Boost enchantments beyond vanilla levels using Amplification", 1);

   private final String a;
   private final String b;
   private final int c;

   private Achievement(String var3, String var4, int var5) {
      this.a = var3;
      this.b = var4;
      this.c = var5;
   }

   public String getDisplayName() {
      return this.a;
   }

   public String getDescription() {
      return this.b;
   }

   public int getTargetProgress() {
      return this.c;
   }

   // $FF: synthetic method
   private static Achievement[] a() {
      return new Achievement[]{SHATTERED, ZIP_AWAY, PIERCING_PRECISION, SAVED_BY_THE_DICE, OVERFLOWING, ALMOST_FELL_FROM_GRACE, GOOD_AS_NEW, REAWAKENING, ITS_RABBIT_SEASON, TIME_FOR_A_CHANGE, THE_NEXT_LEVEL, OVERCHARGED, BOUNDARY_BREAK};
   }
}


static public class ConfigManager {
   private final BlissGems a;
   private FileConfiguration b;
   private static final String c = "1.0.0";

   public ConfigManager(BlissGems var1) {
      this.a = var1;
      this.reload();
      this.a();
   }

   public void reload() {
      this.a.reloadConfig();
      this.b = this.a.getConfig();
   }

   private void a() {
      File var1 = new File(this.a.getDataFolder(), "config.yml");
      if (var1.exists()) {
         YamlConfiguration var2 = YamlConfiguration.loadConfiguration(new InputStreamReader(this.a.getResource("config.yml"), StandardCharsets.UTF_8));
         String var3 = this.b.getString("config-version", "unknown");
         String var4 = ((FileConfiguration)var2).getString("config-version", "1.0.0");
         boolean var5 = !var3.equals(var4);
         boolean var6 = false;
         int var7 = 0;
         Set var8 = ((FileConfiguration)var2).getKeys(true);

         for(String var10 : var8) {
            if (!((FileConfiguration)var2).isConfigurationSection(var10) && !this.b.contains(var10)) {
               var6 = true;
               ++var7;
            }
         }

         if (!var6) {
            if (var5) {
               this.b.set("config-version", var4);

               try {
                  this.b.save(var1);
                  this.a.getLogger().info("Updated config version from " + var3 + " to " + var4);
               } catch (IOException var11) {
                  this.a.getLogger().warning("Failed to update config version: " + var11.getMessage());
               }
            }

         } else {
            try {
               File var14 = new File(this.a.getDataFolder(), "config.yml.backup");
               Files.copy(var1.toPath(), var14.toPath(), StandardCopyOption.REPLACE_EXISTING);
               this.a.getLogger().info("Created config backup: config.yml.backup");
            } catch (IOException var13) {
               this.a.getLogger().warning("Failed to backup config: " + var13.getMessage());
            }

            if (var5) {
               this.a.getLogger().warning("Detected outdated config! (v" + var3 + " -> v" + var4 + ")");
            }

            this.a.getLogger().warning("Auto-repairing config: " + var7 + " missing entries detected.");

            for(String var16 : var8) {
               if (!((FileConfiguration)var2).isConfigurationSection(var16) && !this.b.contains(var16)) {
                  this.b.set(var16, ((FileConfiguration)var2).get(var16));
                  this.a.getLogger().info("  + Added: " + var16);
               }
            }

            this.b.set("config-version", var4);

            try {
               this.b.save(var1);
               this.a.getLogger().info("Config auto-repair complete! " + var7 + " keys added.");
               this.a.getLogger().info("Your old config was backed up to config.yml.backup");
               this.reload();
            } catch (IOException var12) {
               this.a.getLogger().log(Level.SEVERE, "Failed to save repaired config!", var12);
            }

         }
      }
   }

   public FileConfiguration getConfig() {
      return this.b;
   }

   public int getEnergyGainOnKill() {
      return this.b.getInt("energy.gain-on-kill", 1);
   }

   public int getEnergyLossOnDeath() {
      return this.b.getInt("energy.loss-on-death", 1);
   }

   public int getMaxEnergy() {
      return this.b.getInt("energy.max-energy", 10);
   }

   public int getStartingEnergy() {
      return this.b.getInt("energy.starting-energy", 5);
   }

   public int getRuinedThreshold() {
      return this.b.getInt("energy.ruined-threshold", 1);
   }

   public int getBrokenThreshold() {
      return this.b.getInt("energy.broken-threshold", 0);
   }

   public boolean isBanOnZeroEnergyEnabled() {
      return this.b.getBoolean("energy.ban-on-zero-energy", false);
   }

   public void setBanOnZeroEnergy(boolean var1) {
      this.b.set("energy.ban-on-zero-energy", var1);

      try {
         this.b.save(new File(this.a.getDataFolder(), "config.yml"));
      } catch (IOException var3) {
         this.a.getLogger().warning("Failed to save config: " + var3.getMessage());
      }

   }

   public boolean isSmpStarted() {
      return this.b.getBoolean("smp.started", false);
   }

   public void setSmpStarted(boolean var1) {
      this.b.set("smp.started", var1);

      try {
         this.b.save(new File(this.a.getDataFolder(), "config.yml"));
      } catch (IOException var3) {
         this.a.getLogger().warning("Failed to save config: " + var3.getMessage());
      }

   }

   public int getSmpAutoStartThreshold() {
      return this.b.getInt("smp.auto-start-threshold", 10);
   }

   public boolean isGemEnabled(GemType var1) {
      return this.b.getBoolean("gems.enabled." + var1.getId(), true);
   }

   public boolean isSingleGemOnly() {
      return this.b.getBoolean("gems.single-gem-only", true);
   }

   public int getPassiveUpdateInterval() {
      return this.b.getInt("passives.update-interval", 20);
   }

   public double getPhaseChance(int var1) {
      String var2 = "passives.astra.tier" + var1 + ".phase-chance";
      return this.b.getDouble(var2, var1 == 1 ? 0.1 : 0.15);
   }

   public double getLifeHealAmount(int var1) {
      String var2 = "passives.life.tier" + var1 + ".heal-amount";
      return this.b.getDouble(var2, var1 == 1 ? 0.3 : (double)0.5F);
   }

   public int getLifeHealInterval(int var1) {
      String var2 = "passives.life.tier" + var1 + ".heal-interval";
      return this.b.getInt(var2, 100);
   }

   public double getUndeadDamageMultiplier(int var1) {
      String var2 = "passives.life.tier" + var1 + ".undead-damage-multiplier";
      return this.b.getDouble(var2, var1 == 1 ? (double)2.0F : (double)3.0F);
   }

   public double getSaturationMultiplier(int var1) {
      String var2 = "passives.life.tier" + var1 + ".saturation-multiplier";
      return this.b.getDouble(var2, var1 == 1 ? (double)1.5F : (double)2.0F);
   }

   public int getGoldenAppleAbsorptionLevel(int var1) {
      String var2 = "passives.life.tier" + var1 + ".golden-apple-absorption-level";
      return this.b.getInt(var2, var1 == 1 ? 0 : 1);
   }

   public double getShockingArrowDamage(int var1) {
      String var2 = "passives.flux.tier" + var1 + ".shocking-arrow-damage";
      return this.b.getDouble(var2, var1 == 1 ? (double)2.0F : (double)3.0F);
   }

   public double getFlowStateSpeedBoost(int var1) {
      String var2 = "passives.flux.tier" + var1 + ".flow-state-speed-boost";
      return this.b.getDouble(var2, var1 == 1 ? 0.15 : 0.2);
   }

   public boolean isDoubleJumpEnabled(int var1) {
      String var2 = "passives.puff.tier" + var1 + ".double-jump-enabled";
      return this.b.getBoolean(var2, true);
   }

   public double getDoubleJumpVelocity(int var1) {
      String var2 = "passives.puff.tier" + var1 + ".double-jump-velocity";
      return this.b.getDouble(var2, var1 == 1 ? 0.6 : 0.8);
   }

   public double getLaunchVelocity(int var1) {
      String var2 = "passives.puff.tier" + var1 + ".launch-velocity";
      return this.b.getDouble(var2, var1 == 1 ? (double)3.5F : (double)4.5F);
   }

   public boolean isFallDamageImmunity(int var1) {
      String var2 = "passives.puff.tier" + var1 + ".fall-damage-immunity";
      return this.b.getBoolean(var2, true);
   }

   public boolean isSculkImmunity(int var1) {
      String var2 = "passives.puff.tier" + var1 + ".sculk-immunity";
      return this.b.getBoolean(var2, var1 == 2);
   }

   public boolean isAutoSmeltEnabled(int var1) {
      String var2 = "passives.fire.tier" + var1 + ".auto-smelt";
      return this.b.getBoolean(var2, true);
   }

   public boolean isFireResistanceEnabled(int var1) {
      String var2 = "passives.fire.tier" + var1 + ".fire-resistance";
      return this.b.getBoolean(var2, true);
   }

   public boolean isSoulSandImmunity(int var1) {
      String var2 = "passives.speed.tier" + var1 + ".soul-sand-immunity";
      return this.b.getBoolean(var2, true);
   }

   public int getSpeedLevel(int var1) {
      String var2 = "passives.speed.tier" + var1 + ".speed-level";
      return this.b.getInt(var2, var1 == 1 ? 0 : 1);
   }

   public int getStrengthLevel(int var1) {
      String var2 = "passives.strength.tier" + var1 + ".strength-level";
      return this.b.getInt(var2, var1 == 1 ? 0 : 1);
   }

   public double getBloodthornsMaxBonusDamage(int var1) {
      String var2 = "passives.strength.tier" + var1 + ".bloodthorns-max-bonus-damage";
      return this.b.getDouble(var2, var1 == 1 ? (double)7.0F : (double)10.0F);
   }

   public int getLuckLevel(int var1) {
      String var2 = "passives.wealth.tier" + var1 + ".luck-level";
      return this.b.getInt(var2, var1 == 1 ? 0 : 1);
   }

   public double getVillagerDiscount(int var1) {
      String var2 = "passives.wealth.tier" + var1 + ".villager-discount";
      return this.b.getDouble(var2, var1 == 1 ? 0.1 : 0.2);
   }

   /** @deprecated */
   @Deprecated
   public double getPhaseChance() {
      return this.getPhaseChance(2);
   }

   /** @deprecated */
   @Deprecated
   public double getLifeHealAmount() {
      return this.getLifeHealAmount(2);
   }

   /** @deprecated */
   @Deprecated
   public int getLifeHealInterval() {
      return this.getLifeHealInterval(2);
   }

   /** @deprecated */
   @Deprecated
   public double getUndeadDamageMultiplier() {
      return this.getUndeadDamageMultiplier(2);
   }

   /** @deprecated */
   @Deprecated
   public double getSaturationMultiplier() {
      return this.getSaturationMultiplier(2);
   }

   public int getGlobalAbilityCooldown() {
      return this.b.getInt("abilities.global-cooldown", 1);
   }

   public int getAbilityCooldown(String var1) {
      return this.b.getInt("abilities.cooldowns." + var1, 10);
   }

   public double getAbilityDamage(String var1) {
      return this.b.getDouble("abilities.damage." + var1, (double)4.0F);
   }

   public int getAbilityDuration(String var1) {
      return this.b.getInt("abilities.durations." + var1, 10);
   }

   public boolean isAutoEnchantEnabled() {
      return this.b.getBoolean("auto-enchant.enabled", true);
   }

   public boolean shouldPlayUpgradeEffects() {
      return this.b.getBoolean("upgrader.play-effects", true);
   }

   public String getUpgradeParticle() {
      return this.b.getString("upgrader.particle", "ENCHANT");
   }

   public int getUpgradeParticleCount() {
      return this.b.getInt("upgrader.particle-count", 50);
   }

   public String getUpgradeSound() {
      return this.b.getString("upgrader.sound", "ENTITY_PLAYER_LEVELUP");
   }

   public int getTraderCooldown() {
      return this.b.getInt("trader.cooldown", 5);
   }

   public boolean shouldPlayTradeEffects() {
      return this.b.getBoolean("trader.play-effects", true);
   }

   public boolean isEnergyBottleDropEnabled() {
      return this.b.getBoolean("energy-bottle.drop-enabled", true);
   }

   public int getRepairKitEnergyPerSecond() {
      return this.b.getInt("repair-kit.energy-per-second", 1);
   }

   public int getRepairKitMaxEnergy() {
      return this.b.getInt("repair-kit.max-total-energy", 10);
   }

   public int getRepairKitRadius() {
      return this.b.getInt("repair-kit.heal-radius", 10);
   }

   public int getRepairKitUpdateInterval() {
      return this.b.getInt("repair-kit.update-interval", 20);
   }

   public boolean shouldPrioritizeLowestEnergy() {
      return this.b.getBoolean("repair-kit.prioritize-lowest", true);
   }

   public String getMessage(String var1) {
      return this.b.getString("messages." + var1, "").replace("&", "§");
   }

   public String getPrefix() {
      return this.getMessage("prefix");
   }

   public String getFormattedMessage(String var1, Object... var2) {
      if (this.areOptionalMessagesDisabled() && var1.equals("ability-activated")) {
         return null;
      } else {
         String var3 = this.getMessage(var1);
         if (var3 != null && !var3.trim().isEmpty()) {
            for(int var4 = 0; var4 < var2.length; var4 += 2) {
               if (var4 + 1 < var2.length) {
                  var3 = var3.replace("{" + String.valueOf(var2[var4]) + "}", String.valueOf(var2[var4 + 1]));
               }
            }

            String var10000 = this.getPrefix();
            String var6 = var10000 + " §f" + var3;
            String var5 = var6.replaceAll("§[0-9a-fk-or]", "").trim();
            if (!var5.equals("BlissGems »") && !var5.isEmpty()) {
               return var6;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public void sendFormattedMessage(CommandSender var1, String var2, Object... var3) {
      String var4 = this.getFormattedMessage(var2, var3);
      if (var4 != null && !var4.isEmpty()) {
         var1.sendMessage(var4);
      }

   }

   public boolean areOptionalMessagesDisabled() {
      return this.b.getBoolean("disable-optional-messages", true);
   }
}


static public class CustomItemManager {
   private static final Map<String, a> a = new HashMap();
   private static NamespacedKey b;
   private static NamespacedKey c;

   public static void initialize(JavaPlugin var0) {
      b = new NamespacedKey(var0, "item_id");
      c = new NamespacedKey(var0, "locked_item");
   }

   private static void a(String var0, Material var1, int var2, String var3) {
      a.put(var0, new a(var1, var2, var3, (List)null));
   }

   private static void a(String var0, Material var1, int var2, String var3, List<String> var4) {
      a.put(var0, new a(var1, var2, var3, var4));
   }

   public static void registerAddonItem(String var0, Material var1, int var2, String var3, List<String> var4) {
      if (a.containsKey(var0)) {
         throw new IllegalArgumentException("Item ID '" + var0 + "' is already registered!");
      } else {
         a.put(var0, new a(var1, var2, var3, var4));
      }
   }

   public static String getIdByItem(ItemStack var0) {
      if (var0 != null && var0.hasItemMeta()) {
         ItemMeta var1 = var0.getItemMeta();
         if (var1 == null) {
            return null;
         } else {
            return var1.getPersistentDataContainer().has(b, PersistentDataType.STRING) ? (String)var1.getPersistentDataContainer().get(b, PersistentDataType.STRING) : null;
         }
      } else {
         return null;
      }
   }

   public static ItemStack getItemById(String var0) {
      return getItemById(var0, -1);
   }

   public static ItemStack getItemById(String var0, int var1) {
      a var2 = (a)a.get(var0);
      if (var2 == null) {
         return null;
      } else {
         ItemStack var3 = new ItemStack(var2.a);
         ItemMeta var4 = var3.getItemMeta();
         if (var4 == null) {
            return var3;
         } else {
            int var5 = var2.b;
            if (GemType.isGem(var0) && var1 >= 0) {
               var5 = a(var2.b, var1, var0);
            }

            var4.setCustomModelData(var5);
            var4.getPersistentDataContainer().set(b, PersistentDataType.STRING, var0);
            if (GemType.isGem(var0)) {
               var4.getPersistentDataContainer().set(c, PersistentDataType.BYTE, (byte)1);
            }

            var4.setDisplayName(var2.c);
            if (GemType.isGem(var0)) {
               a(var4, var2.d, var1);
            } else if (var2.d != null && !var2.d.isEmpty()) {
               var4.setLore(var2.d);
            }

            var3.setItemMeta(var4);
            return var3;
         }
      }
   }

   private static int a(int var0, int var1, String var2) {
      if (var2 == null || !var2.equals("strength_gem_t1") && !var2.equals("strength_gem_t2")) {
         byte var3;
         if (var1 <= 2) {
            var3 = 0;
         } else if (var1 <= 5) {
            var3 = 20;
         } else if (var1 <= 8) {
            var3 = 30;
         } else {
            var3 = 40;
         }

         return var0 + var3;
      } else {
         return var0;
      }
   }

   public static void updateGemTexture(ItemStack var0, int var1) {
      if (var0 != null && var0.hasItemMeta()) {
         String var2 = getIdByItem(var0);
         if (var2 != null && GemType.isGem(var2)) {
            a var3 = (a)a.get(var2);
            if (var3 != null) {
               ItemMeta var4 = var0.getItemMeta();
               if (var4 != null) {
                  int var5 = a(var3.b, var1, var2);
                  var4.setCustomModelData(var5);
                  a(var4, var3.d, var1);
                  var0.setItemMeta(var4);
               }
            }
         }
      }
   }

   private static void a(ItemMeta var0, List<String> var1, int var2) {
      EnergyState var3 = EnergyState.fromEnergy(var2);
      boolean var4 = var3.isEnhanced();
      ArrayList var5 = new ArrayList();
      if (var1 != null) {
         var5.addAll(var1);
      }

      if (var4) {
         if (!var5.isEmpty()) {
            var5.add("");
         }

         var5.add("§5✮ " + var3.getDisplayName());
      }

      var0.setLore(var5.isEmpty() ? null : var5);
      if (var4) {
         var0.addEnchant(Enchantment.UNBREAKING, 1, true);
         var0.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
      } else {
         var0.removeEnchant(Enchantment.UNBREAKING);
      }

   }

   public static boolean isCustomItem(ItemStack var0) {
      return getIdByItem(var0) != null;
   }

   public static boolean markAsUndroppable(ItemStack var0) {
      if (var0 != null && var0.getType() != Material.AIR && var0.hasItemMeta()) {
         ItemMeta var1 = var0.getItemMeta();
         if (var1 == null) {
            return false;
         } else {
            PersistentDataContainer var2 = var1.getPersistentDataContainer();
            if (var2.has(c, PersistentDataType.BYTE) && (Byte)var2.get(c, PersistentDataType.BYTE) == 1) {
               return false;
            } else {
               var2.set(c, PersistentDataType.BYTE, (byte)1);
               var0.setItemMeta(var1);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public static boolean isUndroppable(ItemStack var0) {
      if (var0 != null && var0.getType() != Material.AIR) {
         if (!var0.hasItemMeta()) {
            return false;
         } else {
            ItemMeta var1 = var0.getItemMeta();
            if (var1 == null) {
               return false;
            } else {
               PersistentDataContainer var2 = var1.getPersistentDataContainer();
               return var2.has(c, PersistentDataType.BYTE) && (Byte)var2.get(c, PersistentDataType.BYTE) == 1;
            }
         }
      } else {
         return false;
      }
   }

   static {
      a("astra_gem_t1", Material.ECHO_SHARD, 1001, "§d§lASTRA GEM", List.of("§f§lMANAGE THE TIDES OF THE COSMOS", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Soul Capture & Soul Healing", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l\ud83d\udd2a ASTRAL DAGGERS", "§7Fire 3 phantom daggers that deal damage", "", "§8Upgrade to Tier 2 for Astral Projection,", "§8Dimensional Drift & Void!"));
      a("fire_gem_t1", Material.ECHO_SHARD, 1002, "§d§lFIRE GEM", List.of("§f§lMANIPULATE FIRE", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Fire Resistance", "§7- Flame & Fire Aspect on weapons", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l\ud83d\udd25 CHARGED FIREBALL", "§7Hold to charge, release to fire", "§7Stand on obsidian to prevent charge decay", "", "§8Upgrade to Tier 2 for Campfire,", "§8Crisp & Meteor Shower!"));
      a("flux_gem_t1", Material.ECHO_SHARD, 1003, "§d§lFLUX GEM", List.of("§f§lWITH GREAT POWER COMES GREAT RESPONSIBILITY", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Immune to Weakness, Slowness & Hunger", "§7- Shocking Chance (stun on arrow hits)", "", "§b\ud83c\udf1f §b§lABILITY", "§7- Conduction (teleport to copper blocks)", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l✠ FLUX BEAM", "§7Charge and fire a powerful beam", "§7Charged beam deals massive armor damage", "", "§8Upgrade to Tier 2 for Ground & more!"));
      a("life_gem_t1", Material.ECHO_SHARD, 1004, "§d§lLIFE GEM", List.of("§f§lCONTROL THE BALANCE OF LIFE", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Wither immunity", "§7- Continuous healing", "§7- Unbreaking on tools", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l\ud83d\udc98 HEART DRAINER", "§7Siphon health from your enemies", "", "§8Upgrade to Tier 2 for Life Circle,", "§8Vitality Vortex & Heart Lock!"));
      a("puff_gem_t1", Material.ECHO_SHARD, 1005, "§d§lPUFF GEM", List.of("§f§lBE THE BIGGEST BIRD", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- No fall damage", "§7- Power & Punch on bows", "", "§b\ud83c\udf1f §b§lABILITY", "§7- Double Jump", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l☁ DASH", "§7Dashes in the direction you're looking", "§7Deals damage if passing through enemies", "", "§8Upgrade to Tier 2 for Breezy Bash!"));
      a("speed_gem_t1", Material.ECHO_SHARD, 1006, "§d§lSPEED GEM", List.of("§f§lWATCH THE WORLD TURN INTO A BLUR", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Speed I & Dolphin's Grace", "§7- Efficiency on tools", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l⚡ BLUR", "§7Summons successive lightning strikes", "§7dealing damage and knockback", "", "§8Upgrade to Tier 2 for Speed Storm", "§8& Terminal Velocity!"));
      a("strength_gem_t1", Material.ECHO_SHARD, 1007, "§d§lSTRENGTH GEM", List.of("§f§lHAVE THE STRENGTH OF AN ARMY", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Strength I", "§7- Sharpness II on weapons", "§7- Bloodthorns (more damage at low HP)", "", "§8No active abilities at Tier 1", "§8Upgrade to Tier 2 for Nullify, Frailer", "§8& Shadow Stalker!"));
      a("wealth_gem_t1", Material.ECHO_SHARD, 1008, "§d§lWEALTH GEM", List.of("§f§lFUEL AN EMPIRE", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Luck & Hero of the Village", "§7- Mending, Fortune & Looting on tools", "§7- Durability Chip (extra armor damage)", "", "§8No active abilities at Tier 1", "§8Upgrade to Tier 2 for Pockets,", "§8Unfortunate & more!"));
      a("astra_gem_t2", Material.ECHO_SHARD, 2001, "§d§lASTRA GEM", List.of("§f§lMANAGE THE TIDES OF THE COSMOS", "§a§o(Pristine)", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Soul Capture & Soul Healing", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l\ud83d\udd2a ASTRAL DAGGERS", "§7Fire 3 phantom daggers that deal damage", "", "§b§l\ud83d\udc7b ASTRAL PROJECTION", "§7Scout in spectator mode", "§7Sub-abilities: §dSpook §7& §dTag", "", "§b§l\ud83c\udf00 DIMENSIONAL DRIFT", "§7Ride an invisible horse while invisible", "", "§b§l\ud83d\udd73 DIMENSIONAL VOID", "§7Nullify enemy gem abilities in radius"));
      a("fire_gem_t2", Material.ECHO_SHARD, 2002, "§d§lFIRE GEM", List.of("§f§lMANIPULATE FIRE", "§a§o(Pristine)", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Fire Resistance", "§7- Flame & Fire Aspect on weapons", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l\ud83d\udd25 CHARGED FIREBALL", "§7Hold to charge, release to fire", "§7Stand on obsidian to prevent charge decay", "", "§b§l\ud83e\udd7e COZY CAMPFIRE", "§7Spawns a campfire granting allies Regen IV", "", "§b§l\ud83e\uddca CRISP", "§7Evaporate water, replace blocks with nether", "", "§b§l\ud83e\udde8 METEOR SHOWER", "§7Rain fire on a target area"));
      a("flux_gem_t2", Material.ECHO_SHARD, 2003, "§d§lFLUX GEM", List.of("§f§lWITH GREAT POWER COMES GREAT RESPONSIBILITY", "§a§o(Pristine)", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Immune to Weakness, Slowness & Hunger", "§7- Shocking Chance (stun on arrow hits)", "", "§b\ud83c\udf1f §b§lABILITY", "§7- Conduction (teleport to copper blocks)", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l✠ FLUX BEAM", "§7Chargeable beam dealing massive armor damage", "", "§b§l\ud83c\udf00 GROUND", "§7Freeze enemies in place", "", "§b§l\ud83d\udca5 FLASHBANG", "§7Blindness and Nausea to enemies in radius", "", "§b§l⚡ KINETIC BURST", "§7Radial knockback with sonic boom"));
      a("life_gem_t2", Material.ECHO_SHARD, 2004, "§d§lLIFE GEM", List.of("§f§lCONTROL THE BALANCE OF LIFE", "§a§o(Pristine)", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Wither immunity", "§7- Continuous healing", "§7- Unbreaking on tools", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l\ud83d\udc98 HEART DRAINER", "§7Siphon health from your enemies", "", "§b§l✨ CIRCLE OF LIFE", "§7Zone that decreases enemy max HP", "§7and increases yours and allies' HP", "", "§b§l\ud83d\udcab VITALITY VORTEX", "§7Grants effects based on your surroundings", "", "§b§l\ud83d\udd12 HEART LOCK", "§7Cap enemy max HP at their current HP"));
      a("puff_gem_t2", Material.ECHO_SHARD, 2005, "§d§lPUFF GEM", List.of("§f§lBE THE BIGGEST BIRD", "§a§o(Pristine)", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- No fall damage", "§7- Power & Punch on bows", "", "§b\ud83c\udf1f §b§lABILITY", "§7- Double Jump", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l☁ DASH", "§7Dashes in the direction you're looking", "§7Deals damage if passing through enemies", "", "§b§l⏫ BREEZY BASH", "§7Launch an enemy skyward then slam them", "", "§b§l\ud83c\udf2a GROUP BREEZY BASH", "§7Send all nearby enemies flying away"));
      a("speed_gem_t2", Material.ECHO_SHARD, 2006, "§d§lSPEED GEM", List.of("§f§lWATCH THE WORLD TURN INTO A BLUR", "§a§o(Pristine)", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Speed I & Dolphin's Grace", "§7- Efficiency on tools", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l⚡ BLUR", "§7Summons successive lightning strikes", "§7dealing damage and knockback", "", "§b§l\ud83c\udf29 SPEED STORM", "§7Freezes enemies while granting allies", "§7Speed and Haste", "", "§b§l\ud83d\udca8 TERMINAL VELOCITY", "§7Speed III + Haste II for a short duration"));
      a("strength_gem_t2", Material.ECHO_SHARD, 2007, "§d§lSTRENGTH GEM", List.of("§f§lHAVE THE STRENGTH OF AN ARMY", "§a§o(Pristine)", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Strength I", "§7- Sharpness V on weapons", "§7- Bloodthorns (more damage at low HP)", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l\ud83d\udeab NULLIFY", "§7Strip all potion effects from a target", "", "§b§l⚔ FRAILER", "§7Clear target's potions, apply Weakness I", "§7(20s) and Wither I (40s)", "", "§b§l\ud83d\udd0d SHADOW STALKER", "§7Consume a player head or owned item", "§7to track a player's location"));
      a("wealth_gem_t2", Material.ECHO_SHARD, 2008, "§d§lWEALTH GEM", List.of("§f§lFUEL AN EMPIRE", "§a§o(Pristine)", "", "§a\ud83c\udf1f §a§lPASSIVES", "§7- Luck & Hero of the Village", "§7- Mending, Fortune & Looting on tools", "§7- Durability Chip & Armor Mend", "", "§d\ud83c\udf1f §d§lPOWERS", "§b§l\ud83d\udcb8 UNFORTUNATE", "§7Chance to disable enemy actions", "", "§b§l\ud83c\udf92 POCKETS", "§79 extra inventory slots (/bliss pockets)", "", "§b§l\ud83d\udd12 ITEM LOCK", "§7Lock an enemy's held item temporarily", "", "§b§l✨ AMPLIFICATION", "§7Boost all enchantments for 45s", "", "§b§l\ud83c\udf40 RICH RUSH", "§7Increased drop rates for ~3 min"));
      a("gem_upgrader", Material.ENCHANTED_BOOK, 3001, "§6§l§nGem Upgrader", List.of("§7Right Click to upgrade any Tier 1 gem to Tier 2", "", "§8Works for ALL gem types:", "§5Astra §8• §cFire §8• §bFlux §8• §dLife", "§fPuff §8• §aSpeed §8• §6Strength §8• §eWealth"));
      a("energy_bottle", Material.GHAST_TEAR, 4001, "§b§lEnergy Bottle");
      a("gem_trader", Material.EMERALD, 4002, "§2§lGem Trader");
      a("repair_kit", Material.BEACON, 4003, "§d§lRepair Kit");
      a("gem_fragment", Material.PRISMARINE_SHARD, 4004, "§3§lGem Fragment");
      a("revive_beacon", Material.BEACON, 4005, "§e§lRevive Beacon", Arrays.asList("§7A powerful beacon that can revive players", "§7from the brink of death.", "", "§6Right-click to activate"));
   }

   private static class a {
      final Material a;
      final int b;
      final String c;
      final List<String> d;

      a(Material var1, int var2, String var3, List<String> var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }
   }
}

static public enum EnergyState {
   BROKEN(0, 0, "Broken", "§c§lBROKEN"),
   RUINED(1, 1, "Ruined", "§4Ruined"),
   SHATTERED(2, 2, "Shattered", "§6Shattered"),
   CRACKED(3, 3, "Cracked", "§eΩCracked"),
   SCRATCHED(4, 4, "Scratched", "§aSκratched"),
   PRISTINE(5, 5, "Pristine", "§bPristine"),
   PRISTINE_PLUS_1(6, 6, "Pristine +1", "§d§lPristine §5+1"),
   PRISTINE_PLUS_2(7, 7, "Pristine +2", "§d§lPristine §5+2"),
   PRISTINE_PLUS_3(8, 8, "Pristine +3", "§d§lPristine §5+3"),
   PRISTINE_PLUS_4(9, 9, "Pristine +4", "§d§lPristine §5+4"),
   PRISTINE_PLUS_5(10, 10, "Pristine +5", "§d§lPristine §5+5");

   private final int a;
   private final int b;
   private final String c;
   private final String d;

   private EnergyState(int var3, int var4, String var5, String var6) {
      this.a = var3;
      this.b = var4;
      this.c = var5;
      this.d = var6;
   }

   public int getMinEnergy() {
      return this.a;
   }

   public int getMaxEnergy() {
      return this.b;
   }

   public String getName() {
      return this.c;
   }

   public String getDisplayName() {
      return this.d;
   }

   public static EnergyState fromEnergy(int var0) {
      var0 = Math.max(0, Math.min(10, var0));

      for(EnergyState var4 : values()) {
         if (var0 >= var4.a && var0 <= var4.b) {
            return var4;
         }
      }

      return BROKEN;
   }

   public boolean passivesActive() {
      return this.a > 1;
   }

   public boolean abilitiesUsable() {
      return this.a >= 2;
   }

   public boolean isEnhanced() {
      return this.a > 5;
   }

   public boolean isMaxEnergy() {
      return this == PRISTINE_PLUS_5;
   }

   // $FF: synthetic method
   private static EnergyState[] a() {
      return new EnergyState[]{BROKEN, RUINED, SHATTERED, CRACKED, SCRATCHED, PRISTINE, PRISTINE_PLUS_1, PRISTINE_PLUS_2, PRISTINE_PLUS_3, PRISTINE_PLUS_4, PRISTINE_PLUS_5};
   }
}

static public enum GemType {
   ASTRA("astra", "Astra", "Wield phantom daggers and explore astral dimensions"),
   FIRE("fire", "Fire", "Burn your enemies with charged fireballs and cozy campfires"),
   FLUX("flux", "Flux", "Stun enemies and unleash electric power"),
   LIFE("life", "Life", "Heal yourself and drain the life from enemies"),
   PUFF("puff", "Puff", "Defy gravity with double jumps and immunity to fall damage"),
   SPEED("speed", "Speed", "Move faster and sedate your foes"),
   STRENGTH("strength", "Strength", "Empower your strikes and hunt your enemies"),
   WEALTH("wealth", "Wealth", "Find fortune and fuel an empire");

   private final String id;
   private final String displayName;
   private final String description;

   private GemType(String var3, String var4, String var5) {
      this.id = var3;
      this.displayName = var4;
      this.description = var5;
   }

   public String getId() {
      return this.id;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getDescription() {
      return this.description;
   }

   public String getColor() {
      String var10000;
      switch (this.ordinal()) {
         case 0 -> var10000 = "§d";
         case 1 -> var10000 = "§c";
         case 2 -> var10000 = "§b";
         case 3 -> var10000 = "§a";
         case 4 -> var10000 = "§f";
         case 5 -> var10000 = "§e";
         case 6 -> var10000 = "§4";
         case 7 -> var10000 = "§6";
         default -> throw new IncompatibleClassChangeError();
      }

      return var10000;
   }

   public static GemType fromOraxenId(String var0) {
      if (var0 == null) {
         return null;
      } else {
         for(GemType var4 : values()) {
            if (var0.toLowerCase().startsWith(var4.id + "_gem")) {
               return var4;
            }
         }

         return null;
      }
   }

   public static int getTierFromOraxenId(String var0) {
      if (var0 == null) {
         return 1;
      } else {
         return var0.endsWith("_gem_t2") ? 2 : 1;
      }
   }

   public static String buildOraxenId(GemType var0, int var1) {
      String var10000 = var0.getId();
      return var10000 + "_gem_t" + var1;
   }

   public static boolean isGem(String var0) {
      return var0 != null && var0.contains("_gem_t");
   }

   // $FF: synthetic method
   private static GemType[] $values() {
      return new GemType[]{ASTRA, FIRE, FLUX, LIFE, PUFF, SPEED, STRENGTH, WEALTH};
   }
}


static public class ParticleUtils {
   public static final Color ASTRA_PURPLE = Color.fromRGB(106, 11, 184);
   public static final Color FIRE_ORANGE = Color.fromRGB(255, 119, 0);
   public static final Color FLUX_CYAN = Color.fromRGB(94, 215, 255);
   public static final Color FLUX_DARK_CYAN = Color.fromRGB(16, 131, 173);
   public static final Color LIFE_PINK = Color.fromRGB(255, 0, 180);
   public static final Color LIFE_PINK_ALT = Color.fromRGB(255, 0, 179);
   public static final Color PUFF_WHITE = Color.fromRGB(255, 255, 255);
   public static final Color SPEED_YELLOW = Color.fromRGB(244, 255, 28);
   public static final Color STRENGTH_RED = Color.fromRGB(199, 0, 10);
   public static final Color WEALTH_GREEN = Color.fromRGB(0, 166, 44);

   public static void drawColoredCircle(Location var0, double var1, Color var3, float var4, int var5) {
      World var6 = var0.getWorld();
      if (var6 != null) {
         Particle.DustOptions var7 = new Particle.DustOptions(var3, var4);

         for(int var8 = 0; var8 < var5; ++var8) {
            double var9 = (double)var8 / (double)var5 * (double)2.0F * Math.PI;
            double var11 = Math.cos(var9) * var1;
            double var13 = Math.sin(var9) * var1;
            Location var15 = var0.clone().add(var11, (double)0.0F, var13);
            var6.spawnParticle(Particle.DUST, var15, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, var7, true);
         }

      }
   }

   public static void drawExpandingCircles(final Location var0, final Color var1, final float var2, double var3, final Runnable var5) {
      final double[] var6 = a(var3);
      (new BukkitRunnable() {
         int a = 0;

         public void run() {
            if (this.a >= var6.length) {
               if (var5 != null) {
                  var5.run();
               }

               this.cancel();
            } else {
               ParticleUtils.drawColoredCircle(var0, var6[this.a], var1, var2, 32);
               if (this.a + 1 < var6.length) {
                  ParticleUtils.drawColoredCircle(var0, var6[this.a + 1], var1, var2, 32);
                  this.a += 2;
               } else {
                  ++this.a;
               }

            }
         }
      }).runTaskTimer(Bukkit.getPluginManager().getPlugin("BlissGems"), 0L, 1L);
   }

   private static double[] a(double var0) {
      int var2 = (int)((var0 - (double)0.75F) / (double)0.25F) + 1;
      double[] var3 = new double[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = (double)0.75F + (double)var4 * (double)0.25F;
      }

      return var3;
   }

   public static void drawColoredLine(Location var0, Location var1, Color var2, float var3, int var4) {
      World var5 = var0.getWorld();
      if (var5 != null) {
         Particle.DustOptions var6 = new Particle.DustOptions(var2, var3);
         double var7 = var0.distance(var1);
         int var9 = (int)(var7 * (double)var4);

         for(int var10 = 0; var10 <= var9; ++var10) {
            double var11 = (double)var10 / (double)var9;
            double var13 = var0.getX() + (var1.getX() - var0.getX()) * var11;
            double var15 = var0.getY() + (var1.getY() - var0.getY()) * var11;
            double var17 = var0.getZ() + (var1.getZ() - var0.getZ()) * var11;
            Location var19 = new Location(var5, var13, var15, var17);
            var5.spawnParticle(Particle.DUST, var19, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, var6, true);
         }

      }
   }

   public static void drawPulsingLine(final Location var0, final Location var1, final Color var2, final float var3, final int var4, int var5, final Runnable var6) {
      (new BukkitRunnable() {
         int a = 0;

         public void run() {
            if (this.a >= var4) {
               if (var6 != null) {
                  var6.run();
               }

               this.cancel();
            } else {
               ParticleUtils.drawColoredLine(var0, var1, var2, var3, 10);
               ++this.a;
            }
         }
      }).runTaskTimer(Bukkit.getPluginManager().getPlugin("BlissGems"), 0L, (long)var5);
   }

   public static void drawDome(Location var0, Color var1, float var2, double var3) {
      double[] var5 = new double[]{var3, var3 - (double)1.0F, var3 - (double)2.0F, var3 - (double)3.0F, var3 - (double)4.0F, var3 - (double)5.0F};

      for(int var6 = 0; var6 < var5.length; ++var6) {
         if (!(var5[var6] <= (double)0.0F)) {
            Location var7 = var0.clone().add((double)0.0F, (double)var6, (double)0.0F);
            drawColoredCircle(var7, var5[var6], var1, var2, 48);
         }
      }

   }

   public static void drawSpiral(Location var0, Color var1, float var2, double var3, double var5, double var7) {
      World var9 = var0.getWorld();
      if (var9 != null) {
         Particle.DustOptions var10 = new Particle.DustOptions(var1, var2);
         int var11 = (int)(var3 * (double)8.0F);

         for(int var12 = 0; var12 < var11; ++var12) {
            double var13 = (double)var12 / (double)var11 * var3;
            double var15 = (double)var12 / (double)var11 * (double)4.0F * Math.PI + var7;
            double var17 = Math.cos(var15) * var5;
            double var19 = Math.sin(var15) * var5;
            Location var21 = var0.clone().add(var17, var13, var19);
            var9.spawnParticle(Particle.DUST, var21, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, var10, true);
         }

      }
   }

   public static void drawRing(Location var0, Color var1, float var2, double var3, double var5, int var7) {
      World var8 = var0.getWorld();
      if (var8 != null) {
         Particle.DustOptions var9 = new Particle.DustOptions(var1, var2);

         for(int var10 = 0; var10 < var7; ++var10) {
            double var11 = (double)var10 / (double)var7 * (double)2.0F * Math.PI;
            double var13 = Math.cos(var11) * var3;
            double var15 = Math.sin(var11) * var3;
            Location var17 = var0.clone().add(var13, var5, var15);
            var8.spawnParticle(Particle.DUST, var17, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, var9, true);
         }

      }
   }

   public static void drawBeam(Location var0, Vector var1, double var2, double var4, Particle var6) {
      World var7 = var0.getWorld();
      if (var7 != null) {
         var1 = var1.normalize();
         byte var8 = 25;
         byte var9 = 8;
         double var10 = var2 / (double)var8;

         for(int var12 = 0; var12 < var8; ++var12) {
            Location var13 = var0.clone().add(var1.clone().multiply((double)var12 * var10));
            double var14 = var4 * ((double)1.0F - (double)var12 / (double)var8);
            if (var14 < (double)0.5F) {
               var14 = (double)0.5F;
            }

            for(int var16 = 0; var16 < var9; ++var16) {
               double var17 = (double)var16 / (double)var9 * (double)2.0F * Math.PI;
               Vector var19 = var1.clone().crossProduct(new Vector(0, 1, 0)).normalize();
               Vector var20 = var1.clone().crossProduct(var19).normalize();
               Vector var21 = var19.clone().multiply(Math.cos(var17) * var14).add(var20.clone().multiply(Math.sin(var17) * var14));
               Location var22 = var13.clone().add(var21);
               var7.spawnParticle(var6, var22, 3, (double)0.0F, (double)0.0F, (double)0.0F, 1.0E-9, (Object)null, true);
            }
         }

      }
   }

   public static void drawTrail(Location var0, Particle var1, int var2) {
      World var3 = var0.getWorld();
      if (var3 != null) {
         var3.spawnParticle(var1, var0, var2, 0.1, 0.1, 0.1, (double)0.0F, (Object)null, true);
      }
   }
}
    }

    static final class P_dev_xoperr_blissgems_commands {


static public class BlissCommand implements CommandExecutor, TabCompleter {
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
                     case FLUX -> this.plugin.getFluxAbilities().e(var3);
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


static public class FixHeartsCommand implements CommandExecutor, TabCompleter {
   private final BlissGems plugin;

   public FixHeartsCommand(BlissGems var1) {
      this.plugin = var1;
   }

   public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
      if (!var1.hasPermission("blissgems.admin") && !var1.hasPermission("blissgems.fixhearts")) {
         var1.sendMessage("§cYou don't have permission to use this command.");
         return true;
      } else {
         Player var5;
         if (var4.length == 0) {
            if (!(var1 instanceof Player)) {
               var1.sendMessage("§cUsage: /fixhearts <player>");
               return true;
            }

            var5 = (Player)var1;
         } else {
            var5 = Bukkit.getPlayer(var4[0]);
            if (var5 == null) {
               var1.sendMessage("§cPlayer §e" + var4[0] + "§c not found or offline.");
               return true;
            }
         }

         AttributeInstance var6 = var5.getAttribute(Attribute.GENERIC_MAX_HEALTH);
         if (var6 == null) {
            var1.sendMessage("§cCould not access max-health attribute for " + var5.getName() + ".");
            return true;
         } else {
            Logger var10000 = this.plugin.getLogger();
            String var10001 = var5.getName();
            var10000.info("[fixhearts] " + var10001 + " baseValue=" + var6.getBaseValue() + " value=" + var6.getValue() + " health=" + var5.getHealth());

            for(AttributeModifier var8 : var6.getModifiers()) {
               String var9 = "n/a";

               try {
                  NamespacedKey var10 = var8.getKey();
                  if (var10 != null) {
                     var9 = var10.toString();
                  }
               } catch (Throwable var11) {
               }

               var10000 = this.plugin.getLogger();
               var10001 = var8.getName();
               var10000.info("[fixhearts]   modifier name=" + var10001 + " key=" + var9 + " uuid=" + String.valueOf(var8.getUniqueId()) + " amount=" + var8.getAmount() + " op=" + String.valueOf(var8.getOperation()));
            }

            if (this.plugin.getLifeAbilities() != null) {
               this.plugin.getLifeAbilities().cleanup(var5);
            }

            if (this.plugin.getSoulManager() != null) {
               this.plugin.getSoulManager().cleanup(var5);
            }

            int var12 = 0;

            for(AttributeModifier var16 : new ArrayList(var6.getModifiers())) {
               var6.removeModifier(var16);
               ++var12;
            }

            double var15 = var6.getDefaultValue();
            var6.setBaseValue(var15);
            var5.setHealth(var6.getValue());
            var5.sendMessage("§d§l❤ §fYour hearts have been restored.");
            if (var1 != var5) {
               var10001 = var5.getName();
               var1.sendMessage("§aFixed hearts for §l" + var10001 + "§a (removed " + var12 + " modifier(s)).");
            }

            var10000 = this.plugin.getLogger();
            var10001 = var1.getName();
            var10000.info("[fixhearts] " + var10001 + " reset hearts for " + var5.getName() + " (removed " + var12 + " modifier(s))");
            return true;
         }
      }
   }

   public List<String> onTabComplete(CommandSender var1, Command var2, String var3, String[] var4) {
      if (var4.length == 1) {
         String var5 = var4[0].toLowerCase();
         return (List)Bukkit.getOnlinePlayers().stream().map(Player::getName).filter((var1x) -> var1x.toLowerCase().startsWith(var5)).collect(Collectors.toList());
      } else {
         return new ArrayList();
      }
   }
}


static public class a {
   private final BlissGems a;

   public a(BlissGems var1) {
      this.a = var1;
   }

   public void a(Player var1, String[] var2) {
      if (var2.length == 0) {
         this.a(var1);
      } else {
         switch (var2[0].toLowerCase()) {
            case "top":
               this.a(var1);
               break;
            case "me":
            case "my":
            case "personal":
               this.b(var1);
               break;
            case "gems":
            case "usage":
               this.c(var1);
               break;
            default:
               var1.sendMessage("§c/bliss stats [top|me|gems]");
         }
      }

   }

   private void a(Player var1) {
      var1.sendMessage("§6§m" + "═".repeat(50));
      var1.sendMessage("§e§l\ud83c\udfc6 TOP KILLERS \ud83c\udfc6");
      var1.sendMessage("§6§m" + "═".repeat(50));
      List var2 = this.a.getStatsManager().getTopKillers(10);
      if (var2.isEmpty()) {
         var1.sendMessage("§7No kills recorded yet.");
      } else {
         int var3 = 1;

         for(Map.Entry var5 : var2) {
            Player var6 = Bukkit.getPlayer((UUID)var5.getKey());
            String var7 = var6 != null ? var6.getName() : "Unknown";
            String var10000;
            switch (var3) {
               case 1 -> var10000 = "\ud83e\udd47";
               case 2 -> var10000 = "\ud83e\udd48";
               case 3 -> var10000 = "\ud83e\udd49";
               default -> var10000 = "  ";
            }

            String var8 = var10000;
            var1.sendMessage("§e" + var8 + " §7#" + var3 + " §f" + var7 + " §7- §f" + String.valueOf(var5.getValue()) + " §7kills");
            ++var3;
         }
      }

      var1.sendMessage("§6§m" + "═".repeat(50));
      var1.sendMessage("§8Use §7/bliss stats me §8for your personal stats");
      var1.sendMessage("§8Use §7/bliss stats gems §8for gem distribution");
   }

   private void b(Player var1) {
      var1.sendMessage("§6§m" + "═".repeat(50));
      var1.sendMessage("§e§l\ud83d\udcca YOUR STATS \ud83d\udcca");
      var1.sendMessage("§6§m" + "═".repeat(50));
      int var2 = this.a.getStatsManager().getKills(var1);
      int var3 = this.a.getStatsManager().getDeaths(var1);
      long var4 = this.a.getStatsManager().getTimePlayed(var1);
      GemType var6 = this.a.getGemManager().getGemType(var1);
      int var7 = this.a.getEnergyManager().getEnergy(var1);
      var1.sendMessage("§7Kills: §f" + var2);
      var1.sendMessage("§7Deaths: §f" + var3);
      Object var10001 = var3 == 0 ? var2 : String.format("%.2f", (double)var2 / (double)var3);
      var1.sendMessage("§7K/D Ratio: §f" + String.valueOf(var10001));
      String var8 = this.a(var4);
      var1.sendMessage("§7Time Played: §f" + var8);
      var8 = var6 != null ? var6.getDisplayName() : "None";
      var1.sendMessage("§7Current Gem: §f" + var8);
      var1.sendMessage("§7Energy: §f" + var7 + "§8/§f10");
      var1.sendMessage("§6§m" + "═".repeat(50));
   }

   private void c(Player var1) {
      var1.sendMessage("§6§m" + "═".repeat(50));
      var1.sendMessage("§e§l\ud83d\udc8e GEM DISTRIBUTION \ud83d\udc8e");
      var1.sendMessage("§6§m" + "═".repeat(50));
      Map var2 = this.a.getStatsManager().getGemUsageStats();

      for(Map.Entry var4 : var2.entrySet()) {
         String var5 = (String)var4.getKey();
         int var6 = (Integer)var4.getValue();
         String var10000;
         switch (var5) {
            case "Fire Gem" -> var10000 = "\ud83d\udd25";
            case "Speed Gem" -> var10000 = "⚡";
            case "Wealth Gem" -> var10000 = "\ud83d\udcb0";
            case "Astra Gem" -> var10000 = "✨";
            case "Puff Gem" -> var10000 = "\ud83d\udca8";
            case "Flux Gem" -> var10000 = "⚡";
            case "Life Gem" -> var10000 = "❤️";
            case "Strength Gem" -> var10000 = "\ud83d\udcaa";
            default -> var10000 = "  ";
         }

         String var7 = var10000;
         var1.sendMessage("§7" + var7 + " " + var5 + ": §f" + var6 + " §7player" + (var6 == 1 ? "" : "s"));
      }

      var1.sendMessage("§6§m" + "═".repeat(50));
   }

   private String a(long var1) {
      long var3 = var1 / 1000L;
      long var5 = var3 / 60L;
      long var7 = var5 / 60L;
      long var9 = var7 / 24L;
      if (var9 > 0L) {
         return var9 + "d " + var7 % 24L + "h";
      } else if (var7 > 0L) {
         return var7 + "h " + var5 % 60L + "m";
      } else {
         return var5 > 0L ? var5 + "m " + var3 % 60L + "s" : var3 + "s";
      }
   }
}
    }

}
