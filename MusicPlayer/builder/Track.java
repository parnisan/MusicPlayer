package MusicPlayer.builder;

public class Track {

    private final String title;
    private final String artist;

    private final String genre;
    private final int durationSeconds;
    private final int bitrate;

    private Track(Builder builder) {
        this.title = builder.title;
        this.artist = builder.artist;
        this.genre = builder.genre;
        this.durationSeconds = builder.durationSeconds;
        this.bitrate = builder.bitrate;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getBitrate() {
        return bitrate;
    }


    @Override
    public String toString() {
        int minutes = durationSeconds / 60;
        int seconds = durationSeconds % 60;
        return artist + " - " +  title + " ( " +  genre + ", "  + minutes + ":" + seconds + ", " + bitrate + " kbps )";
    }


    public static class Builder {

        private final String title;
        private final String artist;

        private String genre = "Unknown";
        private int durationSeconds = 0;
        private int bitrate = 128;


        public Builder(String title, String artist) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Название трека не может быть пустым");
            }
            if (artist == null || artist.trim().isEmpty()) {
                throw new IllegalArgumentException("Исполнитель не может быть пустым");
            }
            this.title = title;
            this.artist = artist;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder durationSeconds(int durationSeconds) {
            if (durationSeconds < 0) {
                throw new IllegalArgumentException("Длительность не может быть отрицательной");
            }
            this.durationSeconds = durationSeconds;
            return this;
        }

        public Builder bitrate(int bitrate) {
            if (bitrate <= 0) {
                throw new IllegalArgumentException("Битрейт должен быть положительным");
            }
            this.bitrate = bitrate;
            return this;
        }

        public Track build() {
            return new Track(this);
        }
    }
}