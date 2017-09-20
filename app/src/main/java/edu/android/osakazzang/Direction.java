package edu.android.osakazzang;

/**
 * Created by itwill on 2017-09-20.
 */

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Direction {

        @SerializedName("status") private String status;
        @SerializedName("geocoded_waypoint") private GeocodedWaypoint[] geoWaypoint;
        static class GeocodedWaypoint{
            @SerializedName("geocoder_status") private String geoStatus;
            @SerializedName("types") private String[] type;
            @SerializedName("place_id") private String placeId;
        }

        @SerializedName("routes") private Route[] route;
        static class Route{
            @SerializedName("summary") private String summary;
            @SerializedName("legs") private Leg[] leg;
            static class Leg{
                @SerializedName("steps") private Step[] step;
                static class Step{
                    @SerializedName("travel_mode") private String travelMode;
                    @SerializedName("start_location") private StartLocation startLatLng;
                    static class StartLocation{
                        @SerializedName("lat") private double startLat;
                        @SerializedName("lng") private double startLng;
                    }

                    @SerializedName("end_location") private EndLocation endLatLng;
                    static class EndLocation{
                        @SerializedName("lat") private double endLat;
                        @SerializedName("lng") private double endLng;
                    }

                    @SerializedName("polyline") private PolyLine polyLine;
                    static class PolyLine{
                        @SerializedName("points") private String PolyLinePoints;
                    }

                    @SerializedName("duration") private Duration duration;
                    static class Duration{
                        @SerializedName("value") private int durationValue;
                        @SerializedName("text") private String durationText;
                    }

                    @SerializedName("html_instructions") private String html;
                    @SerializedName("distance") private Distance distance;
                    static class Distance{
                        @SerializedName("value") private int distanceValue;
                        @SerializedName("text") private String distanceText;
                    }
                }
                @SerializedName("start_address") private String startAddr;
                @SerializedName("end_address") private String endAddr;
            }

            @SerializedName("copyrights") private String copyright;
            @SerializedName("overview_polyline") private OverViewPolyLine overViewPolyLine;
            static class OverViewPolyLine{
                @SerializedName("points") private String points;
            }
            @SerializedName("warnings") private String[] warnings;
            @SerializedName("waypoint_order") private int[] wayPointIndex;
            @SerializedName("bounds") private Bounds bounds;
            static class Bounds{
                @SerializedName("southwest") private SouthWest southWest;
                static class SouthWest{
                    @SerializedName("lat") private double swLat;
                    @SerializedName("lng") private double swLng;
                }
                @SerializedName("northeast") private NorthEast northEast;
                static class NorthEast{
                    @SerializedName("lat") private double neLat;
                    @SerializedName("lng") private double neLng;
                }
            }
        }


        public List<DirectionItem> getDirectionList(){
            List<DirectionItem> list = new ArrayList<>();

            for(Route r : route){
                for(Route.Leg l : r.leg) {
                    for(Route.Leg.Step s : l.step){
                        LatLng start = new LatLng(s.startLatLng.startLat, s.startLatLng.startLng);
                        LatLng end = new LatLng(s.endLatLng.endLat, s.endLatLng.endLng);
                        DirectionItem item = new DirectionItem(start, end);
                        list.add(item);
                    }
                }
            }

            return  list;
        }

        public class DirectionItem {
            private LatLng startLatLng;
            private LatLng endLatLng;

            public DirectionItem(LatLng startLatLng, LatLng endLatLng) {
                this.startLatLng = startLatLng;
                this.endLatLng = endLatLng;
            }

            public LatLng getStartLatLng() {
                return startLatLng;
            }

            public LatLng getEndLatLng() {
                return endLatLng;
            }
        }
}
