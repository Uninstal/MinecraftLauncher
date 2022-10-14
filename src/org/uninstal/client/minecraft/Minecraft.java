package org.uninstal.client.minecraft;

import org.uninstal.client.Client;
import org.uninstal.client.util.Paths;

public class Minecraft {

  private MinecraftMonitor monitor;
  private final String cp = "libraries/com/turikhay/ca-fixer/1.0/ca-fixer-1.0.jar;" +
    "libraries/net/minecraftforge/forge/1.12.2-14.23.5.2860/forge-1.12.2-14.23.5.2860.jar;" +
    "libraries/org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar;" +
    "libraries/net/minecraft/launchwrapper/1.12/launchwrapper-1.12.jar;" +
    "libraries/org/jline/jline/3.5.1/jline-3.5.1.jar;" +
    "libraries/com/typesafe/akka/akka-actor_2.11/2.3.3/akka-actor_2.11-2.3.3.jar;" +
    "libraries/com/typesafe/config/1.2.1/config-1.2.1.jar;" +
    "libraries/org/scala-lang/scala-actors-migration_2.11/1.1.0/scala-actors-migration_2.11-1.1.0.jar;" +
    "libraries/org/scala-lang/scala-compiler/2.11.1/scala-compiler-2.11.1.jar;" +
    "libraries/org/scala-lang/plugins/scala-continuations-library_2.11/1.0.2_mc/scala-continuations-library_2.11-1.0.2_mc.jar;" +
    "libraries/org/scala-lang/plugins/scala-continuations-plugin_2.11.1/1.0.2_mc/scala-continuations-plugin_2.11.1-1.0.2_mc.jar;" +
    "libraries/org/scala-lang/scala-library/2.11.1/scala-library-2.11.1.jar;" +
    "libraries/org/scala-lang/scala-parser-combinators_2.11/1.0.1/scala-parser-combinators_2.11-1.0.1.jar;" +
    "libraries/org/scala-lang/scala-reflect/2.11.1/scala-reflect-2.11.1.jar;" +
    "libraries/org/scala-lang/scala-swing_2.11/1.0.1/scala-swing_2.11-1.0.1.jar;" +
    "libraries/org/scala-lang/scala-xml_2.11/1.0.2/scala-xml_2.11-1.0.2.jar;" +
    "libraries/lzma/lzma/0.0.1/lzma-0.0.1.jar;libraries/java3d/vecmath/1.5.2/vecmath-1.5.2.jar;" +
    "libraries/net/sf/trove4j/trove4j/3.0.3/trove4j-3.0.3.jar;" +
    "libraries/org/apache/maven/maven-artifact/3.5.3/maven-artifact-3.5.3.jar;" +
    "libraries/net/sf/jopt-simple/jopt-simple/5.0.3/jopt-simple-5.0.3.jar;" +
    "libraries/org/apache/logging/log4j/log4j-api/2.15.0/log4j-api-2.15.0.jar;" +
    "libraries/org/apache/logging/log4j/log4j-core/2.15.0/log4j-core-2.15.0.jar;" +
    "libraries/ru/tlauncher/patchy/1.0.0/patchy-1.0.0.jar;libraries/oshi-project/oshi-core/1.1/oshi-core-1.1.jar;" +
    "libraries/net/java/dev/jna/jna/4.4.0/jna-4.4.0.jar;" +
    "libraries/net/java/dev/jna/platform/3.4.0/platform-3.4.0.jar;" +
    "libraries/com/ibm/icu/icu4j-core-mojang/51.2/icu4j-core-mojang-51.2.jar;" +
    "libraries/com/paulscode/codecjorbis/20101023/codecjorbis-20101023.jar;" +
    "libraries/com/paulscode/codecwav/20101023/codecwav-20101023.jar;" +
    "libraries/com/paulscode/libraryjavasound/20101123/libraryjavasound-20101123.jar;" +
    "libraries/com/paulscode/librarylwjglopenal/20100824/librarylwjglopenal-20100824.jar;" +
    "libraries/com/paulscode/soundsystem/20120107/soundsystem-20120107.jar;" +
    "libraries/io/netty/netty-all/4.1.9.Final/netty-all-4.1.9.Final.jar;" +
    "libraries/com/google/guava/guava/21.0/guava-21.0.jar;" +
    "libraries/org/apache/commons/commons-lang3/3.5/commons-lang3-3.5.jar;" +
    "libraries/commons-io/commons-io/2.5/commons-io-2.5.jar;" +
    "libraries/commons-codec/commons-codec/1.10/commons-codec-1.10.jar;" +
    "libraries/net/java/jinput/jinput/2.0.5/jinput-2.0.5.jar;" +
    "libraries/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar;" +
    "libraries/com/google/code/gson/gson/2.8.0/gson-2.8.0.jar;" +
    "libraries/by/ely/authlib/3.2.38.0-cafixer/authlib-3.2.38.0-cafixer.jar;" +
    "libraries/com/mojang/realms/1.10.22/realms-1.10.22.jar;" +
    "libraries/org/apache/commons/commons-compress/1.8.1/commons-compress-1.8.1.jar;" +
    "libraries/org/apache/httpcomponents/httpclient/4.3.3/httpclient-4.3.3.jar;" +
    "libraries/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar;" +
    "libraries/org/apache/httpcomponents/httpcore/4.3.2/httpcore-4.3.2.jar;" +
    "libraries/it/unimi/dsi/fastutil/7.1.0/fastutil-7.1.0.jar;" +
    "libraries/org/apache/logging/log4j/log4j-api/2.8.1/log4j-api-2.8.1.jar;" +
    "libraries/org/apache/logging/log4j/log4j-core/2.8.1/log4j-core-2.8.1.jar;" +
    "libraries/org/lwjgl/lwjgl/lwjgl/2.9.4-nightly-20150209/lwjgl-2.9.4-nightly-20150209.jar;" +
    "libraries/org/lwjgl/lwjgl/lwjgl_util/2.9.4-nightly-20150209/lwjgl_util-2.9.4-nightly-20150209.jar;" +
    "libraries/com/mojang/text2speech/1.10.3/text2speech-1.10.3.jar;" +
    "versions/ForgeOptiFine 1.12.2/ForgeOptiFine 1.12.2.jar";

  /**
   * Запуск майнкрафта.
   * Сделано в отдельном потоке, чтобы избежать зависаний.
   */
  public void launch() {
    new Thread(() -> {
      MinecraftProcessBuilder b = new MinecraftProcessBuilder();
      appendMainArguments(b);
      appendClasspath(b);
      appendMinecraftArguments(b);
      Process process = b.start();
      
      if (process != null) {
        monitor = new MinecraftMonitor(process.getInputStream(), process.getErrorStream());
        try {
          process.waitFor();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();
  }
  
  private void appendMainArguments(MinecraftProcessBuilder b) {
    String path = Paths.getHomeLocation() + "/";
    path = path.substring(1);
    b.append("java");
    b.append("-Xms2048M");
    b.append("-Xmx2048M");
    b.append("-Djava.library.path=" + path + "versions/ForgeOptiFine 1.12.2/natives");
    b.append("-Dminecraft.client.jar=" + path + "versions/ForgeOptiFine 1.12.2/ForgeOptiFine 1.12.2.jar");
  }
  
  private void appendClasspath(MinecraftProcessBuilder b) {
    String path = Paths.getHomeLocation() + "/";
    b.append("-cp").append(path + cp.replace(";", ";" + path));
  }
  
  private void appendMinecraftArguments(MinecraftProcessBuilder b) {
    String path = Paths.getHomeLocation();
    b.append("net.minecraft.launchwrapper.Launch");
    b.append("--username").append(Client.getAccount().getLogin());
    b.append("--version").append("ForgeOptiFine 1.12.2");
    b.append("--gameDir").append(path);
    b.append("--assetsDir").append(path + "/assets");
    b.append("--assetIndex").append("1.12");
    b.append("--accessToken").append("[vk.cc/7iPiB9]");
    b.append("--tweakClass").append("net.minecraftforge.fml.common.launcher.FMLTweaker");
    // b.append("--uuid").append("db0b41e25a323186b7c889c251ce4c35");
    b.append("--userType").append("legacy");
    b.append("--versionType").append("Forge");
    // b.append("--width").append("925");
    // b.append("--height").append("530");
    // b.append("--modListFile").append("tempModList-1665681180757");
  }
}