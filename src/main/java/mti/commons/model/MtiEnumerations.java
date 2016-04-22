package mti.commons.model;

public class MtiEnumerations
{

	public enum MissionState {
		ENABLED(
			(short) 0),
		DISABLED(
			(short) 1);

		private final Short s4607Value;

		MissionState(
			final Short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public MissionState getMissionState(
			final Short value ) {
			if (value != null) {
				for (final MissionState missionState : MissionState.values()) {
					if (missionState.to4607Value() == value.shortValue()) {
						return missionState;
					}
				}
			}

			return null;
		}
	};

	public enum Classification {
		TOP_SECRET(
			(short) 1),
		SECRET(
			(short) 2),
		CONFIDENTIAL(
			(short) 3),
		RESTRICTED(
			(short) 4),
		UNCLASSIFIED(
			(short) 5),
		NO_CLASSIFICATION(
			(short) 6);

		private final Short s4607Value;

		Classification(
			final Short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public Classification getClassification(
			final Short value ) {
			if (value != null) {
				for (final Classification classification : Classification.values()) {
					if (classification.to4607Value() == value.shortValue()) {
						return classification;
					}
				}
			}

			return null;
		}
	};

	public enum ExerciseIndicator {
		OPERATION_REAL(
			(short) 0),
		OPERATION_SIMULATED(
			(short) 1),
		OPERATION_SYNTHESIZED(
			(short) 2),
		EXERCISE_REAL(
			(short) 128),
		EXERCISE_SIMULATED(
			(short) 129),
		EXERCISE_SYNTHESIED(
			(short) 130);

		private final Short s4607Value;

		ExerciseIndicator(
			final Short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public ExerciseIndicator getExerciseIndicator(
			final Short value ) {
			if (value != null) {
				for (final ExerciseIndicator exerciseIndicator : ExerciseIndicator.values()) {
					if (exerciseIndicator.to4607Value() == value.shortValue()) {
						return exerciseIndicator;
					}
				}
			}

			return null;
		}
	};

	public enum PlatformType {
		UNIDENTIFIED(
			(short) 0),
		ACS(
			(short) 1),
		ARL_M(
			(short) 2),
		SENTINEL(
			(short) 3),
		ROTARY_WIND_RADAR(
			(short) 4),
		GLOBAL_HAWK_NAVY(
			(short) 5),
		HORIZON(
			(short) 6),
		E_8_JOINT_STARS(
			(short) 7),
		P_3C(
			(short) 8),
		PREDATOR(
			(short) 9),
		RADARSAT2(
			(short) 10),
		U_2(
			(short) 11),
		E_10(
			(short) 12),
		UGS_SINGLE(
			(short) 13),
		UGS_CLUSTER(
			(short) 14),
		GROUND_BASED(
			(short) 15),
		UAV_ARMY(
			(short) 16),
		UAV_MARINES(
			(short) 17),
		UAV_NAVY(
			(short) 18),
		UAV_AIR_FORCE(
			(short) 19),
		GLOBAL_HAWK_AIR_FORCE(
			(short) 20),
		GLOBAL_HAWK_AUSTRALIA(
			(short) 21),
		GLOBAL_HAWK_GERMANY(
			(short) 22),
		PUAL_REVERE(
			(short) 23),
		MARINER_UAV(
			(short) 24),
		BAC_111(
			(short) 25),
		COYOTE(
			(short) 26),
		KING_AIR(
			(short) 27),
		LIMIT(
			(short) 28),
		NRL_NP_3B(
			(short) 29),
		SOSTAR_X(
			(short) 30),
		WATCHKEEPER(
			(short) 31),
		AGS_A321(
			(short) 32),
		STRYKER(
			(short) 33),
		HALE_UAV(
			(short) 34),
		SIMD(
			(short) 35),
		REAPER(
			(short) 36),
		WARRIOR_A(
			(short) 37),
		WARRIOR(
			(short) 38),
		TWIN_OTTER(
			(short) 39),
		OTHER(
			(short) 255);

		private final Short s4607Value;

		PlatformType(
			final Short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public PlatformType getPlatformType(
			final Short value ) {
			if (value != null) for (final PlatformType platformType : PlatformType.values()) {
				if (platformType.to4607Value() == value.shortValue()) {
					return platformType;
				}
			}

			return PlatformType.UNIDENTIFIED;
		}
	};

	static public enum TargetClassification {
		NO_INFORMATION_LIVE(
			(short) 0),
		TRACKED_VEHICLE_LIVE(
			(short) 1),
		WHEELED_VHICLE_LIVE(
			(short) 2),
		ROTARY_WING_AIRCRAFT_LIVE(
			(short) 3),
		FIXED_WING_AIRCRAFT_LIVE(
			(short) 4),
		STATIONARY_ROTATOR_LIVE(
			(short) 5),
		MARITIME_LIVE(
			(short) 6),
		BEACON_LIVE(
			(short) 7),
		AMPHIBIOUSE_LIVE(
			(short) 8),
		PERSON_LIVE(
			(short) 9),
		VEHICLE_LIVE(
			(short) 10),
		ANIMAL_LIVE(
			(short) 11),
		LARGE_MULTIPLE_RETURN_LAND_LIVE(
			(short) 12),
		LARGE_MULTIPLE_RETURN_MARITIME_LIVE(
			(short) 13),
		OTHER_LIVE(
			(short) 126),
		UNKNOWN_LIVE(
			(short) 127),
		NO_INFORMATION_SIMULATED(
			(short) 128),
		TRACKED_VEHICLE_SIMULATED(
			(short) 129),
		WHEELED_VHICLE_SIMULATED(
			(short) 130),
		ROTARY_WING_AIRCRAFT_SIMULATED(
			(short) 131),
		FIXED_WING_AIRCRAFT_SIMULATED(
			(short) 132),
		STATIONARY_ROTATOR_SIMULATED(
			(short) 133),
		MARITIME_SIMULATED(
			(short) 134),
		BEACON_SIMULATED(
			(short) 135),
		AMPHIBIOUSE_SIMULATED(
			(short) 136),
		PERSON_SIMULATED(
			(short) 137),
		VEHICLE_SIMULATED(
			(short) 138),
		ANIMAL_SIMULATED(
			(short) 139),
		LARGE_MULTIPLE_RETURN_LAND_SIMULATED(
			(short) 140),
		LARGE_MULTIPLE_RETURN_MARITIME_SIMULATED(
			(short) 141),
		TAGGING_DEVICE(
			(short) 143),
		OTHER_SIMULATED(
			(short) 254),
		UNKNOWN_SIMULATED(
			(short) 255);

		private final Short s4607Value;

		TargetClassification(
			final short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public TargetClassification getTargetClassification(
			final Short value ) {
			for (final TargetClassification targetClassification : TargetClassification.values()) {
				if (targetClassification.to4607Value() == value) {
					return targetClassification;
				}
			}

			return null;
		}
	}

	static public enum SensorType {
		UNIDENTIFIED(
			(short) 0),
		OTHER(
			(short) 1),
		HiSAR(
			(short) 2),
		ASTOR(
			(short) 3),
		ROTARY_WING_RADAR(
			(short) 4),
		GLOBAR_HAWK_SENSOR(
			(short) 5),
		HORIZON(
			(short) 6),
		APY_3(
			(short) 7),
		APY_6(
			(short) 8),
		APY_8_LYNX_I(
			(short) 9),
		RADARSAT2(
			(short) 10),
		ASARS_2A(
			(short) 11),
		TESAR(
			(short) 12),
		MP_RTIP(
			(short) 13),
		APG_77(
			(short) 14),
		APG_79(
			(short) 15),
		APG_81(
			(short) 16),
		APY_6v1(
			(short) 17),
		DPY_1_LYNX_II(
			(short) 18),
		SIDM(
			(short) 19),
		LIMIT(
			(short) 20),
		TCAR_AGS_A321(
			(short) 21),
		LSRS_SENSOR(
			(short) 22),
		UGS_SINGLE_SENSOR(
			(short) 25),
		UGS_CLUSTER_SENSOR(
			(short) 24),
		IMASTER_GMTI(
			(short) 25),
		AN_ZPY_1_STARLITE(
			(short) 26),
		VADER(
			(short) 27),
		NO_STATEMENT(
			(short) 255);

		private final Short s4607Value;

		SensorType(
			final Short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public SensorType getSensorType(
			final Short value ) {
			if (value != null) {
				for (final SensorType sensorType : SensorType.values()) {
					if (sensorType.to4607Value() == value.shortValue()) {
						return sensorType;
					}
				}
			}

			return null;
		}

	};

	static public enum RadarMode {
		UNSPECIFIED_MODE(
			(short) 0),
		MTI(
			(short) 1),
		HRR(
			(short) 2),
		UHRR(
			(short) 3),
		HUR(
			(short) 4),
		FTI(
			(short) 5),
		ATTACK_CONTROL_SATC(
			(short) 11),
		ATTACK_CONTROL(
			(short) 12),
		SATC(
			(short) 13),
		ATTACK_PLANNING_SATC(
			(short) 14),
		ATTACK_PLANNING(
			(short) 15),
		MRSS(
			(short) 16),
		LRSS(
			(short) 17),
		WAS_GRCA(
			(short) 18),
		WAS_RRCA(
			(short) 19),
		ATTACK_PLANNING_TRACKING(
			(short) 20),
		ATTACK_CONTROL_TRACKING(
			(short) 21),
		WAMTI(
			(short) 31),
		CRS(
			(short) 32),
		MRS(
			(short) 33),
		HRS(
			(short) 34),
		POINT_IMAGING(
			(short) 35),
		SMTI(
			(short) 36),
		REPETIVE_POINT_IMAGING(
			(short) 37),
		MONOPLUSE_CALIBRATION(
			(short) 38),
		SEARCH(
			(short) 51),
		EMTI_WFS(
			(short) 52),
		EMTI_NFS(
			(short) 53),
		EMTI_AUGMENTED_SPOT(
			(short) 54),
		EMTI_WAMTI(
			(short) 55),
		GMTI_PPI_MODE(
			(short) 61),
		GMTI_EXPANDED_MODE(
			(short) 62),
		NSS(
			(short) 63),
		SBS(
			(short) 64),
		WA(
			(short) 65),
		GRCA(
			(short) 81),
		RRCA(
			(short) 82),
		SECTOR_SEARCH(
			(short) 83),
		HORIZON_BASIC(
			(short) 84),
		HORIZON_HS(
			(short) 85),
		HORIZON_BURN_THROUGH(
			(short) 86),
		CRESO_ACQUISITION(
			(short) 87),
		CRESO_COUNT(
			(short) 88),
		MTI_EXO(
			(short) 94),
		MTI_ENDO_EXO(
			(short) 95),
		SS_MTI_EXO(
			(short) 96),
		SS_MTI_ENDO_EXO(
			(short) 97),
		TEST_STATUS_MODE(
			(short) 100),
		MTI_SPOT_SCAN(
			(short) 101),
		MTI_ARC_SCAN(
			(short) 102),
		HRR_MTI_SPOT_SCAN(
			(short) 103),
		HRR_MTI_ARC_SCAN(
			(short) 104),
		GRCA_GH(
			(short) 111),
		RRCA_GH(
			(short) 112),
		GMTI_HRR(
			(short) 113),
		SMALL_AREA_GMTI(
			(short) 120),
		WIDE_AREA_GMTI(
			(short) 121),
		DISMOUNT_GMTI(
			(short) 122),
		HRR_GMTI(
			(short) 123);

		private final Short s4607Value;

		RadarMode(
			final Short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public RadarMode getRadarMode(
			final Short value ) {
			if (value != null) {
				for (final RadarMode radarMode : RadarMode.values()) {
					if (radarMode.to4607Value() == value.shortValue()) {
						return radarMode;
					}
				}
			}

			return null;
		}
	};

	static public enum TerrainElevationModel {
		NONE_SPECIFIED(
			(short) 0),
		DTED0(
			(short) 1),
		DTED1(
			(short) 2),
		DTED2(
			(short) 3),
		DTED3(
			(short) 4),
		DTED4(
			(short) 5),
		DTED5(
			(short) 6),
		SRTM1(
			(short) 7),
		SRTM2(
			(short) 8),
		DGM50_M745(
			(short) 9),
		DGM250(
			(short) 10),
		ITHD(
			(short) 11),
		STHD(
			(short) 12),
		SEDRIS(
			(short) 13);

		private final Short s4607Value;

		TerrainElevationModel(
			final Short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public TerrainElevationModel getElevationModel(
			final Short value ) {
			if (value != null) {
				for (final TerrainElevationModel elevationModel : TerrainElevationModel.values()) {
					if (elevationModel.to4607Value() == value.shortValue()) {
						return elevationModel;
					}
				}
			}

			return null;
		}
	};

	static public enum GeoidModel {
		NONE_SPECIFIED(
			(short) 0),
		EGM96(
			(short) 1),
		GEO96(
			(short) 2),
		FLAT_EARTH(
			(short) 3);

		private final Short s4607Value;

		GeoidModel(
			final Short value ) {
			s4607Value = value;
		}

		public final Short to4607Value() {
			return s4607Value;
		}

		static public GeoidModel getGeoidModel(
			final Short value ) {
			if (value != null) {
				for (final GeoidModel geoidModel : GeoidModel.values()) {
					if (geoidModel.to4607Value() == value.shortValue()) {
						return geoidModel;
					}
				}
			}

			return null;
		}
	};

	static public enum Cocom {
		DOMESTIC(
			(short) 1),
		NORTHCOM(
			(short) 2),
		SOUTHCOM(
			(short) 4),
		EUCOM(
			(short) 8),
		AFRICOM(
			(short) 16),
		CENTCOM(
			(short) 32),
		PACOM(
			(short) 64);

		private final Short value;

		Cocom(
			Short value ) {
			this.value = value;
		}

		public final String toString() {
			return value.toString();
		}

		public final Short toShort() {
			return value;
		}

		public final Cocom fromShort(
			Short value ) {

			if (value != null) {
				for (Cocom c : Cocom.values()) {
					if (value == c.value) {
						return c;
					}
				}
			}
			return null;
		}
	};

}
