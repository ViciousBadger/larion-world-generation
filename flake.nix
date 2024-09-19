{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    utils.url = "github:numtide/flake-utils";
  };
  outputs = {
    self,
    nixpkgs,
    utils,
  }: let
    lib = nixpkgs.lib;
    system = "x86_64-linux";
    pkgs = import nixpkgs {inherit system;};
    libraryPath = lib.makeLibraryPath (with pkgs; [
      libGL
    ]);
  in
    utils.lib.eachDefaultSystem (system: {
      formatter = pkgs.alejandra;

      devShells.default = pkgs.mkShell rec {
        buildInputs = with pkgs; [
          jdk21
          libGL
          (jdt-language-server.override { jdk = jdk21; })
        ];
        LD_LIBRARY_PATH = "${nixpkgs.lib.makeLibraryPath buildInputs}";
        # LD_LIBRARY_PATH="/run/opengl-driver/lib:/run/opengl-driver-32/lib";
      };
    });
}

