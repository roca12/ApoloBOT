package controller.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

public class SlashBuilder {
	
	private DiscordApi api;
	
	private Set<SlashCommandBuilder> builders;
	
	private Properties prop = new Properties();

	public SlashBuilder(DiscordApi api) {
		this.api=api;
		builders = new HashSet<>();
	}

	public Set<SlashCommandBuilder> initSlashCommands() {

		builders.add(new SlashCommandBuilder().setName("ayuda")
				.setDescription("Muestra la lista completa de comandos y quienes pueden usarlo"));

		builders.add(new SlashCommandBuilder().setName("ping").setDescription("Retorna un saludo conocido"));

		builders.add(new SlashCommandBuilder().setName("test")
				.setDescription("Verifica la conectividad de todos los sistemas"));

		builders.add(new SlashCommandBuilder().setName("numerousuarios")
				.setDescription("Cuenta la cantidad de usuarios existentes en el servidor"));

		builders.add(SlashCommand.with("entrenamiento", "Capacidad de anunciar o cancelar clases",
				Arrays.asList(
						SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Cancelar",
								"Cancela la siguiente clase"),
						SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Anunciar",
								"Envia un aviso a todos los participantes de este canal de texto"))));

		builders.add(SlashCommand.with("evento",
				"Administra eventos futuros, tendra un recordatorio un dia antes y una hora antes",
				Arrays.asList(
						SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Agregar",
								"Agrega nuevo evento"),
						SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Eliminar",
								"Elimina un evento por ID"),
						SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Actualizar",
								"Actualiza un evento por ID"),
						SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Listar",
								"Lista todos los eventos"))));

		return builders;
	}

	public Set<SlashCommandBuilder> getBuilders() {
		return builders;
	}

	public void setBuilders(Set<SlashCommandBuilder> builders) {
		this.builders = builders;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public DiscordApi getApi() {
		return api;
	}

	public void setApi(DiscordApi api) {
		this.api = api;
	}
}
