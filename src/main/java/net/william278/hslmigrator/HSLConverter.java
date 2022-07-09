package net.william278.hslmigrator;

import me.william278.husksync.bukkit.data.DataSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;


/**
 * Class used for converting legacy HuskSync data to Bukkit data
 */
@SuppressWarnings("unused")
public class HSLConverter {

    private static HSLConverter instance;

    private HSLConverter() {
    }

    /**
     * Returns an instance of the {@link HSLConverter}
     *
     * @return {@link HSLConverter} instance
     */
    @NotNull
    public static HSLConverter getInstance() {
        if (instance == null) {
            instance = new HSLConverter();
        }
        return instance;
    }

    /**
     * Deserializes a player's location data from a string into a {@link DataSerializer.PlayerLocation}
     *
     * @param serializedLocationData String containing the player's location data
     * @return {@link DataSerializer.PlayerLocation} containing the player's location data
     * @throws IOException If the string is not a valid Base64 encoded player location string
     */
    @Nullable
    public DataSerializer.PlayerLocation deserializePlayerLocationData(@NotNull String serializedLocationData)
            throws IOException {
        if (serializedLocationData.isEmpty()) {
            return null;
        }
        try {
            return (DataSerializer.PlayerLocation) deserialize(serializedLocationData);
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Deserializes a player's advancement data into {@link DataSerializer.AdvancementRecordDate} data.
     *
     * @param serializedAdvancementData The serialized advancement data {@link String}
     * @return The deserialized {@link DataSerializer.AdvancementRecordDate} for the player
     * @throws IOException If the deserialization fails
     */
    @SuppressWarnings({"unchecked", "deprecation"}) // Ignore the unchecked cast here
    @NotNull
    public List<DataSerializer.AdvancementRecordDate> deserializeAdvancementData(
            @NotNull String serializedAdvancementData) throws IOException {
        if (serializedAdvancementData.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            List<?> deserialize = (List<?>) deserialize(serializedAdvancementData);

            // Migrate old AdvancementRecord into date format
            if (!deserialize.isEmpty() && deserialize.get(0) instanceof DataSerializer.AdvancementRecord) {
                deserialize = ((List<DataSerializer.AdvancementRecord>) deserialize).stream()
                        .map(o -> new DataSerializer.AdvancementRecordDate(
                                o.advancementKey(),
                                o.awardedAdvancementCriteria()
                        )).toList();
            }

            return (List<DataSerializer.AdvancementRecordDate>) deserialize;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Deserializes a player's statistic data as serialized into {@link DataSerializer.StatisticData}.
     *
     * @param serializedStatisticData The serialized statistic data {@link String}
     * @return The deserialized {@link DataSerializer.StatisticData} for the player
     * @throws IOException If the deserialization fails
     */
    @NotNull
    public DataSerializer.StatisticData deserializeStatisticData(@NotNull String serializedStatisticData)
            throws IOException {
        if (serializedStatisticData.isEmpty()) {
            return new DataSerializer.StatisticData(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
        }
        try {
            return (DataSerializer.StatisticData) deserialize(serializedStatisticData);
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Deserialize an object from a Base64 string
     */
    @NotNull
    private Object deserialize(@NotNull String serializedString) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(serializedString);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return objectInputStream.readObject();
        }
    }

}
