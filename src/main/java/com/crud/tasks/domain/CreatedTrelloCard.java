package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedTrelloCard {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortUrl")
    private String shortUrl;

    @JsonProperty("badges")
    private Badges badges;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Badges {

        @JsonProperty("attachmentsByType")
        private AttachmentsByType attachmentsByType;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AttachmentsByType {

            @JsonProperty("trello")
            private Trello trello;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Trello {
                @JsonProperty("board")
                private int board;

                @JsonProperty("card")
                private int card;
            }
        }
    }
}


